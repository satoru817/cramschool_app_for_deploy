//MEMO:複数校舎への対応完了
package com.example.demo.controller.academic;

import com.example.demo.constant.Subject;
import com.example.demo.constant.TotalSubjectForNine;
import com.example.demo.dto.AverageScoreForKlass;
import com.example.demo.dto.SchoolRecordDTO;
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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@Slf4j
public class SchoolRecordController {
    private final SchoolRecordService schoolRecordService;
    private final SchoolRecordResultService schoolRecordResultService;
    private final SchoolRecordSetRepository schoolRecordSetRepository;
    private final SchoolService schoolService;
    private final TermAndYearService termAndYearService;
    private final StudentRepository studentRepository;
    private final SchoolRecordRepository schoolRecordRepository;
    private final SchoolStudentRepository schoolStudentRepository;
    private final SchoolRecordResultRepository schoolRecordResultRepository;
    private final SchoolRepository schoolRepository;
    private final CsvService csvService;
    private final ClassRepository classRepository;
    private final ClassStudentService classStudentService;
    private final SchoolRecordSetService schoolRecordSetService;
    private final UserService userService;


    @Transactional(readOnly = true)
    @GetMapping("/schoolRecordSet/analysis/{id}")
    public String analyzeResultAtOnce(@PathVariable("id")Integer schoolRecordSetId,
                                      Model model){
        SchoolRecordSet schoolRecordSet = schoolRecordSetService.findById(schoolRecordSetId);

        CramSchool cramSchool = schoolRecordSet.getCramSchool();

        Integer grade = schoolRecordSet.getGrade();

        LocalDate calcDate = termAndYearService.getDateForSchoolRecordSet(schoolRecordSet);

        LinkedHashMap<String,List<AverageScoreForKlass>> subjectAverages = new LinkedHashMap<>();

        for(Subject subject :Subject.values()){
            List<AverageScoreForKlass> averages =
                    schoolRecordResultRepository.findAverageForEachKlassForEachSubjectForSet(
                            subject.getValue(),
                            schoolRecordSet,
                            calcDate
                    );
            averages.forEach(average->{
                userService.setTeacherName(cramSchool,average,subject.getValue(),grade+6,calcDate);
            });

            AverageScoreForKlass averageOfEveryClass =
                    schoolRecordResultRepository.findAverageForEveryKlassForEachSubjectForSet(
                            subject.getValue(),
                            schoolRecordSet,
                            calcDate
                    );

            averages.add(averageOfEveryClass);

            subjectAverages.put(subject.getValue(),averages);
        }

        for(TotalSubjectForNine totalSubject : TotalSubjectForNine.values()){
            List<AverageScoreForKlass> averages =
                    schoolRecordResultRepository.findAverageForIntegratedKlassForTotalSubjectForSet(
                            totalSubject.getValue(),
                            schoolRecordSet,
                            calcDate
                    );

            AverageScoreForKlass averageOfEveryClass =
                    schoolRecordResultRepository.findAverageForEveryKlassForEachTotalSubjectForSet(
                            totalSubject.getValue(),
                            schoolRecordSet,
                            calcDate
                    );

            averages.add(averageOfEveryClass);

            subjectAverages.put(totalSubject.getValue(),averages);
        }

        UtilForNullRemoval.eraseNullEntry(subjectAverages);

        model.addAttribute("schoolRecordSet",schoolRecordSet);
        model.addAttribute("subjectAverages",subjectAverages);

        return "schoolRecord/analysisAtOnce";
    }


    @Transactional(readOnly = true)
    @GetMapping("/schoolRecord/analysis/{id}")
    public String analyzeResultForEachSchool(@PathVariable("id")Integer schoolRecordId,
                          Model model){

        SchoolRecord schoolRecord = schoolRecordService.findById(schoolRecordId);

        LocalDate calcDate = termAndYearService.getDateForSchoolRecord(schoolRecord);

        CramSchool cramSchool = schoolRecord.getSchool().getCramSchool();

        Integer grade = schoolRecord.getSchoolRecordSet().getGrade();

        LinkedHashMap<String,List<AverageScoreForKlass>> subjectAverages = new LinkedHashMap<>();

        for(Subject subject : Subject.values()){
            List<AverageScoreForKlass> averages =
                    schoolRecordResultRepository.findAverageForEachKlassForEachSubject(
                            subject.getValue(),
                            schoolRecord,
                            calcDate
                    );
            averages.forEach(average->{
                userService.setTeacherName(cramSchool,average,subject.getValue(),grade+6,calcDate);
            });

            AverageScoreForKlass averageOfEveryClass =
                    schoolRecordResultRepository.findAverageForEveryKlassForEachSubject(
                            subject.getValue(),
                            schoolRecord,
                            calcDate
                    );
            averages.add(averageOfEveryClass);
            subjectAverages.put(subject.getValue(),averages);
        }


        for(TotalSubjectForNine totalSubject : TotalSubjectForNine.values()){
            List<AverageScoreForKlass> totalAverages =
                    schoolRecordResultRepository.findAverageForIntegratedKlassForTotalSubject(
                            totalSubject.getValue(),
                            schoolRecord,
                            calcDate
                    );
            AverageScoreForKlass averageOfEveryClass =
                    schoolRecordResultRepository.findAverageForEveryClassForEachTotalSubject(
                            totalSubject.getValue(),
                            schoolRecord,
                            calcDate
                    );

            totalAverages.add(averageOfEveryClass);
            subjectAverages.put(totalSubject.getValue(),totalAverages);
        }

        UtilForNullRemoval.eraseNullEntry(subjectAverages);

        model.addAttribute("schoolRecord",schoolRecord);
        model.addAttribute("subjectAverages",subjectAverages);

        return "schoolRecord/analysisForEachSchool";
    }


    @GetMapping("/schoolRecord/csvDownload/eachSchool/{id}")
    public void downloadCsvForEachSchool(@PathVariable("id") Integer schoolRecordId,
                                         HttpServletResponse response) throws IOException {
        // 必要なデータの取得
        SchoolRecord schoolRecord = schoolRecordRepository.getReferenceById(schoolRecordId);
        SchoolRecordSet schoolRecordSet = schoolRecord.getSchoolRecordSet();
        List<SchoolRecordResult> results = schoolRecordResultRepository.findAllBySchoolRecord(schoolRecord);

        // ファイル名の生成
        String fileName = String.format("%s%s年度%s年生%s学期_内申点情報",
                schoolRecord.getSchool().getName(),
                schoolRecordSet.getTerm(),
                schoolRecordSet.getGrade(),
                schoolRecordSet.getSemester());

        // CSV生成と出力
        csvService.generateSchoolRecordExcel(results, fileName, response);
    }

    @GetMapping("/schoolRecord/csvDownload/{id}")
    public void downloadCsv(@PathVariable("id") Integer schoolRecordSetId,
                            @RequestParam(name="klassId",required = false)Integer klassId,
                            HttpServletResponse response) throws IOException {
        SchoolRecordSet schoolRecordSet = schoolRecordSetRepository.getReferenceById(schoolRecordSetId);
        List<SchoolRecordResult> results;

        if(klassId == null){
            results = schoolRecordResultRepository.findAllBySchoolRecordSetIdExcludingAverageMan(schoolRecordSetId);
        }else{
            LocalDate date = termAndYearService.getTodayAsLocalDate();
            results = schoolRecordResultRepository.findAllResultBySchoolRecordSetIdAndKlassIdExcludingAverageMan(schoolRecordSetId,klassId,date);
        }
        // 必要なデータの取得



        // ファイル名の生成
        String fileName = String.format("%s年度%s年生%s学期_内申点情報",
                schoolRecordSet.getTerm(),
                schoolRecordSet.getGrade(),
                schoolRecordSet.getSemester());

        // CSV生成と出力
        csvService.generateSchoolRecordExcel(results, fileName, response);
    }





    //SchoolRecordをinsertするとともに、SchoolRecordResultもオール0でインサート
    @GetMapping("/registerSchoolRecord")
    public String registerSchoolRecord(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       Model model){
        
        return "schoolRecord/schoolRecordRegister";
    }

    //OK
    @Transactional
    @PostMapping("/createSchoolRecordSet")
    public String createSchoolRecordSet(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute SchoolRecordSet schoolRecordSet,
            Model model) {

        schoolRecordSet.setCramSchool(userDetails.getCramSchool());//追加
        schoolRecordSetRepository.save(schoolRecordSet);//ここまでOK

        //ログイン教室に関連する中学校をすべて取得　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
        List<School> jhs = schoolService.findAllJuniorHighForCramSchool(userDetails.getCramSchool());//改修した。
        for(School jh: jhs){
            SchoolRecord schoolRecord = new SchoolRecord();
            schoolRecord.setSchool(jh);
            schoolRecord.setSchoolRecordSet(schoolRecordSet);
            schoolRecordService.save(schoolRecord);
            Integer el1 = termAndYearService.getEl1ForJhUsingTermAndGrade(schoolRecordSet.getTerm(),schoolRecordSet.getGrade());
            log.info("el1:{}",el1);
            List<Integer> studentIds = schoolStudentRepository
                    .findSchoolStudentBySchoolAndDateAndEl1(jh.getSchoolId(),el1,termAndYearService.getSemesterEndDate(schoolRecordSet.getTerm(),schoolRecordSet.getSemester()));
            log.info("studentIds:{}",studentIds);
            //学校、el1、学期最終日を使って対応する生徒のIdを選び出している。
            //循環参照を避けるためにこのようなIdをだしてから再度そのstudentを呼ぶような余計な操作をしている。出来れば一発で終わらせたい所。
            for(Integer studentId : studentIds){
                SchoolRecordResult schoolRecordResult = new SchoolRecordResult();
                schoolRecordResult.setSchoolRecord(schoolRecord);
                schoolRecordResult.setStudent(studentRepository.getById(studentId));
                schoolRecordResult.setJapanese(null); // 国語
                schoolRecordResult.setMath(null); // 数学
                schoolRecordResult.setEnglish(null); // 英語
                schoolRecordResult.setScience(null); // 理科
                schoolRecordResult.setSocial(null); // 社会
                schoolRecordResult.setMusic(null); // 音楽
                schoolRecordResult.setArt(null); // 美術
                schoolRecordResult.setTech(null); // 技術
                schoolRecordResult.setPe(null); // 体育
                schoolRecordResultService.save(schoolRecordResult);
            }
        }

        model.addAttribute("success", "School Record Set が作成されました。");
        
        return "redirect:/showSchoolRecordSets"; // 適切なリダイレクト先を指定
    }

    //OK
    @GetMapping("/showSchoolRecordSets")
    public String showSchoolRecordSets(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(page=0,size=10,sort={"term","grade","semester"},direction= Sort.Direction.DESC) Pageable pageable,Model model){

        Page<SchoolRecordSet> schoolRecordSets = schoolRecordSetRepository.findAllByCramSchool(userDetails.getCramSchool(),pageable);
        model.addAttribute("schoolRecordSets",schoolRecordSets);
        
        return "schoolRecord/showSchoolRecordSets";
    }

    //OK
    @GetMapping("/showSchoolRecords")
    public String showSchoolRecords(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(page=0,size=10,sort={"schoolRecordSet.term","schoolRecordSet.grade","schoolRecordSet.semester"},direction=Sort.Direction.DESC)Pageable pageable,
            Model model){

        Page<SchoolRecord> schoolRecordPage = schoolRecordRepository.findAllByCramSchool(userDetails.getCramSchool(),pageable);

        List<SchoolRecordDTO> schoolRecordDTOS = new ArrayList<>();
        for(SchoolRecord schoolRecord: schoolRecordPage){
            schoolRecordDTOS.add(SchoolRecordService.convertToDTO(schoolRecord));
        }

        model.addAttribute("schoolRecordDTOS",schoolRecordDTOS);
        model.addAttribute("currentPage", schoolRecordPage.getNumber());
        model.addAttribute("totalPages", schoolRecordPage.getTotalPages());
        
        return "schoolRecord/showSchoolRecords";

    }


    //MEMO:schoolRecordSetのidから対応するschoolRecordResultをすべて作成する。オールzeroで作成して、画面で値を入れる。
    //todo:klassIdによる条件分岐を追加する必要がある。
    @GetMapping("/schoolRecordResultSetRegister/{id}")
    public String schoolRecordResultSetRegister(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @PathVariable("id")Integer schoolRecordSetId,
                                                @RequestParam(name="klassId" , required = false)Integer klassId,
                                                @RequestParam(name="studentName",required = false)String studentName,
                                                @RequestParam(name="successMessage", required=false)String successMessage,
                                                Model model,
                                                @PageableDefault(page = 0,size = 10, sort = "studentId", direction = Sort.Direction.DESC)Pageable pageable,
                                                @RequestParam(name="currentPage",required = false)Integer currentPage){

        int page = (currentPage != null) ? currentPage : pageable.getPageNumber();
        pageable = PageRequest.of(page, pageable.getPageSize());
        CramSchool cramSchool = userDetails.getCramSchool();
        LocalDate date = termAndYearService.getTodayAsLocalDate();

        List<Klass> allClassOptions = classRepository.getAllKlassByCramSchool(cramSchool);

        Page<SchoolRecordResult> schoolRecordResultsPage;

        // studentNameとklassIdの条件分岐
        if (studentName != null && !studentName.trim().isEmpty()) {
            // 生徒名での検索
            schoolRecordResultsPage = schoolRecordResultRepository.findAllBySchoolRecordSetIdAndNameLike(schoolRecordSetId, studentName, pageable);
        } else if (klassId != null) {
            // クラスIDでの検索
            schoolRecordResultsPage = schoolRecordResultRepository.findAllBySchoolRecordSetIdAndKlassId(schoolRecordSetId, klassId,date, pageable);
        } else {
            // 検索条件なし
            schoolRecordResultsPage = schoolRecordResultRepository.findAllSchoolRecordBySchoolRecordSetId(schoolRecordSetId, pageable);
        }

        setKlassNameForPage(schoolRecordResultsPage);

        SchoolRecordSet schoolRecordSet = schoolRecordSetRepository.getReferenceById(schoolRecordSetId);

        if(successMessage!=null){
            model.addAttribute("successMessage",successMessage);
        }
        model.addAttribute("schoolRecordResultsPage",schoolRecordResultsPage);
        model.addAttribute("schoolRecordSet",schoolRecordSet);
        model.addAttribute("currentPage",page);
        model.addAttribute("studentName",studentName);
        model.addAttribute("allClassOptions",allClassOptions);
        model.addAttribute("klassId",klassId);

        return "schoolRecord/schoolRecordResultChunkEdit";
    }

    public void setKlassNameForPage(Page<SchoolRecordResult> results){
        LocalDate date = LocalDate.now();
        results.forEach(result -> {
           result.setKlassName(classStudentService.findIntegratedKlassName(result.getStudent(),date));
        });
    }

    public void setKlassNameForList(List<SchoolRecordResult> results){
        LocalDate date = LocalDate.now();

        results.forEach(result->{
            result.setKlassName(classStudentService.findIntegratedKlassName(result.getStudent(),date));
        });
    }

    //学校ごとに全件表示する。
    @Transactional(readOnly = true)
    @GetMapping("/schoolRecordResult/showAll/{id}")
    public String schoolRecordResultShowAllForEachSchool(@PathVariable("id")Integer schoolRecordId,
                                                         @RequestParam(name="sort",required = false)String sort,
                                                         @RequestParam(name="order",required = false)Integer direction,//1のとき昇順、0のとき降順
                                                         Model model){
        SchoolRecord schoolRecord = schoolRecordRepository.getReferenceById(schoolRecordId);

        List<SchoolRecordResult> schoolRecordResults;

        if("klass".equals(sort)){
            schoolRecordResults = schoolRecordResultService
                    .getAllBySchoolRecordWithKlassSort(schoolRecord,direction);
        }else if(!UtilForString.isNullOrEmpty(sort)){
            schoolRecordResults = schoolRecordResultRepository
                    .findAllBySchoolRecordIdWithSort(schoolRecordId,sort,direction);
        }else{
            schoolRecordResults = schoolRecordResultRepository
                    .findAllBySchoolRecord(schoolRecord);
        }


        setKlassNameForList(schoolRecordResults);


        model.addAttribute("schoolRecord",schoolRecord);
        model.addAttribute("schoolRecordSet",schoolRecord.getSchoolRecordSet());
        model.addAttribute("schoolRecordId",schoolRecordId);
        model.addAttribute("schoolRecordResults",schoolRecordResults);

        return "schoolRecord/showAllResultBySchool";

    }

    //同じタイミングのを全件表示する。
    @Transactional(readOnly = true)
    @GetMapping("/schoolRecordResultSet/showAll/{id}")
    public String schoolRecordResultShowAllForSet(@PathVariable("id")Integer schoolRecordSetId,
                                                         @RequestParam(name="sort",required = false)String sort,
                                                         @RequestParam(name="order",required = false)Integer direction,//1のとき昇順、0のとき降順
                                                         Model model){
        List<SchoolRecordResult> schoolRecordResults;
        SchoolRecordSet schoolRecordSet = schoolRecordSetRepository.getReferenceById(schoolRecordSetId);
        if("klass".equals(sort)){
            schoolRecordResults = schoolRecordResultService
                    .getAllBySchoolRecordSetWithKlassSort(schoolRecordSet,direction);
        }else if(!UtilForString.isNullOrEmpty(sort)){
            schoolRecordResults = schoolRecordResultRepository
                    .findAllBySchoolRecordSetIdWithSort(schoolRecordSetId,sort,direction);
        }else{
            schoolRecordResults = schoolRecordResultRepository
                    .findAllBySchoolRecordSetId(schoolRecordSetId);
        }
        setKlassNameForList(schoolRecordResults);


        model.addAttribute("schoolRecordSet",schoolRecordSet);
        model.addAttribute("schoolRecordSetId",schoolRecordSetId);

        model.addAttribute("schoolRecordResults",schoolRecordResults);

        return "schoolRecord/showAllResultSet";

    }

    @GetMapping("/schoolRecordResultRegister/{id}")
    public String schoolRecordResultRegister(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @PathVariable("id")Integer schoolRecordId,
                                             Model model,
                                             @RequestParam(name="klassId",required = false)Integer klassId,
                                             @RequestParam(name="successMessage", required=false)String successMessage,
                                             @PageableDefault(page = 0,size = 10, sort = "studentId", direction = Sort.Direction.DESC)Pageable pageable,
                                             @RequestParam(name="studentName",required = false)String studentName,
                                             @RequestParam(name="currentPage",required = false)Integer currentPage){

        int page = (currentPage != null) ? currentPage : pageable.getPageNumber();
        pageable = PageRequest.of(page, pageable.getPageSize());
        LocalDate date = termAndYearService.getTodayAsLocalDate();
        CramSchool cramSchool = userDetails.getCramSchool();

        List<Klass> allClassOptions = classRepository.getAllKlassByCramSchool(cramSchool);
        model.addAttribute("allClassOptions",allClassOptions);

        Page<SchoolRecordResult> schoolRecordResultPage;

        // 名前による検索かクラスIDによる検索のどちらかのみが実行される
        if (studentName != null && !studentName.trim().isEmpty()) {
            // 名前での検索
            schoolRecordResultPage = schoolRecordResultRepository
                    .findAllBySchoolRecordIdAndNameLike(schoolRecordId, studentName, pageable);
        } else if (klassId != null) {
            // クラスIDでの検索
            schoolRecordResultPage = schoolRecordResultRepository
                    .findAllBySchoolRecordIdAndKlassId(schoolRecordId, klassId, date, pageable);
        } else {
            // 条件なしで全件検索
            schoolRecordResultPage = schoolRecordResultRepository
                    .findAllBySchoolRecordId(schoolRecordId, pageable);
        }

        setKlassNameForPage(schoolRecordResultPage);

        SchoolRecord schoolRecord = schoolRecordRepository.getReferenceById(schoolRecordId);
        SchoolRecordDTO schoolRecordDTO = SchoolRecordService.convertToDTO(schoolRecord);

        if(successMessage!=null){
            model.addAttribute("successMessage",successMessage);
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("studentName", studentName);
        model.addAttribute("klassId", klassId);
        model.addAttribute("schoolRecordResultPage", schoolRecordResultPage);
        model.addAttribute("schoolRecordDTO", schoolRecordDTO);

        return "schoolRecord/schoolRecordResultEdit";
    }

    //OK
    @PostMapping("/schoolRecordResults")
    public String submitSchoolRecordResultsEditInChunk(@RequestParam Map<String, String> params,
                                                       Model model,
                                                       RedirectAttributes redirectAttributes) {
        List<SchoolRecordResult> schoolRecordResults = new ArrayList<>();

        try {
            // schoolRecordSetIdを取得
            log.info("schoolRecordSetId:{}", params.get("schoolRecordSetId"));
            Integer schoolRecordSetId = Integer.valueOf(params.get("schoolRecordSetId"));
            log.info("schoolRecordSetId:{}", schoolRecordSetId);
            Integer currentPage = Integer.valueOf(params.get("currentPage"));

            String studentName = params.get("studentName");
            log.info("studentName:{}", studentName);

            for (String key : params.keySet()) {
                if (key.startsWith("schoolRecordResultId_")) {
                    Integer schoolRecordResultId = Integer.valueOf(params.get(key));
                    SchoolRecordResult result = schoolRecordResultRepository.getReferenceById(schoolRecordResultId);

                    result.setJapanese(parseInteger(params.get("japanese_" + schoolRecordResultId)));
                    result.setMath(parseInteger(params.get("math_" + schoolRecordResultId)));
                    result.setEnglish(parseInteger(params.get("english_" + schoolRecordResultId)));
                    result.setScience(parseInteger(params.get("science_" + schoolRecordResultId)));
                    result.setSocial(parseInteger(params.get("social_" + schoolRecordResultId)));
                    result.setMusic(parseInteger(params.get("music_" + schoolRecordResultId)));
                    result.setArt(parseInteger(params.get("art_" + schoolRecordResultId)));
                    result.setTech(parseInteger(params.get("tech_" + schoolRecordResultId)));
                    result.setPe(parseInteger(params.get("pe_" + schoolRecordResultId)));

                    schoolRecordResults.add(result);
                }
            }

            // データベースに保存（一括保存に変更）
            schoolRecordResultRepository.saveAll(schoolRecordResults);
            redirectAttributes.addFlashAttribute("successMessage", "結果が正常に保存されました。");

            // クエリパラメータを適切に構築
            StringBuilder redirectUrl = new StringBuilder()
                    .append("redirect:/schoolRecordResultSetRegister/")
                    .append(schoolRecordSetId)
                    .append("?currentPage=")
                    .append(currentPage);

            if (studentName != null && !studentName.isEmpty()) {
                redirectUrl.append("&studentName=")
                        .append(URLEncoder.encode(studentName, StandardCharsets.UTF_8.toString()));
            }

            return redirectUrl.toString();

        } catch (Exception e) {
            log.error("Error saving school records", e);
            redirectAttributes.addFlashAttribute("errorMessage", "データの保存中にエラーが発生しました。");
            return "redirect:/schoolRecordResultSetRegister/" + params.get("schoolRecordSetId");
        }
    }

    //OK
    @PostMapping("/schoolRecordResultsBySchool")
    public String submitSchoolRecordResultsEditBySchool(@RequestParam Map<String, String> params,
                                                        Model model,
                                                        RedirectAttributes redirectAttributes) {
        List<SchoolRecordResult> schoolRecordResults = new ArrayList<>();

        // schoolRecordSetIdを取得

        for (String key : params.keySet()) {
            if (key.startsWith("schoolRecordResultId_")) {
                Integer schoolRecordResultId = Integer.valueOf(params.get(key));

                // SchoolRecordとStudentをOptionalで取得し、存在しない場合はエラー処理
                SchoolRecordResult result = schoolRecordResultRepository.getReferenceById(schoolRecordResultId);
                result.setJapanese(parseInteger(params.get("japanese_" + schoolRecordResultId)));
                result.setMath(parseInteger(params.get("math_" + schoolRecordResultId)));
                result.setEnglish(parseInteger(params.get("english_" + schoolRecordResultId)));
                result.setScience(parseInteger(params.get("science_" + schoolRecordResultId)));
                result.setSocial(parseInteger(params.get("social_" + schoolRecordResultId)));
                result.setMusic(parseInteger(params.get("music_" + schoolRecordResultId)));
                result.setArt(parseInteger(params.get("art_" + schoolRecordResultId)));
                result.setTech(parseInteger(params.get("tech_" + schoolRecordResultId)));
                result.setPe(parseInteger(params.get("pe_" + schoolRecordResultId)));


                schoolRecordResults.add(result);
            }
        }
        

        // データベースに保存（upsert）
        for (SchoolRecordResult recordResult : schoolRecordResults) {
            schoolRecordResultRepository.save(recordResult);
        }
        Integer currentPage = Integer.valueOf(params.get("currentPage"));
        String studentName = params.get("studentName");
        int schoolRecordId = Integer.parseInt(params.get("schoolRecordId"));
        String successMessage = "結果が正常に保存されました。";
        redirectAttributes.addAttribute("successMessage", successMessage);
        redirectAttributes.addAttribute("currentPage",currentPage);
        redirectAttributes.addAttribute("studentName",studentName);

        // todo:同じページに戻りたい！
        return "redirect:/schoolRecordResultRegister/"+schoolRecordId;
    }

    private Integer parseInteger(String value) {
        if (value == null||value.trim().isEmpty()) {
            return null;  // null の場合は null を返す
        }
        return Integer.valueOf(value);  // 数字文字列の場合は Integer に変換
    }

    //todo:全SchoolRecordResultsを取ってきて値を代入して保存。ない場合は0を代入
    @PostMapping("/registerSchoolRecordResultsFromCsv/{id}")
    public String registerSchoolRecordResultsFromCsv(@PathVariable("id") Integer schoolRecordSetId,
                                                     @RequestParam("file") MultipartFile file,
                                                     Model model,
                                                     RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a CSV file to upload.");
            return "error";
        }

        // student.code をキーとして SchoolRecordResult を効率的に取得するために Map に変換
        List<SchoolRecordResult> schoolRecordResults = schoolRecordResultRepository.findAllBySchoolRecordSetId(schoolRecordSetId);
        log.info("schoolRecordResult lengh:{}", schoolRecordResults.size());

        Map<Long, SchoolRecordResult> resultMap = schoolRecordResults.stream()
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


                SchoolRecordResult existingRecord = resultMap.get(Long.valueOf(stringStudentCode));


                 //studentCode が一致する SchoolRecordResult が存在する場合のみ更新
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
            schoolRecordResultRepository.saveAll(schoolRecordResults);
            return "redirect:/schoolRecordResultSetRegister/" + schoolRecordSetId;

        } catch (Exception e) {
            log.info("errorが出ています:{}",e.toString());

            return "redirect:/schoolRecordResultSetRegister/" + schoolRecordSetId;
        }
    }

    // 空文字を考慮し、null を返すメソッド
    private Integer parseOrNull(String value) {
        return (value == null || value.trim().isEmpty()) ? null : Integer.parseInt(value);
    }





}
