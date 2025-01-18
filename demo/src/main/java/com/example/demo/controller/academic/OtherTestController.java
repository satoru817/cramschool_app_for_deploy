package com.example.demo.controller.academic;


import com.example.demo.constant.Subject;
import com.example.demo.constant.TotalSubjectForNine;
import com.example.demo.dto.AverageScoreForKlass;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.*;
import com.example.demo.util.UtilForNullRemoval;
import com.example.demo.util.UtilForString;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/otherTest")
public class OtherTestController {
    private final OtherTestRepository otherTestRepository;
    private final OtherTestResultRepository otherTestResultRepository;
    private final SchoolRepository schoolRepository;
    private final ClassRepository classRepository;
    private final TermAndYearService termAndYearService;
    private final SchoolService schoolService;
    private final StudentService studentService;
    private final OtherTestService otherTestService;
    private final StudentRepository studentRepository;
    private final ClassStudentService classStudentService;
    private final OtherTestResultService otherTestResultService;
    private final UserService userService;
    private final CsvService csvService;
    
    @Transactional(readOnly = true)
    @GetMapping("/analysis/{id}")
    public String analyzeResult(@PathVariable("id")Integer otherTestId,
                                Model model){
        OtherTest otherTest = otherTestService.findById(otherTestId);

        CramSchool cramSchool = otherTest.getSchool().getCramSchool();

        Integer grade = Integer.valueOf(otherTest.getGrade());

        LocalDate testDate = otherTest.getDate();

        LinkedHashMap<String,List<AverageScoreForKlass>> subjectAverages = new LinkedHashMap<>();

        for(Subject subject : Subject.values()){
            List<AverageScoreForKlass> averages =
                    otherTestResultRepository.findAverageForEachKlassForEachSubject(
                            subject.getValue(),
                            otherTest,
                            testDate
                    );
            averages.forEach(average->{
                userService.setTeacherName(cramSchool,average,subject.getValue(),grade+6,testDate);
            });

            AverageScoreForKlass averageOfEveryClass =
                    otherTestResultRepository.findAverageForEveryKlassForEachSubject(
                            subject.getValue(),
                            otherTest,
                            testDate
                    );
            averages.add(averageOfEveryClass);
            subjectAverages.put(subject.getValue(),averages);
        }

        for(TotalSubjectForNine totalSubject : TotalSubjectForNine.values()){
            List<AverageScoreForKlass> totalAverages =
                    otherTestResultRepository.findAverageForIntegratedKlassForTotalSubject(
                            totalSubject.getValue(),
                            otherTest,
                            testDate
                    );

            AverageScoreForKlass averageScoreForKlass =
                    otherTestResultRepository.findAverageForEveryClassForEachTotalSubject(
                            totalSubject.getValue(),
                            otherTest,
                            testDate
                    );
            totalAverages.add(averageScoreForKlass);
            subjectAverages.put(totalSubject.getValue(),totalAverages);
        }


        UtilForNullRemoval.eraseNullEntry(subjectAverages);

        model.addAttribute("otherTest",otherTest);
        model.addAttribute("subjectAverages",subjectAverages);

        return "otherTest/analysis";
        
    }

    @Transactional(readOnly = true)
    @GetMapping("/all_result/{id}")
    public String showAllResult(@PathVariable("id")Integer otherTestId,
                                @RequestParam(name="sort",required = false)String sort,
                                @RequestParam(name="order",required = false)Integer direction,
                                Model model){
        OtherTest otherTest = otherTestRepository.getReferenceById(otherTestId);
        List<OtherTestResult> otherTestResults;
        if("klass".equals(sort)){
            otherTestResults = otherTestResultService
                    .getAllByOtherTestWithKlassSort(otherTest,direction);
        }else if(!UtilForString.isNullOrEmpty(sort)){
            otherTestResults = otherTestResultRepository
                    .findAllByOtherTestWithSort(otherTest,sort,direction);
        }else{
            otherTestResults = otherTestResultRepository.findAllResultByOtherTest(otherTest);
        }

        setKlassNameForOtherTestResult(otherTest,otherTestResults);

        model.addAttribute("otherTest",otherTest);
        model.addAttribute("otherTestResults",otherTestResults);

        return "otherTest/showAllResult";

    }

    private void setKlassNameForOtherTestResult(OtherTest test,List<OtherTestResult> results){
        LocalDate date = test.getDate();
        results.forEach(result->{
            result.setKlassName(classStudentService.findIntegratedKlassName(result.getStudent(),date));
        });

    }


    @GetMapping("/csvDownload/{id}")
    public void downloadExcel(@PathVariable("id") Integer otherTestId, HttpServletResponse response) throws IOException {
        log.info("downLoadCSVは呼び出されています");
        List<OtherTestResult> otherTestResults = otherTestResultRepository.findAllByOtherTestId(otherTestId);


        String fileName = otherTestResults.get(0).getOtherTest().getName();

        csvService.generateOtherTestExcel(otherTestResults,fileName,response);


    }

    // CSVの値をエスケープする補助メソッド
    private String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        // カンマを含む場合はダブルクォートで囲む
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // null値を処理する補助メソッド
    private String formatValue(Integer value) {
        return value != null ? value.toString() : "";
    }

    @PostMapping("/registerOtherTestResultsFromCsv/{id}")
    public String registerOtherTestResultsFromCsv(@PathVariable("id") Integer otherTestId,
                                                     @RequestParam("file") MultipartFile file,
                                                     Model model,
                                                     RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a CSV file to upload.");
            return "error";
        }

        // student.code をキーとして otherTestResult を効率的に取得するために Map に変換
        List<OtherTestResult> otherTestResults = otherTestResultRepository.findAllByOtherTestId(otherTestId);
        log.info("otherTestResult lengh:{}", otherTestResults.size());

        Map<Long, OtherTestResult> resultMap = otherTestResults.stream()
                .filter(result -> result.getStudent().getCode() != null)
                .collect(Collectors.toMap(result -> result.getStudent().getCode(), result -> result));


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withDelimiter(',')
                    .withQuote('"')
                    .withFirstRecordAsHeader()
                    .parse(reader);


            for (CSVRecord record : records) {
                record.toMap().forEach((header, value) -> log.info("Header:{}, Value:{}, is生徒番号:{}", header, value,header.trim().equals("生徒番号")));  // 全てのカラム名と値を表示
                String stringStudentCode = record.get("生徒番号");
                log.info("stringStudentCode:{}",stringStudentCode);


                OtherTestResult existingRecord = resultMap.get(Long.valueOf(stringStudentCode));


                //studentCode が一致する otherTestResult が存在する場合のみ更新
                if (existingRecord != null) {

                    existingRecord.setJapanese(parseOrNull(record.get("国語")));
                    existingRecord.setMath(parseOrNull(record.get("数学")));
                    existingRecord.setEnglish(parseOrNull(record.get("英語")));
                    existingRecord.setSocial(parseOrNull(record.get("社会")));
                    existingRecord.setScience(parseOrNull(record.get("理科")));
                    existingRecord.setMusic(parseOrNull(record.get("音楽")));
                    existingRecord.setArt(parseOrNull(record.get("美術")));
                    existingRecord.setTech(parseOrNull(record.get("技術")));
                    existingRecord.setPe(parseOrNull(record.get("体育")));
                }
            }
            otherTestResultRepository.saveAll(otherTestResults);
            String successMessage = "その他のテストの結果の保存は成功しました！";
            redirectAttributes.addFlashAttribute("successMessage",successMessage);
            return "redirect:/otherTest/resultEdit/" + otherTestId;

        } catch (Exception e) {
            log.info("errorが出ています:{}",e.toString());
            String errorMessage = "その他のテストの結果の保存に何らかの理由で失敗しました。";
            redirectAttributes.addFlashAttribute("errorMessage",errorMessage);
            return "redirect:/otherTest/resultEdit/" + otherTestId;
        }
    }

    // 空文字を考慮し、null を返すメソッド
    private Integer parseOrNull(String value) {
        return (value == null || value.trim().isEmpty()) ? null : Integer.parseInt(value);
    }

    @PostMapping("/resultUpdate/{id}")
    public String resultUpdate(@PathVariable("id")Integer otherTestId,
                               @RequestParam Map<String, String> params,
                               RedirectAttributes redirectAttributes){

        for (String key : params.keySet()) {
            if (key.startsWith("id_")) {
                Integer id = Integer.valueOf(params.get(key));
                OtherTestResult result = otherTestResultRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid test result ID: " + id));

                // 科目ごとの点数を更新
                result.setJapanese(parseInteger(params.get("japanese_" + id)));
                result.setMath(parseInteger(params.get("math_" + id)));
                result.setEnglish(parseInteger(params.get("english_" + id)));
                result.setScience(parseInteger(params.get("science_" + id)));
                result.setSocial(parseInteger(params.get("social_" + id)));
                result.setMusic(parseInteger(params.get("music_" + id)));
                result.setArt(parseInteger(params.get("art_" + id)));
                result.setTech(parseInteger(params.get("tech_" + id)));
                result.setPe(parseInteger(params.get("pe_" + id)));
                // 保存
                otherTestResultRepository.save(result);
            }
        }

        redirectAttributes.addAttribute("currentPage",params.get("currentPage"));
        redirectAttributes.addAttribute("studentName",params.get("studentName"));
        redirectAttributes.addFlashAttribute("successMessage", "Results updated successfully!");

        // 更新後の結果一覧を再表示
        return "redirect:/otherTest/resultEdit/" + otherTestId; // otherTestIdを使用

    }

    private Integer parseInteger(String value) {
        if (value == null||value.trim().isEmpty()) {
            return null;  // null の場合は null を返す
        }
        return Integer.valueOf(value);  // 数字文字列の場合は Integer に変換
    }


    @GetMapping("/resultEdit/{id}")
    public String editOtherTestResult(@PathVariable("id")Integer otherTestId,
                                      @PageableDefault(page = 0, size = 10, sort = "otherTestSet.term", direction = Sort.Direction.DESC) Pageable pageable,
                                      @RequestParam(name="studentName",required = false)String studentName,
                                      @RequestParam(name="currentPage",required = false)Integer currentPage,

                                      Model model){
        int page = (currentPage != null) ? currentPage : pageable.getPageNumber();
        log.info("page:{}",page);
        pageable = PageRequest.of(page, pageable.getPageSize());

        OtherTest otherTest = otherTestRepository.getReferenceById(otherTestId);
        Page<OtherTestResult> otherTestResults ;
        if(studentName!=null&& !studentName.trim().isEmpty()){
            otherTestResults = otherTestResultRepository.findAllByOtherTestAndStudentName(otherTest,studentName,pageable);
        }else{
            otherTestResults = otherTestResultRepository.findAllByOtherTest(otherTest,pageable);
        }

        model.addAttribute("studentName",studentName);
        model.addAttribute("otherTestId",otherTestId);
        model.addAttribute("otherTest",otherTest);
        model.addAttribute("otherTestResults",otherTestResults);

        return "otherTest/editOtherTestResult";


    }

    //この時点で確実にaveのテスト結果はnullで作られている。
    //editでは、平均点および満点を登録する。
    @GetMapping("/edit/{id}")
    public String editOtherTest(@PathVariable("id")Integer otherTestId,
                                Model model){
        OtherTest otherTest = otherTestRepository.getReferenceById(otherTestId);

        otherTest.setSchoolId(otherTest.getSchool().getSchoolId());

        model.addAttribute("otherTest",otherTest);


        return "otherTest/editOtherTest";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute OtherTest otherTest,
                       RedirectAttributes redirectAttributes){
        otherTestService.save(otherTest);

        return "redirect:/otherTest/edit/"+otherTest.getOtherTestId();
    }





    @GetMapping("/show")
    public String showOtherTests(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @RequestParam(name="searchQuery", required = false)String searchQuery,
                                 @PageableDefault(page=0,size=20,sort="date",direction = Sort.Direction.DESC) Pageable pageable,
                                 Model model){
        Page<OtherTest> otherTests;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            otherTests = otherTestRepository.findBySearchQuery(searchQuery,pageable);
        } else {
            otherTests = otherTestRepository.findAll(pageable);
        }

        model.addAttribute("otherTests",otherTests);
        model.addAttribute("searchQuery",searchQuery);

        return "otherTest/show";

    }



    @GetMapping("/create")
    public String createOtherTest_g(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    Model model){

        List<School> schools = schoolService.findAllJuniorHighForCramSchool(userDetails.getCramSchool());

        OtherTest otherTest = new OtherTest();

        model.addAttribute("otherTest",otherTest);
        model.addAttribute("schools",schools);

        return "otherTest/createOtherTest";
    }

    //その他のテストを作成するメソッド。
    //選択された中学校と日付を用いて所属する生徒を取得してその他のテストの結果も作成して保存する。
    @Transactional
    @PostMapping("/register")
    public String registerOtherTest(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @ModelAttribute @Validated OtherTest otherTest,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes){
        CramSchool cramSchool = userDetails.getCramSchool();
        String message;
        if(result.hasErrors()){
            message="入力に間違いがあります";
            redirectAttributes.addFlashAttribute("message",message);
            return "redirect:/otherTest/create";
        }else{
            try{
                otherTest.setSchool(schoolRepository.getReferenceById(otherTest.getSchoolId()));
                otherTestRepository.save(otherTest);


                List<Student> students = studentService.getStudentForSchoolIdAndGradeAndDate(otherTest.getSchoolId(), Integer.valueOf(otherTest.getGrade()),otherTest.getDate());


                log.info("studentsのlength:{}",students.size());
                students.forEach(student -> {
                    log.info("forEachは呼び出されています:{}",student.getName());
                    OtherTestResult otherTestResult = new OtherTestResult();
                    otherTestResult.setStudent(student);
                    otherTestResult.setOtherTest(otherTest);
                    otherTestResultRepository.save(otherTestResult);
                });
                return "redirect:/otherTest/show";
            } catch (RuntimeException e) {

                redirectAttributes.addFlashAttribute("message",e.getMessage());
                return "redirect:/otherTest/create";
            }
        }
    }
}
