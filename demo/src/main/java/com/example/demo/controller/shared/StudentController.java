package com.example.demo.controller.shared;
import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.mapper.RegularTestResultMapper;
import com.example.demo.repository.*;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.*;
import com.example.demo.util.ListUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.springframework.core.Conventions;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@RequiredArgsConstructor
@Controller
@Slf4j
public class StudentController {
    private final StudentService studentService;
    private final SchoolService schoolService;
    private final SchoolStudentService schoolStudentService;
    private final TermAndYearService termAndYearService;
    private final StatusService statusService;
    private final StatusStudentService statusStudentService;
    private final StatusRepository statusRepository;
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final RegularTestResultRepository regularTestResultRepository;
    private final SchoolRecordResultRepository schoolRecordResultRepository;
    private final MockTestResultRepository mockTestResultRepository;
    private final SchoolRecordResultService schoolRecordResultService;
    private final OtherTestResultRepository otherTestResultRepository;
    private final ClassStudentService classStudentService;
    private final RegularTestResultMapper regularTestResultMapper;
    private final UserService userService;
    private final RoleService roleService;
    private final CramSchoolService cramSchoolService;
    private final UserRepository userRepository;

    private static final int ALL_SCHOOLS = -1;
    ArrayList<String> gradeList = new ArrayList<>(Arrays.asList("未就学","小１","小２","小３","小４","小５","小６","中１","中２","中３","高１","高２","高３"));
    //これは順番付きの配列


    //個人成績まとめページ。これにはcramSchool情報は不要である。
    @GetMapping("/student/all_score/{id}")
    public String showAllScore(@PathVariable("id")Integer studentId,
                               Model model){
        log.info("showAllScoreは呼ばれています");
        Student student = studentRepository.getReferenceById(studentId);


        //定期テストの結果をdtoに変換
        List<RegularTestResult> regularTestResultsUnconverted = regularTestResultRepository.findAllByStudentId(studentId);
        List<RegularTestResultDtoForChart> regularTestResultsConverted = regularTestResultMapper.toChartDTOList(regularTestResultsUnconverted);


        //todo:その他のテストに関しても平均との差を表示するように改修したほうがいい？
        List<OtherTestResult> otherTestResults = otherTestResultRepository.findAllByStudentId(studentId);


        List<SchoolRecordResult> schoolRecordResults = schoolRecordResultRepository.findAllByStudentId(studentId);
        List<SchoolRecordResultData> schoolRecordResultDataList = schoolRecordResultService.getData(schoolRecordResults);


        List<MockTestResult> results = mockTestResultRepository.findAllByStudentId(studentId);
        List<MockTestResultDTO> dtos = results.stream()
                .map(MockTestResultDTO::from)
                .collect(Collectors.toList());

        model.addAttribute("otherTestResults",otherTestResults);
        model.addAttribute("schoolRecordResultDataList",schoolRecordResultDataList);
        model.addAttribute("regularTestResults",regularTestResultsConverted);
        model.addAttribute("mockTestResults",dtos);
        model.addAttribute("student",student);

        return "student/all_score";
    }


    //OK
    @Transactional
    @GetMapping("/studentRegister")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public String studentRegister_g(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        CramSchool cramSchool = userDetails.getCramSchool();
        if (!model.containsAttribute("studentForm")) {
            StudentForm studentForm = new StudentForm();
            model.addAttribute("studentForm", studentForm);
        }
        List<Status> statusList = statusService.findAll();
        model.addAttribute("statusList",statusList);

        List<School> schoolList = schoolRepository.findAllByCramSchool(cramSchool);
        model.addAttribute("schoolList", schoolList);
        
        return "student/student_register";
    }

    //OK
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Transactional
    @PostMapping("/studentRegister")
    public String studentRegister_p(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    Model model,
                                    @Validated StudentForm studentForm,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        CramSchool cramSchool = userDetails.getCramSchool();
        if (!bindingResult.hasErrors()) {

            Long code = studentForm.getCode();
            Integer schoolId = studentForm.getSchoolId();
            School school = schoolService.fetchById(schoolId);

            //studentオブジェクトの生成とDBへの保存
            Student student = new Student();
            student.setCode(code);
            student.setName(studentForm.getName());
            student.setFurigana(studentForm.getFurigana());
            student.setEl1(termAndYearService.getWhenEnteredElementarySchool(studentForm.getGrade()));
            student.setCramSchool(cramSchool);

            studentService.save(student);

            //SchoolStudentオブジェクトの生成とDBへの保存
            SchoolStudent schoolStudent = new SchoolStudent();
            schoolStudent.setStudent(student);
            schoolStudent.setSchool(school);
            schoolStudent.setCreatedAt(termAndYearService.minLocalDate);
            schoolStudent.setChangedAt(termAndYearService.maxLocalDate);
            schoolStudentService.save(schoolStudent);

            //StatusStudentオブジェクトの生成とDBへの保存
            StatusStudent statusStudent = new StatusStudent();
            statusStudent.setStudent(student);
            statusStudent.setStatus(statusService.getStatusById(studentForm.getStatusId()));
            statusStudent.setCreatedAt(termAndYearService.minLocalDate);
            statusStudent.setChangedAt(termAndYearService.maxLocalDate);
            statusStudentService.save(statusStudent);

            return "redirect:/studentShow";

        } else {
            System.out.println("there is a error");
            redirectAttributes.addFlashAttribute("studentForm", studentForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
                    + "studentForm", bindingResult);

            return "redirect:/studentRegister";
        }
    }

    //todo:改修が必要。まず、はじめは何も表示しない。filterボタンが押された時点で、Pageを作るようにする。
    @Transactional
    @GetMapping("/studentShow")
    public String studentShow_g(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @PageableDefault(page = 0, size = 20, sort = "code", direction = Sort.Direction.DESC) Pageable pageable,
                                @RequestParam(required = false) String grade,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) Integer cramSchoolId,
                                Model model) {
        CramSchool cramSchool = userDetails.getCramSchool();
        List<CramSchool> cramSchools = prepareCramSchools(userDetails);

        Page<Student> studentPage = getPageOnCondition(userDetails,grade,name,cramSchoolId,cramSchools,pageable);

        model.addAttribute("cramSchools" , cramSchools);

        if(studentPage != null){
            model.addAttribute("currentPage", studentPage.getNumber());
            model.addAttribute("totalPages", studentPage.getTotalPages());
            model.addAttribute("studentShows", convertToStudentShowList(studentPage.getContent()));

        }else{
            model.addAttribute("currentPage",0);
            model.addAttribute("totalPages",0);
        }
        model.addAttribute("grades", termAndYearService.getAvailableGrades());
        model.addAttribute("grade",grade);
        model.addAttribute("name",name);
        model.addAttribute("cramSchoolId",cramSchoolId);
        
        return "student/student_show";
    }



    private List<CramSchool> prepareCramSchools (UserDetailsImpl userDetails){
        List<CramSchool> cramSchools = new ArrayList<>();
        if(UserService.isSuperAdmin(userDetails)){
            cramSchools = cramSchoolService.getAllCramSchools();
        }else{
            cramSchools = userRepository.findAllByUser(userDetails.getUser());
        }
        return cramSchools;
    }

    /**
     * 複雑な条件分岐をしてPage<Student>を作成します。
     * SUPER_ADMINであり、かつcramSchoolIdが-1の場合は全教室の対応する学年、名前の生徒をとってくる。
     * SUPER_ADMINではなく、かつcramSchoolIdが-1の場合はその教師に紐付けられた教室の対応する学年、名前の生徒をとってくる
     * cramSchoolIdがNULLのときはNULLを返す
     * @param userDetails
     * @param grade
     * @param name
     * @param cramSchoolId
     * @param pageable
     * @return
     */
    private Page<Student> getPageOnCondition(UserDetailsImpl userDetails,
                                             String grade,
                                             String name,
                                             Integer cramSchoolId,
                                             List<CramSchool> cramSchools,
                                             Pageable pageable){
        //初期アクセスの場合nullを返す
        if(cramSchoolId == null){
            return null;
        }

        Integer el1 = null;
        String validName = null;

        if(grade != null && !grade.isEmpty()){
            el1 = termAndYearService.getWhenEnteredElementarySchool(termAndYearService.convertGrade(grade));
        }

        if(name != null && !name.trim().isEmpty()){
            validName = name;
        }

        //「全校舎選択」の場合

        if(UserService.isSuperAdmin(userDetails) && cramSchoolId == ALL_SCHOOLS){
            if(el1 == null && validName == null){
                return  studentService.findAllCurrentStudent(pageable);
            }else if(el1 == null){
                return studentService.findAllCurrentStudentsByName(validName,pageable);
            }else if(validName == null){
                return  studentService.findAllStudentsByEl1(el1,pageable);
            }else{
                return  studentService.findAllStudentsByEl1AndName(validName,el1,pageable);
            }
        }else if(!UserService.isSuperAdmin(userDetails) && cramSchoolId == ALL_SCHOOLS){
            if(el1 == null && validName == null){
                return studentService.findAllCurrentStudentInCramSchools(cramSchools,pageable);
            }else if(el1 == null){
                return studentService.findAllCurrentStudentsByNameInCramSchools(validName,cramSchools,pageable);
            }else if(validName == null){
                return studentService.findAllStudentsByEl1InCramSchools(el1,cramSchools,pageable);
            }else{
                return studentService.findAllStudentsByEl1AndNameInCramSchools(validName,el1,cramSchools,pageable);
            }
        }

        //校舎が決定されている場合はそれも考慮してpageを作成する
        CramSchool cramSchool = cramSchoolService.getById(cramSchoolId);
        if(el1 == null && validName == null){
            return  studentService.findAllCurrentStudentInACramSchool(cramSchool,pageable);
        }else if(el1 == null){
            return studentService.findAllCurrentStudentsByNameInACramSchool(cramSchool,validName,pageable);
        }else if(validName == null){
            return  studentService.findAllStudentsByEl1InACramSchool(cramSchool,el1,pageable);
        }else{
            return  studentService.findAllStudentsByEl1AndNameInACramSchool(cramSchool,validName,el1,pageable);
        }

    }




    //書き換え不要
    @GetMapping("/studentEdit/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public String editStudent(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @PathVariable(name = "id") Integer id,
                              Model model) {
        if(!model.containsAttribute("id")&&!model.containsAttribute("studentForm")){
            model.addAttribute("id",id);
            Student student = studentService.getById(id);
            StudentForm studentForm = convertStudentToStudentForm(student);//このメソッドをどうにかする
            model.addAttribute("studentForm", studentForm);
        }

        List<Status> statusList = statusService.findAll();
        model.addAttribute("statusList",statusList);

        List<School> schoolList = schoolRepository.findAllByCramSchool(userDetails.getCramSchool());
        model.addAttribute("schoolList", schoolList);
        
        return "student/student_edit";
    }

    //OK
    //ログイン時のユーザー情報から生徒と関連づけられる塾が決まる。
    @Transactional
    @PostMapping("/studentUploadCSV")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public String uploadCsv(@AuthenticationPrincipal UserDetailsImpl userDetails,
                            @RequestParam("file") MultipartFile file, Model model,RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            model.addAttribute("error", "Please select a CSV file to upload.");
            return "error"; // Return to an error view
        }

        CramSchool cramSchool = userDetails.getCramSchool();
        Optional<School> optionalUnknown = schoolService.getUnknownForCramSchool(cramSchool);
        School unknownSchool = new School();
        if(optionalUnknown.isPresent()){
            unknownSchool = optionalUnknown.get();
        }else{
            unknownSchool.setCramSchool(cramSchool);
            unknownSchool.setName("不明");
        }

        schoolRepository.save(unknownSchool);


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            reader.readLine(); // Skip "生徒マスタ"





            reader.readLine(); // Skip "日付,2024/09/26"

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withDelimiter(',')
                    .withQuote('"')
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {

                System.out.println("Record: " + record);
                // Access the specific columns by header names
                String studentCode = record.get("生徒コード");
                Long studentCodeL = Long.valueOf(studentCode);
                String grade = record.get("学年");
                Integer gradeI = gradeList.indexOf(grade);
                Integer el1 = termAndYearService.getWhenEnteredElementarySchool(gradeI);
                String schoolName = record.get("在学校名");
                String course = record.get("所属コース");
                String lastName = record.get("生徒名前(姓)");
                String firstName = record.get("生徒名前(名)");
                String lastNameFurigana = record.get("生徒読み(姓)");
                String firstNameFurigana = record.get("生徒読み(名)");


                if(!isAlreadyExists(studentCodeL)){
                    Student student = new Student();
                    student.setCode(studentCodeL);
                    student.setCramSchool(cramSchool);

                    String studentName=lastName + " "+firstName;
                    String studentNameFurigana = lastNameFurigana+ " "+firstNameFurigana;


                    student.setName(studentName);

                    student.setFurigana(studentNameFurigana);
                    //学校を取ってきてnullじゃなければ登録で良いkana。nullのときのエラーhandlingをどうしたらいい？
                    //el1をせっとするメソッドが必要
                    student.setEl1(el1);
                    School school = schoolRepository.findByNameAndCramSchool(schoolName,cramSchool);//ここでduplicateErrorがでている。
                    SchoolStudent schoolStudent = new SchoolStudent();

                    if(school!=null){
                        schoolStudent.setSchool(school);
                    }else{
                        schoolStudent.setSchool(unknownSchool);
                    }

                    String sStatus;

                    switch(course){
                        case "講習生":
                            sStatus = "講習";
                            break;
                        case "個別指導本科生":
                            sStatus="個別";
                            break;
                        case "体験":
                            sStatus="体験";
                            break;
                        default:
                            sStatus = "本科";
                    }
                    studentService.save(student);

                    //TODO sStatusから対応するstatusを取ってくる。
                    Status status = statusRepository.findByName(sStatus);
                    //TODO StatusStudentをつくって保存
                    StatusStudent statusStudent = new StatusStudent();
                    statusStudent.setStudent(student);
                    statusStudent.setStatus(status);
                    statusStudent.setCreatedAt(termAndYearService.minLocalDate);
                    statusStudent.setChangedAt(termAndYearService.maxLocalDate);
                    statusStudentService.save(statusStudent);

                    schoolStudent.setStudent(student);
                    schoolStudent.setCreatedAt(termAndYearService.minLocalDate);
                    schoolStudent.setChangedAt(termAndYearService.maxLocalDate);
                    schoolStudentService.save(schoolStudent);
                }
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error processing file: " + e.getMessage());
            return "redirect:/studentShow"; // Return to an error view
        }

        redirectAttributes.addFlashAttribute("success", "File processed successfully!");
        return "redirect:/studentShow"; // Return to a success view
    }



    //OK
    @PostMapping("/studentExecEdit/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public String DoEdit(@PathVariable(name="id")Integer id,
                         @Validated StudentForm studentForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("id",id);
            redirectAttributes.addFlashAttribute("studentForm",studentForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
            + Conventions.getVariableName(studentForm),bindingResult);


            return "redirect:/studentEdit/{id}";

        }else{
            Student student = convertStudentFormToStudent(studentForm,id);
            studentService.save(student);//codeの変更やstatusの変更は履歴を残さないでそのままDBに変更をかける。



            SchoolStudent schoolStudent = schoolStudentService.getLatestSchoolStudentById(id);

            schoolStudent.setChangedAt(termAndYearService.getTodayAsLocalDate());
            schoolStudentService.save(schoolStudent);//save the updated SchoolStudent

            SchoolStudent newSchoolStudent = new SchoolStudent();
            newSchoolStudent.setStudent(student);
            newSchoolStudent.setSchool(schoolService.fetchById(studentForm.getSchoolId()));
            newSchoolStudent.setCreatedAt(termAndYearService.getTodayAsLocalDate());
            newSchoolStudent.setChangedAt(termAndYearService.maxLocalDate);
            schoolStudentService.save(newSchoolStudent);//新しいSchoolStudentオブジェクトをDBに保存する

            StatusStudent  statusStudent = (StatusStudent) ListUtils.getLast(statusStudentService.getStatusStudentByStudentIdOrdered(id));
            statusStudent.setChangedAt(termAndYearService.getTodayAsLocalDate());
            statusStudentService.save(statusStudent);

            StatusStudent newStatusStudent = new StatusStudent();
            newStatusStudent.setStudent(student);
            newStatusStudent.setStatus(statusService.getStatusById(studentForm.getStatusId()));
            newStatusStudent.setCreatedAt(termAndYearService.getTodayAsLocalDate());
            newStatusStudent.setChangedAt(termAndYearService.maxLocalDate);
            statusStudentService.save(newStatusStudent);


            redirectAttributes.addFlashAttribute("editSuccess");
            return "redirect:/studentShow";
        }
    }



    public StudentShow convertStudentToStudentShow(Student student,LocalDate now){

        String schoolGrade = gradeList.get(termAndYearService.getSchoolGrade(student));
        String status = getStatusNowYouBelongTo(student).getName();
        String klassName = classStudentService.findIntegratedKlassName(student,now);

        return new StudentShow(student.getId(),student.getName(),student.getFurigana(),klassName,student.getCode(),schoolGrade,status,getSchoolNowYouBelongTo(student).getName());


    }


    public List<StudentShow> convertToStudentShowList(List<Student> students){
        List<StudentShow> studentShows = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for(Student student:students){
            studentShows.add(convertStudentToStudentShow(student,now));
        }

        return studentShows;
    }

    //書き換え完了
    public StudentForm convertStudentToStudentForm(Student student){
        return new StudentForm(student.getCode(),termAndYearService.getSchoolGrade(student),student.getName(),student.getFurigana(),getStatusNowYouBelongTo(student).getStatusId(),getSchoolNowYouBelongTo(student).getSchoolId());
        //現在のstatusを取得するようにしないといけない。
    }

    //書き換え完了
    public Student convertStudentFormToStudent(StudentForm studentForm,Integer id){
        //puts id and convert schoolId to real school instance
        Student student = new Student();
        student.setId(id);
        student.setCode(studentForm.getCode());
        student.setName(studentForm.getName());
        student.setFurigana(studentForm.getFurigana());

        student.setEl1(termAndYearService.getWhenEnteredElementarySchool(studentForm.getGrade()));

        return student;
    }

    public Boolean isAlreadyExists(Long code){
        return !(studentService.findByCode(code)==null);
    }

    //新たに追加したmethod
    public School getSchoolNowYouBelongTo(Student student){
        //return schoolStudentService.getSchoolStudentsByStudentIdOrdered(student.getId()).getLast().getSchool();
        return ListUtils.getLast(schoolStudentService.getSchoolStudentsByStudentIdOrdered(student.getId())).getSchool();
    }

    public Status getStatusNowYouBelongTo(Student student){
       // return statusStudentService.getStatusStudentByStudentIdOrdered(student.getId()).getLast().getStatus();
        return ListUtils.getLast(statusStudentService.getStatusStudentByStudentIdOrdered(student.getId())).getStatus();
    }






}