//複数教室への対応完了
package com.example.demo.controller;

import com.example.demo.constant.Subject;
import com.example.demo.constant.TotalSubject;
import com.example.demo.dto.AverageScoreAndStandardScoreForKlass;
import com.example.demo.dto.SubjectRankingDto;
import com.example.demo.entity.*;
import com.example.demo.repository.MockTestRepository;
import com.example.demo.repository.MockTestResultRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.*;
import com.example.demo.util.UtilForNullRemoval;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MockTestController {
    private final MockTestRepository mockTestRepository;
    private final MockTestResultRepository mockTestResultRepository;
    private final StudentRepository studentRepository;
    private final TermAndYearService termAndYearService;
    private final MockTestService mockTestService;
    private final MockTestResultService mockTestResultService;
    private final ClassStudentService classStudentService;
    private final UserService userService;

    // ヘルパーメソッド
    private String getClassNameOrNull(Klass klass) {
        return klass != null ? klass.getName() : null;
    }

    //分析画面へ遷移するメソッド
    //todo:任意のクラスにその模試が行われていた時点で所属していた生徒の平均を各科目および合計科目について作る必要がある。
    @GetMapping("/mockTest/analysis/{id}")
    public String analyzeResult(@PathVariable("id") Integer mockTestId, Model model) {
        // MockTestの存在確認
        MockTest mockTest = mockTestRepository.getReferenceById(mockTestId);

        CramSchool cramSchool = mockTest.getCramSchool();
        // 教科ごとの平均点を格納するMap
        LinkedHashMap<String, List<AverageScoreAndStandardScoreForKlass>> subjectAverages = new LinkedHashMap<>();

        Student student = mockTestResultRepository.findFirstByMockTest(mockTest).getStudent();

        LocalDate testDate = mockTest.getDate();

        //模試を受けた生徒の模試当日の学年（本当は模試自体に学年を紐づけてもよかったが、そうしていない)
        Integer grade = termAndYearService.getGrade(student,mockTest.getDate());

        // 各教科の平均点を取得
        for (Subject subject : Subject.values()) {
            List<AverageScoreAndStandardScoreForKlass> averages =
                    mockTestResultRepository.findAverageForEachKlassForEachSubject(
                            subject.getValue(),
                            mockTest,
                            testDate
                            //grade

                    );
            log.info("averages:{}",averages.toString());

            //クラスの担当教師は別のクエリで取得する。期間まで考慮していっぺんに取得するのが困難だった。
            averages.forEach(average->{
                userService.setTeacherName(cramSchool,average,subject.getValue(),grade,testDate);
            });

            //任意のクラスに所属している生徒全員の平均
            AverageScoreAndStandardScoreForKlass averageOfEveryClass =
                    mockTestResultRepository.findAverageForEveryKlassForEachSubject(
                            subject.getValue(),
                            mockTest,
                            testDate
                    );

            averages.add(averageOfEveryClass);

            subjectAverages.put(subject.getValue(), averages);
        }

        for(TotalSubject totalSubject : TotalSubject.values()){
            List<AverageScoreAndStandardScoreForKlass> totalAverages =
                    mockTestResultRepository.findAverageForIntegratedKlassForTotalSubject(
                            totalSubject.getValue(),
                            mockTest,
                            testDate
                    );
            //任意のクラスに所属している生徒全員の平均
            AverageScoreAndStandardScoreForKlass averageOfEveryClass =
                    mockTestResultRepository.findAverageForEveryKlassForEachTotalSubject(
                            totalSubject.getValue(),
                            mockTest,
                            testDate
                    );

            totalAverages.add(averageOfEveryClass);
            subjectAverages.put(totalSubject.getValue(),totalAverages);
        }
        // コントローラーで事前にフィルタリング

        UtilForNullRemoval.eraseNullEntry(subjectAverages);

        model.addAttribute("mockTest", mockTest);
        model.addAttribute("subjectAverages", subjectAverages);

        return "mockTest/analysis";  // テンプレート名
    }

    @GetMapping("/mockTestResults/ranking/{id}")
    public String rankingView(@PathVariable("id") Integer mockTestId,
                              @RequestParam("rankType") String rankType,
                              @RequestParam("count") Integer count,
                              Model model) {

        MockTest mockTest = mockTestRepository.findById(mockTestId)
                .orElseThrow(() -> new RuntimeException("Mock test not found"));
        LocalDate testDate = mockTest.getDate();

        // 各教科のランキングを取得
        Map<String, List<SubjectRankingDto>> rankings = new LinkedHashMap<>();//LinkedHashMapは順序を保持できる



        // 国語のランキング
        List<SubjectRankingDto> japaneseRanking = mockTestResultService.findSubjectRankingDto(mockTestId, count, rankType.equals("bottom"),"japanese");
        japaneseRanking.forEach(dto -> {
            dto.setClassName(getClassNameOrNull(classStudentService.findKlassForJapaneseByStudentAndDate(studentRepository.getReferenceById(dto.getStudentId()), testDate)));
        });
        rankings.put("japanese", japaneseRanking);

        // 数学のランキング
        List<SubjectRankingDto> mathRanking = mockTestResultService.findSubjectRankingDto(mockTestId, count, rankType.equals("bottom"),"math");
        mathRanking.forEach(dto -> {
            dto.setClassName(getClassNameOrNull(classStudentService.findKlassForMathByStudentAndDate(studentRepository.getReferenceById(dto.getStudentId()), testDate)));
        });
        rankings.put("math", mathRanking);

        // 英語のランキング
        List<SubjectRankingDto> englishRanking = mockTestResultService.findSubjectRankingDto(mockTestId, count, rankType.equals("bottom"),"english");
        englishRanking.forEach(dto -> {
            dto.setClassName(getClassNameOrNull(classStudentService.findKlassForEnglishByStudentAndDate(studentRepository.getReferenceById(dto.getStudentId()), testDate)));
        });
        rankings.put("english", englishRanking);

        // 理科のランキング
        List<SubjectRankingDto> scienceRanking = mockTestResultService.findSubjectRankingDto(mockTestId, count, rankType.equals("bottom"),"science");
        scienceRanking.forEach(dto -> {
            dto.setClassName(getClassNameOrNull(classStudentService.findKlassForScienceByStudentAndDate(studentRepository.getReferenceById(dto.getStudentId()), testDate)));
        });
        rankings.put("science", scienceRanking);

        // 社会のランキング
        List<SubjectRankingDto> socialRanking = mockTestResultService.findSubjectRankingDto(mockTestId, count, rankType.equals("bottom"),"social");
        socialRanking.forEach(dto -> {
            dto.setClassName(getClassNameOrNull(classStudentService.findKlassForSocialByStudentAndDate(studentRepository.getReferenceById(dto.getStudentId()), testDate)));
        });
        rankings.put("social", socialRanking);

        // 三科目のランキング
        List<SubjectRankingDto> total3Ranking = mockTestResultService.findSubjectRankingDto(mockTestId, count, rankType.equals("bottom"),"total3");
        socialRanking.forEach(dto -> {
            //dto.setClassName(getClassNameOrNull(classStudentService.findKlassForSocialByStudentAndDate(studentRepository.getReferenceById(dto.getStudentId()), testDate)));
            //このクラス名取得のメソッドは複雑だから後で作る。
        });
        rankings.put("total3", total3Ranking);

        // 五科目のランキング
        List<SubjectRankingDto> total5Ranking = mockTestResultService.findSubjectRankingDto(mockTestId, count, rankType.equals("bottom"),"total5");
        socialRanking.forEach(dto -> {
            //dto.setClassName(getClassNameOrNull(classStudentService.findKlassForSocialByStudentAndDate(studentRepository.getReferenceById(dto.getStudentId()), testDate)));
        });
        rankings.put("total5", total5Ranking);

        model.addAttribute("rankings", rankings);
        model.addAttribute("rankType", rankType);
        model.addAttribute("count", count);
        model.addAttribute("mockTest",mockTest);

        return "mockTest/ranking";
    }



    @Transactional
    @PostMapping("/registerMockTest")
    public String uploadMockTestResultCsv(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @RequestParam("file") MultipartFile file,
                                          @RequestParam("date") LocalDate date,
                                          Model model) {
        return handleMockTestUpload(userDetails, file, date, model, false);
    }

    @Transactional
    @PostMapping("/registerHighLevelMockTest")
    public String uploadHighLevelMockTestResultCsv(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @RequestParam("file") MultipartFile file,
                                                   @RequestParam("date") LocalDate date,
                                                   @RequestParam("testGrade") String testGrade,
                                                   Model model) {
        return handleMockTestUpload(userDetails, file, date, model, true, testGrade);
    }




    private String handleMockTestUpload(UserDetailsImpl userDetails, MultipartFile file, LocalDate date, Model model, boolean isHighLevel, String... testGrade) {
        log.info("Starting mock test upload process - File: {}, Date: {}, IsHighLevel: {}", file.getOriginalFilename(), date, isHighLevel);

        if (file.isEmpty()) {
            log.error("Upload failed: Empty file received");
            model.addAttribute("error", "CSVファイルを選択してください。");
            return "error";
        }

        String fileName = file.getOriginalFilename();
        MockTest mockTest = new MockTest();
        mockTest.setCramSchool(userDetails.getCramSchool());
        mockTest.setDate(date);
        mockTest.setName(isHighLevel ? createHighLevelTestName(fileName, date) : fileName);

        try {
            mockTest = mockTestRepository.save(mockTest);
            log.info("Created new mock test with ID: {}", mockTest.getId());
        } catch (Exception e) {
            log.error("Failed to save mock test metadata", e);
            throw new RuntimeException("Failed to initialize mock test", e);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(',')
                    .withQuote('"')
                    .withFirstRecordAsHeader()
                    .parse(reader);

            int processedCount = 0;
            int successCount = 0;

            for (CSVRecord record : records) {
                processedCount++;
                try {
                    boolean success = processRecord(record, mockTest, date, isHighLevel, testGrade.length > 0 ? testGrade[0] : null);
                    if (success) successCount++;
                } catch (Exception e) {
                    log.error("Error processing record #{}: {}", processedCount, e.getMessage());
                }
            }

            log.info("Mock test upload completed - Processed: {}, Successful: {}", processedCount, successCount);

        } catch (IOException ex) {
            log.error("Failed to read CSV file: {}", ex.getMessage(), ex);
            throw new RuntimeException("CSV file processing failed", ex);
        }

        return "redirect:/mockTests";
    }

    private String createHighLevelTestName(String fileName, LocalDate date) {
        String term = String.valueOf(TermAndYearService.getTerm(Date.valueOf(date)));
        return term + "年度　" + fileName;
    }

    private boolean processRecord(CSVRecord record, MockTest mockTest, LocalDate date, boolean isHighLevel, String testGrade) {
        String studentCode = isHighLevel ? record.get("受験者番号") : record.get("塾内番号");
        String studentName = record.get("氏名");

        log.debug("Processing record - StudentCode: {}, Name: {}", studentCode, studentName);

        try {
            Integer el1 = termAndYearService.getWhenEnteredElementarySchoolForCsvReader(
                    isHighLevel ? testGrade : record.get("学年"), date);

            log.debug("Calculated el1: {} for student: {}", el1, studentName);

            Optional<Student> student = studentRepository.findByEl1AndCode(el1, Integer.valueOf(studentCode));

            if (student.isPresent()) {
                MockTestResult mockTestResult = new MockTestResult();
                mockTestResult.setMockTestId(mockTest.getId());
                mockTestResult.setStudentId(student.get().getId());

                log.debug("Setting scores for student ID: {}", student.get().getId());
                setScoresAndDreamSchools(record, mockTestResult, isHighLevel);

                try {
                    mockTestResult = mockTestResultRepository.save(mockTestResult);
                    log.info("Saved mock test result - TestID: {}, StudentID: {}, ResultID: {}",
                            mockTest.getId(), student.get().getId(), mockTestResult.getMockTestId());
                    return true;
                } catch (Exception e) {
                    log.error("Failed to save mock test result for student {}: {}", studentName, e.getMessage());
                    return false;
                }
            } else {
                log.warn("Student not found - Code: {}, Name: {}, El1: {}", studentCode, studentName, el1);
                return false;
            }
        } catch (Exception e) {
            log.error("Error processing student record - Code: {}, Name: {}: {}",
                    studentCode, studentName, e.getMessage());
            return false;
        }
    }


    private void setScoresAndDreamSchools(CSVRecord record, MockTestResult mockTestResult, boolean isHighLevel) {
        try {
            log.debug("Setting scores for mock test result - IsHighLevel: {}", isHighLevel);

            // 点数の設定
            mockTestResult.setJapanese(parseInteger(record.get(isHighLevel ? "国語得点" : "国語\n得点")));
            mockTestResult.setMath(parseInteger(record.get(isHighLevel ? "数学得点" : "数学\n得点")));
            mockTestResult.setEnglish(parseInteger(record.get(isHighLevel ? "英語得点" : "英語\n得点")));
            mockTestResult.setScience(parseInteger(record.get(isHighLevel ? "理科得点" : "理科\n得点")));
            mockTestResult.setSocial(parseInteger(record.get(isHighLevel ? "社会得点" : "社会\n得点")));

            log.debug("Scores set - Japanese: {}, Math: {}, English: {}, Science: {}, Social: {}",
                    mockTestResult.getJapanese(), mockTestResult.getMath(),
                    mockTestResult.getEnglish(), mockTestResult.getScience(),
                    mockTestResult.getSocial());

            // 偏差値の設定
            mockTestResult.setJapaneseSs(parseOptionalDouble(record.get(isHighLevel ? "国語全国偏差値" : "国語\n偏差値")));
            mockTestResult.setMathSs(parseOptionalDouble(record.get(isHighLevel ? "数学全国偏差値" : "数学\n偏差値")));
            mockTestResult.setEnglishSs(parseOptionalDouble(record.get(isHighLevel ? "英語全国偏差値" : "英語\n偏差値")));
            mockTestResult.setScienceSs(parseOptionalDouble(record.get(isHighLevel ? "理科全国偏差値" : "理科\n偏差値")));
            mockTestResult.setSocialSs(parseOptionalDouble(record.get(isHighLevel ? "社会全国偏差値" : "社会\n偏差値")));
            mockTestResult.setTotal3Ss(parseOptionalDouble(record.get(isHighLevel ? "3科目合計全国偏差値" : "二科三科\n偏差値")));
            mockTestResult.setTotal5Ss(parseOptionalDouble(record.get(isHighLevel ? "5科目合計全国偏差値" : "四科五科\n偏差値")));

            log.debug("Deviation scores set - Japanese: {}, Math: {}, English: {}, Total3: {}, Total5: {}",
                    mockTestResult.getJapaneseSs(), mockTestResult.getMathSs(),
                    mockTestResult.getEnglishSs(), mockTestResult.getTotal3Ss(),
                    mockTestResult.getTotal5Ss());

            if (!isHighLevel) {
                // 志望校情報を設定
                setDreamSchoolInfo(record, mockTestResult);
                log.debug("Dream school information set for non-high-level test");
            }

        } catch (Exception e) {
            log.error("Error setting scores and dream schools: {}", e.getMessage());
            throw e;
        }
    }
    private void setDreamSchoolInfo(CSVRecord record, MockTestResult mockTestResult) {
        mockTestResult.setDreamSchool1(getDreamSchoolValue(record.get("志望校名１")));
        mockTestResult.setDreamSchool2(getDreamSchoolValue(record.get("志望校名２")));
        mockTestResult.setDreamSchool3(getDreamSchoolValue(record.get("志望校名３")));
        mockTestResult.setDreamSchool4(getDreamSchoolValue(record.get("志望校名４")));
        mockTestResult.setDreamSchool5(getDreamSchoolValue(record.get("志望校名５")));
        mockTestResult.setDreamSchool6(getDreamSchoolValue(record.get("志望校名６")));

        mockTestResult.setDreamSchool1Probability(parseProbability(record.get("合格\n可能性１")));
        mockTestResult.setDreamSchool2Probability(parseProbability(record.get("合格\n可能性２")));
        mockTestResult.setDreamSchool3Probability(parseProbability(record.get("合格\n可能性３")));
        mockTestResult.setDreamSchool4Probability(parseProbability(record.get("合格\n可能性４")));
        mockTestResult.setDreamSchool5Probability(parseProbability(record.get("合格\n可能性５")));
        mockTestResult.setDreamSchool6Probability(parseProbability(record.get("合格\n可能性６")));
    }


    @GetMapping("/mockTestRegisterForm")
    public String mockTestRegisterForm(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       Model model) {
        return "mockTest/mockTestRegister";
    }

    //模試一覧ページ
    @GetMapping("/mockTests")
    public String getMockTests(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @PageableDefault(page = 0, size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
                               Model model) {
        Page<MockTest> mockTests = mockTestRepository.findAllByCramSchool(userDetails.getCramSchool(), pageable);
        model.addAttribute("mockTests", mockTests);
        return "mockTest/mockTests";
    }

    //すべての模試の結果を表示するビュー
    @GetMapping("/mockTestResults/all/{id}")
    public String showAllMockTestResults(
            @PathVariable("id") Integer mockTestId,
            @RequestParam(name = "sort", required = false) String sort,
            Model model) {

        String sortField = "id"; // デフォルトのソートフィールド
        String sortDirection = "asc"; // デフォルトのソート方向

        // sortパラメータがある場合の処理
        if (sort != null && !sort.trim().isEmpty()) {
            String[] sortParams = sort.split(",");
            sortField = sortParams[0];
            sortDirection = sortParams.length > 1 ? sortParams[1] : "asc";
        }

        List<MockTestResult> mockTestResults;

        MockTest mockTest = mockTestRepository.getReferenceById(mockTestId);
        // クラスでのソート
        if ("klass".equals(sortField)) {
            mockTestResults = mockTestResultService
                    .findAllResultsByMockTestWithIntegratedKlassSort(mockTest, sortDirection);
        }
        // その他のフィールドでのソート
        else {
            mockTestResults = mockTestResultRepository
                    .findAllResultsByMockTestIdWithSort(mockTestId, sortField, sortDirection);
        }

        setKlassNameForList(mockTestResults);
        model.addAttribute("mockTestResults", mockTestResults);
        model.addAttribute("mockTestId", mockTestId);

        return "mockTest/mockTestResultShowAll";
    }

    @GetMapping("/mockTestResults/{id}")
    public String showMockTestResults(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable("id") Integer mockTestId,
                                      @PageableDefault(page = 0, size = 10, sort = "studentId", direction = Sort.Direction.DESC) Pageable pageable,
                                      @RequestParam(required = false) String searchName,
                                      Model model) {
        Page<MockTestResult> mockTestResults;

        if (searchName != null && !searchName.trim().isEmpty()) {
            mockTestResults = mockTestResultRepository.findAllByMockTestIdAndStudentNameContaining(mockTestId, searchName, pageable);
        } else {
            mockTestResults = mockTestResultRepository.findAllByMockTestId(mockTestId, pageable);
        }

        setKlassName(mockTestResults);

        model.addAttribute("mockTestId", mockTestId);
        model.addAttribute("mockTestResults", mockTestResults);
        model.addAttribute("searchName", searchName);
        return "mockTest/mockTestResultShow";
    }

    //テスト実施日のクラスを挿入している
    public void setKlassNameForList(List<MockTestResult> results){
        MockTest test = results.get(0).getMockTest();
        LocalDate date = test.getDate();
        results.forEach(result->{
            result.setKlassName(classStudentService.findIntegratedKlassName(result.getStudent(),date));
        });
    }

    //テスト実施日のクラスを挿入している
    public void setKlassName(Page<MockTestResult> results){
        MockTest test = results.getContent().get(0).getMockTest();
        LocalDate date = test.getDate();
        results.forEach(result->{
            result.setKlassName(classStudentService.findIntegratedKlassName(result.getStudent(),date));
        });
    }

    private Integer parseInteger(String value) {
        return (value == null || value.trim().isEmpty()) ? null : Integer.valueOf(value.trim());//0でなくてnullが入るように改修
    }

    private Integer parseOptionalDouble(String value) {
        return (value == null || value.trim().isEmpty()) ? null : (int) Math.round(Double.parseDouble(value.trim()));
    }

    private Integer parseProbability(String probability) {
        return (probability == null || probability.trim().isEmpty() || probability.equals("**")) ? null : Integer.valueOf(probability.trim());
    }

    private String getDreamSchoolValue(String dreamSchool) {
        return dreamSchool != null && !dreamSchool.trim().isEmpty() ? dreamSchool : null;
    }


    @Transactional
    @GetMapping("/mockTest/delete/{id}")
    public String deleteMockTest(@PathVariable("id")Integer mockTestId,
                                 RedirectAttributes redirectAttributes){
        String message;
        try{
            mockTestRepository.deleteById(mockTestId);
            message="mock test deleted successfully!";
        }catch(RuntimeException e){
            log.error("error happened during deletion of mock test :{}", e.getMessage());
            message = "mock test deletion failed";
        }

        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/mockTests";

    }
}