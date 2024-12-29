package com.example.demo.controller;

import com.example.demo.constant.Subject;
import com.example.demo.constant.SubjectNine;
import com.example.demo.constant.TotalSubjectForNine;
import com.example.demo.dto.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@Slf4j
public class RegularTestController {
    private final RegularTestResultService regularTestResultService;
    private final RegularTestResultRepository regularTestResultRepository;
    private final RegularTestService regularTestService;
    private final SchoolService schoolService;
    private final TermAndYearService termAndYearService;
    private final RegularTestConverter regularTestConverter;
    private final StudentService studentService;
    private final RegularTestSetRepository regularTestSetRepository;
    private final SchoolStudentRepository schoolStudentRepository;
    private final RegularTestRepository regularTestRepository;
    private final RegularTestResultConverter regularTestResultConverter;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;
    private final CsvService csvService;
    private final ClassRepository classRepository;
    private final UserService userService;
    private final ClassStudentService classStudentService;
    private final RegularTestSetService regularTestSetService;

    @Transactional(readOnly = true)
    @GetMapping("/regularTestSet/analysis/{id}")
    public String analyzeResultAtOnce(@PathVariable("id")Integer regularTestSetId,
                                      Model model){
        RegularTestSet regularTestSet = regularTestSetService.findById(regularTestSetId);

        CramSchool cramSchool = regularTestSet.getCramSchool();

        Integer grade = regularTestSet.getGrade();

        LocalDate testDate = termAndYearService.getDateForRegularTestSet(regularTestSet);

        LocalDate calcDate = termAndYearService.getDateForRegularTestSet(regularTestSet);

        LinkedHashMap<String,List<AverageScoreForKlass>> subjectAverages = new LinkedHashMap<>();

        for(Subject subject : Subject.values()){
            List<AverageScoreForKlass> averages =
                    regularTestResultRepository.findAverageForEachKlassForEachSubjectForSet(
                            subject.getValue(),
                            regularTestSet,
                            calcDate
                    );
            averages.forEach(average->{
                userService.setTeacherName(cramSchool,average,subject.getValue(),grade+6,testDate);
            });
            AverageScoreForKlass averageOfEveryClass =
                    regularTestResultRepository.findAverageForEveryKlassForEachSubjectForSet(
                            subject.getValue(),
                            regularTestSet,
                            calcDate
                    );
            averages.add(averageOfEveryClass);

            subjectAverages.put(subject.getValue(),averages);
        }

        for(TotalSubjectForNine totalSubject : TotalSubjectForNine.values()){
            List<AverageScoreForKlass> totalAverages =
                    regularTestResultRepository.findAverageForIntegratedKlassForTotalSubjectForSet(
                            totalSubject.getValue(),
                            regularTestSet,
                            calcDate
                    );
            AverageScoreForKlass averageOfEveryClass =
                    regularTestResultRepository.findAverageForEveryKlassForEachTotalSubjectForSet(
                            totalSubject.getValue(),
                            regularTestSet,
                            calcDate
                    );

            totalAverages.add(averageOfEveryClass);

            subjectAverages.put(totalSubject.getValue(),totalAverages);
        }

        UtilForNullRemoval.eraseNullEntry(subjectAverages);

        model.addAttribute("regularTestSet",regularTestSet);
        model.addAttribute("subjectAverages",subjectAverages);

        return "regularTest/analysisAtOnce";
    }

    //学校ごとに定期テスト結果を分析する画面へ遷移するメソッド
    @Transactional(readOnly = true)
    @GetMapping("/regularTest/analysis/{id}")
    public String analyzeResultForEachSchool(@PathVariable("id")Integer regularTestId,
                                             Model model){
        RegularTest regularTest = regularTestService.findById(regularTestId);

        LinkedHashMap<String,List<AverageScoreForKlass>> subjectAverages = new LinkedHashMap<>();

        CramSchool cramSchool = regularTest.getSchool().getCramSchool();
        Integer grade = regularTest.getRegularTestSet().getGrade();
        LocalDate testDate = regularTest.getDate();

        for(Subject subject : Subject.values()){
            List<AverageScoreForKlass> averages =
                    regularTestResultRepository.findAverageForEachKlassForEachSubject(
                            subject.getValue(),
                            regularTest,
                            testDate
                    );
            averages.forEach(average->{
                userService.setTeacherName(cramSchool,average,subject.getValue(),grade+6,testDate);
            });

            AverageScoreForKlass averageOfEveryClass =
                    regularTestResultRepository.findAverageForEveryKlassForEachSubject(
                            subject.getValue(),
                            regularTest,
                            testDate
                    );
            averages.add(averageOfEveryClass);

            subjectAverages.put(subject.getValue(),averages);
        }

        for(TotalSubjectForNine totalSubject : TotalSubjectForNine.values()){
            List<AverageScoreForKlass> totalAverages =
                    regularTestResultRepository.findAverageForIntegratedKlassForTotalSubject(
                            totalSubject.getValue(),
                            regularTest,
                            testDate
                    );
            AverageScoreForKlass averageOfEveryClass =
                    regularTestResultRepository.findAverageForEveryClassForEachTotalSubject(
                            totalSubject.getValue(),
                            regularTest,
                            testDate
                    );
            totalAverages.add(averageOfEveryClass);
            subjectAverages.put(totalSubject.getValue(),totalAverages);
        }

        UtilForNullRemoval.eraseNullEntry(subjectAverages);

        model.addAttribute("regularTest",regularTest);
        model.addAttribute("subjectAverages",subjectAverages);

        return "regularTest/analysisForEachSchool";
    }

    @Transactional(readOnly = true)
    @GetMapping("/regularTestSet/all_result/{id}")
    public String regularTestResult(@PathVariable("id")Integer regularTestSetId,
                                    @RequestParam(name="sort",required = false)String sort,
                                    @RequestParam(name="order",required = false)Integer direction,
                                    Model model){
        RegularTestSet regularTestSet = regularTestSetRepository.getReferenceById(regularTestSetId);

        List<RegularTestResult> regularTestResults;
        if("klass".equals(sort)){
            regularTestResults = regularTestResultService
                    .findAllByRegularTestSetWithKlassSort(regularTestSet,direction);
        }else if(!UtilForString.isNullOrEmpty(sort)){
            regularTestResults = regularTestResultRepository
                    .findAllResultsBySetIdWithSort(regularTestSet,sort,direction);
        }else{
            regularTestResults = regularTestResultRepository
                    .findAllByRegularTestSetId(regularTestSetId);
        }



        setKlassNameForListForSet(regularTestResults);
        model.addAttribute("regularTestSet",regularTestSet);
        model.addAttribute("regularTestResults",regularTestResults);

        return "regularTest/showAllResultSet";
    }

    @Transactional(readOnly = true)
    @GetMapping("/regularTest/all_result/{id}")
    public String showResultBySchool(@PathVariable("id")Integer regularTestId,
                                     @RequestParam(name="sort",required = false)String sort,
                                     @RequestParam(name="order",required = false)Integer direction,
                                     Model model){
        RegularTest regularTest = regularTestRepository.getReferenceById(regularTestId);
        List<RegularTestResult> regularTestResults;
        if("klass".equals(sort)){
            regularTestResults = regularTestResultService
                    .findAllByRegularTestWithKlassSort(regularTest,direction);
        }else if(!UtilForString.isNullOrEmpty(sort)){
            regularTestResults = regularTestResultRepository
                    .findAllByRegularTestWithSort(regularTest,sort,direction);
        }else{
            regularTestResults = regularTestResultRepository
                    .findAllByRegularTest(regularTest);
        }

        //テスト実施日に所属していたクラスを入れることに注意
        setKlassNameForList(regularTestResults,regularTest);
        model.addAttribute("regularTestSet",regularTest.getRegularTestSet());
        model.addAttribute("regularTest",regularTest);
        model.addAttribute("regularTestResults",regularTestResults);

        return "regularTest/showAllResultBySchool";
    }

    private void setKlassNameForListForSet(List<RegularTestResult> results){
        results.forEach(result->{
            result.setKlassName(classStudentService.findIntegratedKlassName(result.getStudent(),result.getRegularTest().getDate()));
        });
    }

    //テスト実施時に所属していたクラスを入れるメソッド
    private void setKlassNameForList(List<RegularTestResult> results,RegularTest test){
        LocalDate testDate = test.getDate();

        results.forEach(result->{
            result.setKlassName(classStudentService.findIntegratedKlassName(result.getStudent(),testDate));
        });

    }

    @GetMapping("/regularTest/csvDownload/{id}")
    public void downloadCsv(@PathVariable("id")Integer regularTestSetId,
                            @RequestParam(name="klassId",required = false) Integer klassId,
                            HttpServletResponse response) throws IOException{
        RegularTestSet regularTestSet = regularTestSetRepository.getReferenceById(regularTestSetId);
        List<RegularTestResult> results;
        if(klassId==null){
            results = regularTestResultRepository.findAllByRegularTestSetIdExcludingAverageMan(regularTestSetId);
        }else{
            LocalDate date = termAndYearService.getTodayAsLocalDate();
            results = regularTestResultRepository.findAllResultsByRegularTestSetIdAndKlassIdExcludingAverageMan(regularTestSetId,klassId,date);
        }


        String fileName = String.format("%s年度%s年生%s学期%sテスト情報",
                regularTestSet.getTerm(),
                regularTestSet.getGrade(),
                regularTestSet.getSemester(),
                regularTestSet.getMorE()
                );

        csvService.generateRegularTestExcel(results,fileName,response);
    }

    @GetMapping("/regularTest/csvDownload/forEachSchool/{id}")
    public void downloadCsvForEachSchool(@PathVariable("id")Integer regularTestId,
                            HttpServletResponse response) throws IOException{
        RegularTest regularTest = regularTestRepository.getReferenceById(regularTestId);
        RegularTestSet regularTestSet = regularTest.getRegularTestSet();
        List<RegularTestResult> results = regularTestResultRepository.findAllByRegularTest(regularTest);

        String fileName = String.format("%s%s年度%s年生%s学期%sテスト情報",
                regularTest.getSchool().getName(),
                regularTestSet.getTerm(),
                regularTestSet.getGrade(),
                regularTestSet.getSemester(),
                regularTestSet.getMorE()
        );

        csvService.generateRegularTestExcel(results,fileName,response);
    }

    @GetMapping("/showAllRegularTest")
    public String showAllRegularTest(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @PageableDefault(page = 0, size = 10, sort = "regularTestSet.term", direction = Sort.Direction.DESC) Pageable pageable,
                                     Model model) {
        Page<RegularTest> regularTests = regularTestRepository.findAllByCramSchool(userDetails.getCramSchool(), pageable);

        model.addAttribute("regularTests", regularTests);

        return "regularTest/showAllRegularTest";
    }

    //OK
    @GetMapping("/showAllRegularTestInChunks")
    public String showAllRegularTestInChunks(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @PageableDefault(page = 0, size = 10, sort = "term", direction = Sort.Direction.DESC) Pageable pageable,
                                             Model model) {
        Page<RegularTestSet> regularTestSets = regularTestSetRepository.findAllByCramSchool(userDetails.getCramSchool(), pageable);
        model.addAttribute("regularTestSets", regularTestSets);

        return "regularTest/showAllRegularTestInChunks";
    }

    //OK
    @Transactional
    @GetMapping("/regularTestEdit/{id}")
    public String regularTestEdit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @PathVariable("id") Integer regularTestId, Model model) {

        RegularTest regularTest = regularTestService.fetchById(regularTestId);

        regularTest.setRegularTestSetId(regularTest.getRegularTestSet().getRegularTestSetId());
        regularTest.setSchoolId(regularTest.getSchool().getSchoolId());

        model.addAttribute("regularTest", regularTest);

        return "regularTest/edit_average_and_fullScore";

    }

    //平均と満点の情報を保存する
    @PostMapping("/regular-test/save")
    public String saveRegularTestInformation(@ModelAttribute RegularTest regularTest,
                                             RedirectAttributes redirectAttributes){

        regularTestService.save(regularTest);

        return "redirect:/regularTestEdit/"+regularTest.getRegularTestId();
    }




    //OK
    @GetMapping("/createRegularTest")
    public String createRegularTests(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        //ログインユーザーのcramSchoolIdと紐づく中学校をすべて選択出来る
        List<School> schools = schoolService.findAllJuniorHighForCramSchool(userDetails.getCramSchool());
        model.addAttribute("schools", schools);


        return "regularTest/createRegularTestAllAtOnce";
    }

    //todo:過去の定期テスト作成画面をつくるメソッド
    @GetMapping("/createPastRegularTest")
    public String createPastRegularTests(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                         Model model) {
        List<School> schools = schoolService.findAllJuniorHighForCramSchool(userDetails.getCramSchool());
        model.addAttribute("schools", schools);

        return "regularTest/createPastRegularTest";
    }

    @Transactional
    @PostMapping("/doCreateRegularTestSetAndRegularTests")
    public String doCreateRegularTestSetAndRegularTest(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam List<Integer> selectedSchoolIds,
            @RequestParam("grade") Integer grade,
            @RequestParam("semester") Integer semester,
            @RequestParam("isMid") Integer isMid,
            RedirectAttributes redirectAttributes) {

        CramSchool cramSchool = userDetails.getCramSchool();
        Integer thisTerm = termAndYearService.getTerm();
        LocalDate testDate = termAndYearService.getTodayAsLocalDate();

        return createRegularTests(cramSchool, selectedSchoolIds, grade, semester, isMid, thisTerm, testDate);
    }

    @PostMapping("/doCreatePastRegularTestSetAndRegularTests")
    public String doCreatePastRegularTestSetAndRegularTest(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam List<Integer> selectedSchoolIds,
            @RequestParam("term") Integer term,
            @RequestParam("grade") Integer grade,
            @RequestParam("semester") Integer semester,
            @RequestParam("isMid") Integer isMid,
            RedirectAttributes redirectAttributes) {

        CramSchool cramSchool = userDetails.getCramSchool();
        LocalDate testDate = RegularTestService.getTestDate(term, semester, isMid);

        return createRegularTests(cramSchool, selectedSchoolIds, grade, semester, isMid, term, testDate);
    }

    private String createRegularTests(
            CramSchool cramSchool,
            List<Integer> selectedSchoolIds,
            Integer grade,
            Integer semester,
            Integer isMid,
            Integer term,
            LocalDate testDate) {

        RegularTestSet regularTestSet = getOrCreateRegularTestSet(cramSchool, grade, semester, isMid, term);

        for (Integer schoolId : selectedSchoolIds) {
            School school = schoolService.fetchById(schoolId);
            createRegularTestIfNotExists(school, regularTestSet, testDate, cramSchool, grade, term);
        }

        return "redirect:/showAllRegularTest";
    }

    private RegularTestSet getOrCreateRegularTestSet(
            CramSchool cramSchool,
            Integer grade,
            Integer semester,
            Integer isMid,
            Integer term) {

        Optional<RegularTestSet> optionalRegularTestSet = regularTestSetRepository
                .findByGradeAndTermAndIsMidAndSemesterAndCramSchool(grade, term, isMid, semester, cramSchool);

        if (optionalRegularTestSet.isPresent()) {
            return optionalRegularTestSet.get();
        }

        RegularTestSet regularTestSet = new RegularTestSet();
        regularTestSet.setCramSchool(cramSchool);
        regularTestSet.setGrade(grade);
        regularTestSet.setSemester(semester);
        regularTestSet.setTerm(term);
        regularTestSet.setIsMid(isMid);
        RegularTestSet savedRegularTestSet = regularTestSetRepository.save(regularTestSet);
        regularTestSetRepository.flush();
        return  savedRegularTestSet;
    }

    private void createRegularTestIfNotExists(
            School school,
            RegularTestSet regularTestSet,
            LocalDate testDate,
            CramSchool cramSchool,
            Integer grade,
            Integer term) {

        Optional<RegularTest> optionalRegularTest = regularTestRepository
                .getBySchoolAndRegularTestSet(school, regularTestSet);

        if (optionalRegularTest.isPresent()) {
            return;
        }

        RegularTest regularTest = createRegularTest(school, regularTestSet, testDate);
        createRegularTestResults(regularTest, school, cramSchool, grade, term, testDate);
    }

    private RegularTest createRegularTest(
            School school,
            RegularTestSet regularTestSet,
            LocalDate testDate) {

        if (school == null || school.getSchoolId() == null) {
            throw new IllegalArgumentException("School or SchoolId must not be null");
        }
        if (regularTestSet == null || regularTestSet.getRegularTestSetId() == null) {
            throw new IllegalArgumentException("RegularTestSet or RegularTestSetId must not be null");
        }

        RegularTest regularTest = new RegularTest();
        regularTest.setRegularTestSet(regularTestSet);
        regularTest.setSchool(school);
        regularTest.setDate(testDate);
        RegularTest savedRegularTest = regularTestRepository.save(regularTest);
        regularTestSetRepository.flush();
        return  savedRegularTest;
    }

    private void createRegularTestResults(
            RegularTest regularTest,
            School school,
            CramSchool cramSchool,
            Integer grade,
            Integer term,
            LocalDate testDate) {

        List<RegularTestResult> regularTestResults = new ArrayList<>();



        // Add test results for regular students
        int el1 = term != termAndYearService.getTerm()
                ? termAndYearService.getWhenEnteredElementarySchoolForJuniorHighSchoolStudentForPast(term, grade)
                : termAndYearService.getWhenEnteredElementarySchoolForJuniorHighSchoolStudent(grade);

        List<Integer> studentIds = schoolStudentRepository
                .findSchoolStudentBySchoolAndDateAndEl1(school.getSchoolId(), el1, testDate);

        for (Integer studentId : studentIds) {
            regularTestResults.add(createRegularTestResult(regularTest, studentService.getById(studentId)));
        }



        regularTestResultRepository.saveAll(regularTestResults);
    }



    private RegularTestResult createRegularTestResult(RegularTest regularTest, Student student) {
        RegularTestResult result = new RegularTestResult();
        result.setRegularTest(regularTest);
        result.setStudent(student);
        return result;
    }
    //学校ごとに編集する画面
    //OK
    @GetMapping("/regularTestResultEdit/{id}")
    public String regularTestResultEdit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @PageableDefault(page = 0, size = 10, sort = "studentId", direction = Sort.Direction.DESC) Pageable pageable,
                                        @PathVariable("id") Integer regularTestId,
                                        @RequestParam(name="klassId",required = false) Integer klassId,
                                        @RequestParam(name = "studentName", required = false) String studentName,
                                        @RequestParam(name = "currentPage", required = false) Integer currentPage,
                                        Model model) {
        CramSchool cramSchool = userDetails.getCramSchool();
        LocalDate date = termAndYearService.getTodayAsLocalDate();
        List<Klass> allClassOptions = classRepository.getAllKlassByCramSchool(cramSchool);
        int page = (currentPage != null) ? currentPage : pageable.getPageNumber();
        pageable = PageRequest.of(page, pageable.getPageSize());

        // クラスIDまたは学生名でフィルタリング
        Page<RegularTestResult> regularTestResults;
        if (klassId != null) {
            // クラスIDでフィルタリング
            regularTestResults = regularTestResultRepository.findByRegularTestIdAndKlassId(regularTestId, klassId,date, pageable);
        } else if (studentName != null && !studentName.trim().isEmpty()) {
            // 学生名でフィルタリング
            regularTestResults = regularTestResultRepository.findByRegularTestIdAndStudentName(regularTestId, studentName, pageable);
        } else {
            // フィルタリングなし
            regularTestResults = regularTestResultRepository.findByRegularTestId(regularTestId, pageable);
        }

        setKlassNameForPage(regularTestResults);

        RegularTest regularTest = regularTestService.fetchById(regularTestId);

        model.addAttribute("currentPage", page);
        model.addAttribute("regularTestResults", regularTestResults);
        model.addAttribute("regularTest", regularTest);
        model.addAttribute("studentName", studentName);
        model.addAttribute("klassId", klassId);
        model.addAttribute("allClassOptions", allClassOptions);

        return "regularTest/editResultBySchool";
    }


    public void setKlassNameForPage(Page<RegularTestResult> results){
        LocalDate date = LocalDate.now();
        results.forEach(result -> {
            result.setKlassName(classStudentService.findIntegratedKlassName(result.getStudent(),date));
        });
    }

    //個別に編集する画面から来るメソッド
    //OK
    @PostMapping("/RegularTestResultUpdate/{id}")
    public String regularTestResultUpdate(@RequestParam(name = "currentPage", required = false) Integer currentPage,
                                          @PathVariable("id") Integer regularTestId,
                                          @RequestParam Map<String, String> params,
                                          @RequestParam(name = "studentName", required = false) String studentName,
                                          RedirectAttributes redirectAttributes) {
        try {
            for (String key : params.keySet()) {
                if (key.startsWith("id_")) {
                    Integer id = Integer.valueOf(params.get(key));
                    RegularTestResult result = regularTestResultRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid test result ID: " + id));

                    // 科目ごとの点数を更新（null チェックを追加）
                    result.setJapanese(parseInteger(params.get("japanese_" + id)));
                    result.setMath(parseInteger(params.get("math_" + id)));
                    result.setEnglish(parseInteger(params.get("english_" + id)));
                    result.setScience(parseInteger(params.get("science_" + id)));
                    result.setSocial(parseInteger(params.get("social_" + id)));
                    result.setMusic(parseInteger(params.get("music_" + id)));
                    result.setArt(parseInteger(params.get("art_" + id)));
                    result.setTech(parseInteger(params.get("tech_" + id)));
                    result.setPe(parseInteger(params.get("pe_" + id)));

                    regularTestResultRepository.save(result);
                }
            }

            redirectAttributes.addFlashAttribute("successMessage", "結果を保存しました。");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "データの保存中にエラーが発生しました。入力内容を確認してください。");
        }

        // リダイレクトURLの構築
        StringBuilder redirectUrl = new StringBuilder()
                .append("redirect:/regularTestResultEdit/")
                .append(regularTestId);

        // クエリパラメータの追加
        redirectUrl.append("?currentPage=")
                .append(currentPage != null ? currentPage : 0);

        // studentNameが存在する場合、エンコードして追加
        if (studentName != null && !studentName.isEmpty()) {
            try {
                String encodedName = URLEncoder.encode(studentName, StandardCharsets.UTF_8.toString());
                redirectUrl.append("&studentName=").append(encodedName);
            } catch (UnsupportedEncodingException e) {
                // UTF-8は必ずサポートされているため、この例外は実際には発生しない
                log.error("Error encoding studentName", e);
            }
        }

        return redirectUrl.toString();
    }


    //いっぺんに編集する画面
    @GetMapping("/regularTestResultChunkEdit/{id}")
    public String regularTestResultChunkEdit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @PageableDefault(page = 0, size = 10, sort = "studentId", direction = Sort.Direction.DESC) Pageable pageable,
                                             @PathVariable("id") Integer regularTestSetId,
                                             @RequestParam(name="klassId",required = false)Integer klassId,
                                             @RequestParam(name = "studentName", required = false) String studentName,
                                             @RequestParam(name = "currentPage", required = false) Integer currentPage,
                                             Model model) {
        //currentPageを受け取ったときはそれでpageableを作り直す
        int page = (currentPage != null) ? currentPage : pageable.getPageNumber();
        pageable = PageRequest.of(page, pageable.getPageSize());
        CramSchool cramSchool = userDetails.getCramSchool();
        LocalDate date = termAndYearService.getTodayAsLocalDate();
        List<Klass> allClassOptions = classRepository.getAllKlassByCramSchool(cramSchool);

        // クラスIDまたは学生名でフィルタリング
        Page<RegularTestResult> regularTestResults;
        if (klassId != null) {
            // クラスIDでフィルタリング
            regularTestResults = regularTestResultRepository.findByRegularTestSetIdAndKlassId(regularTestSetId, klassId,date, pageable);
        } else if (studentName != null && !studentName.trim().isEmpty()) {
            // 学生名でフィルタリング
            regularTestResults = regularTestResultRepository.findByRegularTestSetIdAndStudentName(regularTestSetId, studentName, pageable);
        } else {
            // フィルタリングなし
            regularTestResults = regularTestResultRepository.findByRegularTestSetId(regularTestSetId, pageable);
        }

        setKlassNameForPage(regularTestResults);

        Optional<RegularTestSet> optionalRegularTestSet = regularTestSetRepository.findById(regularTestSetId);
        optionalRegularTestSet.ifPresent(regularTestSet -> {
            model.addAttribute("regularTestSet", regularTestSet);
        });

        model.addAttribute("regularTestResults", regularTestResults);
        model.addAttribute("regularTestSetId", regularTestSetId);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("studentName", studentName);
        model.addAttribute("klassId", klassId);
        model.addAttribute("allClassOptions",allClassOptions);

        return "regularTest/editResultAtOnce";
    }

    //OK
    @PostMapping("/RegularTestResultUpdateAtOnce/{id}")
    public String regularTestResultUpdateAtOnce(@PathVariable("id") Integer regularTestSetId,
                                                @RequestParam Map<String, String> params, RedirectAttributes redirectAttributes) {


        for (String key : params.keySet()) {
            if (key.startsWith("id_")) {
                Integer id = Integer.valueOf(params.get(key));
                RegularTestResult result = regularTestResultRepository.findById(id)
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
                regularTestResultRepository.save(result);
            }
        }
        redirectAttributes.addAttribute("currentPage",params.get("currentPage"));
        redirectAttributes.addAttribute("studentName",params.get("studentName"));
        // 成功メッセージを設定
        redirectAttributes.addFlashAttribute("successMessage", "Results updated successfully!");

        // 更新後の結果一覧を再表示
        return "redirect:/regularTestResultChunkEdit/" + regularTestSetId; // regularTestIdを使用
    }

    private Integer parseInteger(String value) {
        if (value == null||value.trim().isEmpty()) {
            return null;  // null の場合は null を返す
        }
        return Integer.valueOf(value);  // 数字文字列の場合は Integer に変換
    }

    //todo:循環参照エラーがなぜか出ている？@Dataを外し@Getter,@Setterをつけるなどして解消した
    @PostMapping("/registerRegularTestResultsFromCsv/{id}")
    public String registerRegularTestResultsFromCsv(
            @PathVariable("id") Integer regularTestSetId,
            @RequestParam("file") MultipartFile file,
            Model model) {

        return csvService.processRegularTestCsvUpload(file, regularTestSetId, true);
    }

    @PostMapping("/registerRegularTestResultsFromCsv/forEachSchool/{id}")
    public String registerRegularTestResultsFromCsvForEachSchool(
            @PathVariable("id") Integer regularTestId,
            @RequestParam("file") MultipartFile file,
            Model model) {

        return csvService.processRegularTestCsvUpload(file, regularTestId, false);
    }



}


