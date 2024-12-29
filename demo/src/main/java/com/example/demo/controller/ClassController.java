package com.example.demo.controller;

import com.example.demo.constant.Subject;
import com.example.demo.dto.ClassRegistrationForm;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.service.*;
import com.example.demo.util.UtilForString;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Conventions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

//todo:クラスはまとめて作るようにする！
@Slf4j
@RequiredArgsConstructor
@Controller
public class ClassController {
    private final ClassStudentService classStudentService;
    private final ClassStudentRepository classStudentRepository;
    private final ClassService classService;
    private final StudentService studentService;
    private final TermAndYearService termAndYearService;
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final KlassUserRepository klassUserRepository;
    private final  KlassUserService klassUserService;



    //その生徒のクラス所属履歴を全て表示する。
    @GetMapping("/class_history/{id}")
    public String classHistory(
            @PathVariable("id")Integer studentId,
            Model model
    ){
        Student student = studentRepository.getReferenceById(studentId);
        Map<String,List<KlassStudent>> klassHistory = new LinkedHashMap<>();
        for(Subject subject : Subject.values()){
            List<KlassStudent> klassStudentsforEachSubject =
                    classStudentRepository.findKlassStudentForEachSubjectAndStudent(
                            subject.getValue(),
                            student
                    );
            klassHistory.put(subject.getValue(),klassStudentsforEachSubject);
        }

        model.addAttribute("klassHistory",klassHistory);
        model.addAttribute("student",student);

        return "class/history/student";
    }

    //特定のクラスの特定の学年を担当してきた教師を表示する
    @GetMapping("/class_history/teacher/{klassId}/{grade}")
    public String classTeacherLinkHistory(@PathVariable("klassId")Integer klassId,
                                          @PathVariable("grade")Integer grade,
                                          Model model){
        Klass klass = classRepository.getReferenceById(klassId);
        List<KlassUser> klassUsers = klassUserRepository.findAllByKlassAndGrade(klass,grade);

        String gradeStr = termAndYearService.convertGradeBack(grade);

        model.addAttribute("klass",klass);
        model.addAttribute("grade",grade);
        model.addAttribute("gradeStr",gradeStr);
        model.addAttribute("klassUsers",klassUsers);

        return "class/history/teacher";

    }

    @PostMapping("/klass-users/update")
    public String updateKlassUser(@RequestParam("klassUserId")Integer klassUserId,
                                  @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                  @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                  @RequestParam("grade") Integer grade,
                                  RedirectAttributes redirectAttributes){

        KlassUser klassUser = klassUserRepository.getReferenceById(klassUserId);
        boolean isUpdated = klassUserService.updateDates(grade,klassUser,startDate,endDate);

        String message;
        if(isUpdated){
            message = "更新が完了しました";
        }else{
            message = "何らかの理由で更新出来ませんでした";
        }

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/class_history/teacher/"+klassUser.getKlass().getKlassId()+"/"+grade;


    }

    @PostMapping("/klass-students/update")
    public String updateKlassStudent(@RequestParam("klassStudentId") Integer klassStudentId,
                                     @RequestParam("studentId") Integer studentId,
                                     @RequestParam("createdAt") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdAt,
                                     @RequestParam("changedAt") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate changedAt,
                                     RedirectAttributes redirectAttributes) {
        boolean isUpdated = classStudentService.updateDates(klassStudentId, createdAt, changedAt);//todo:同じ生徒、同じ科目、

        String message;
        if(isUpdated){
            message = "更新が完了しました";
        }else{
            message = "何らかの理由で更新出来ませんでした";
        }

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/class_history/" + studentId; // 適切なリダイレクト先に変更してください
    }
    //ok
    @Transactional
    @PostMapping("/submitClassTeacherRegistration")
    public String classTeacherRegistration(
            @RequestParam Map<String, String> allParams,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            RedirectAttributes redirectAttributes) {

        try {
            // teacher_klassId_grade=teacherId の形式でパラメータが来る
            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                String key = entry.getKey();
                String teacherId = entry.getValue();

                // パラメータが正しい形式かチェック
                if (!key.startsWith("teacher_") || teacherId.isEmpty()) {
                    continue;
                }

                // teacher_1_1 -> [teacher, 1, 1]
                String[] parts = key.split("_");
                if (parts.length != 3) {
                    continue;
                }

                Integer klassId = Integer.parseInt(parts[1]);
                Integer grade = Integer.parseInt(parts[2]);
                Integer selectedTeacherId = Integer.parseInt(teacherId);

                // 既存の担当教師の終了日を設定
                klassUserService.terminateCurrentAssignmentAndMakeNewAssignmentBasedOnCondition(klassId, grade,selectedTeacherId);

            }

            redirectAttributes.addFlashAttribute("success", "教師の割り当てを保存しました");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "保存中にエラーが発生しました: " + e.getMessage());
        }

        return "redirect:/classTeacherLink";
    }

    @Transactional
    @GetMapping("/classTeacherLink")
    public String classTeacherLink(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   Model model){
        CramSchool cramSchool = userDetails.getCramSchool();

        List<Klass> klasses = classRepository.getAllKlassByCramSchool(cramSchool);

        List<User> teachers = userRepository.findAllByCramSchool(cramSchool);
        //todo:下のメソッドを変える必要ある->DONE
        Map<String, Map<Integer, User>> currentTeachers = classService.getCurrentAssignments(cramSchool.getCramSchoolId());


        model.addAttribute("klasses",klasses);
        model.addAttribute("teachers",teachers);
       model.addAttribute("currentTeachers",currentTeachers);

        return "class/classTeacherLink";
    }

    @Transactional
    @PostMapping("/createIndividualClass")
    public String createIndividualClass(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @ModelAttribute @Validated Klass klass,
                                        BindingResult result,
                                        RedirectAttributes redirectAttributes){
        String message;
        if(result.hasErrors() || UtilForString.isNullOrEmpty(klass.getSubject())){
            redirectAttributes.addFlashAttribute("klass",klass);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
            +Conventions.getVariableName(klass),result);
            message="クラスの情報に不備があります。";

        }else{
            try{
                CramSchool cramSchool = userDetails.getCramSchool();
                classService.saveIndividualKlass(klass,cramSchool);
                message = "クラスが正常に登録されました";
            } catch (Exception e) {
                message = "クラスの登録処理に失敗しました";
            }
        }

        redirectAttributes.addFlashAttribute("message",message);


        return "redirect:/classEdit";

    }


    //五科目いっぺんに作る
    @Transactional
    @PostMapping("/createGroupClass")
    public String createGroupClass(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @ModelAttribute @Validated Klass klass,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes){
        String message;

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("klass",klass);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
                    +Conventions.getVariableName(klass),result);
            message="クラスの情報に不備があります。";

        }else{
            try{
                CramSchool cramSchool = userDetails.getCramSchool();
                classService.saveGroupKlass(klass,cramSchool);
                message = "クラスが正常に登録されました";
            } catch (Exception e) {
                message = "クラスの登録処理に失敗しました";
            }
        }

        redirectAttributes.addFlashAttribute("message",message);


        return "redirect:/classEdit";
    }


    //OK
    @GetMapping("/classEdit")
    public String classEdit(@AuthenticationPrincipal UserDetailsImpl userDetails,Model model){
        CramSchool cramSchool = userDetails.getCramSchool();
        List<Klass> classList = classRepository.getAllKlassByCramSchool(cramSchool);
        Klass klass = new Klass();
        List<User> teachers = userRepository.findAllByCramSchool(cramSchool);

        model.addAttribute("classList",classList);
        model.addAttribute("klass",klass);
        model.addAttribute("teachers",teachers);

        return "class/classEditView";
    }

    //OK
    @Transactional
    @GetMapping("/classDelete/{id}")
    public String deleteClass(@PathVariable("id")Integer id){
        classService.deleteById(id);

        return "redirect:/classEdit";
    }


    //OK
    @Transactional
    @PostMapping("/createClass")
    public String createClass(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @Validated Klass klass,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("klass", klass);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
                    + Conventions.getVariableName(klass), result);
            return "redirect:/classEdit";
        }

        CramSchool cramSchool = userDetails.getCramSchool();

        // 5教科分のクラスを作成
        String[] subjects = {"国語", "数学", "英語", "理科", "社会"};
        List<Klass> klassesToSave = new ArrayList<>();
        try {
            for (String subject : subjects) {
                Klass newKlass = new Klass();
                newKlass.setCramSchool(cramSchool);
                newKlass.setName(klass.getName());          // 入力されたクラス名
                newKlass.setSortOrder(klass.getSortOrder());// 入力された順位
                newKlass.setSubject(subject);              // 各教科を設定
                klassesToSave.add(newKlass);

            }
            classRepository.saveAll(klassesToSave);//バッチ処理

            redirectAttributes.addFlashAttribute("successMessage",
                    "5教科分のクラスを作成しました");

        } catch (Exception e) {
            // エラーハンドリング
            redirectAttributes.addFlashAttribute("errorMessage",
                    "クラスの作成中にエラーが発生しました");
            return "redirect:/classEdit";
        }

        return "redirect:/classEdit";
    }

    @GetMapping("/studentClassEdit")
    public String studentClassEdit(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   Model model,
                                   @PageableDefault(size = 10, page = 0,sort= "code" , direction = Sort.Direction.DESC) Pageable pageable,
                                   @RequestParam(name="name", required=false) String name,
                                   @RequestParam(name="grade" ,required=false) String grade,
                                   @RequestParam(name="klass", required = false)Integer klassId,
                                   @RequestParam(name = "currentPage", required = false) Integer currentPage) {
        //そもそも現在学生じゃないのまでこれだと取ってきてしまう。
        int page = (currentPage != null) ? currentPage : pageable.getPageNumber();
        pageable = PageRequest.of(page, pageable.getPageSize());

        CramSchool cramSchool = userDetails.getCramSchool();
        Page<Student> studentPage;

        LocalDate date = termAndYearService.getTodayAsLocalDate();

        if (grade != null && !grade.isEmpty() && name != null && !name.isEmpty() && klassId != null) {
            // 学年、名前、クラスIDで条件検索
            Integer el1 = termAndYearService.getWhenEnteredElementarySchool(termAndYearService.convertGrade(grade));
            studentPage = studentRepository.findByEl1AndNameContainingAndKlassIdAndCramSchool(el1, name, klassId, cramSchool,date, pageable);
        } else if (grade != null && !grade.isEmpty() && klassId != null) {
            // 学年とクラスIDで条件検索
            Integer el1 = termAndYearService.getWhenEnteredElementarySchool(termAndYearService.convertGrade(grade));
            studentPage = studentRepository.findByEl1AndKlassIdAndCramSchool(el1, klassId, cramSchool,date, pageable);
        } else if (name != null && !name.isEmpty() && klassId != null) {
            // 名前とクラスIDで条件検索
            studentPage = studentRepository.findByNameContainingAndKlassIdAndCramSchool(name, klassId, cramSchool,date, pageable);
        } else if (grade != null && !grade.isEmpty() && name != null && !name.isEmpty()) {
            // 学年と名前で条件検索
            Integer el1 = termAndYearService.getWhenEnteredElementarySchool(termAndYearService.convertGrade(grade));
            studentPage = studentRepository.findByEl1AndNameContainingAndCramSchool(el1, name, cramSchool, pageable);
        } else if (grade != null && !grade.isEmpty()) {
            // 学年で条件検索
            Integer el1 = termAndYearService.getWhenEnteredElementarySchool(termAndYearService.convertGrade(grade));
            studentPage = studentRepository.findAllByEl1AndCramSchool(el1, cramSchool, pageable);
        } else if (name != null && !name.isEmpty()) {
            // 名前で条件検索
            studentPage = studentRepository.findByNameContainingAndCramSchool(name, cramSchool, pageable);
        } else if (klassId != null) {
            // クラスIDで条件検索
            studentPage = studentRepository.findByKlassIdAndCramSchool(klassId, cramSchool, date,pageable);
        } else {
            // それ以外は現在の生徒を取得
            log.info("それ以外の場合がよびだされています。");
            studentPage = studentService.findCurrentStudentsByCramSchool(cramSchool, pageable);
        }

        //全科目について"N/A"クラスを作成する。UNIQUE制約があるので、upsertになる。
       // makeNAForAllSubjects(cramSchool);

        //塾に関連したKlassを科目ごとに取ってくる
        List<Klass> japaneseClassOptions = classRepository.findBySubjectAndCramSchool("japanese",cramSchool);
        List<Klass> mathClassOptions = classRepository.findBySubjectAndCramSchool("math",cramSchool);
        List<Klass> englishClassOptions = classRepository.findBySubjectAndCramSchool("english",cramSchool);
        List<Klass> scienceClassOptions = classRepository.findBySubjectAndCramSchool("science",cramSchool);
        List<Klass> socialClassOptions = classRepository.findBySubjectAndCramSchool("social",cramSchool);



        List<ClassRegistrationForm> classRegistrationForms = new ArrayList<>();


        //現在所属するクラスをセットしている。
        setCurrentKlassToClassRegistrationForm(classRegistrationForms,studentPage);


        model.addAttribute("classRegistrationForms", classRegistrationForms);

        model.addAttribute("grades", termAndYearService.getAvailableGrades());

        //検索ワードの設定pagination用
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("grade",grade);
        model.addAttribute("klassId",klassId);
        model.addAttribute("name",name);

        //クラスの選択肢
        model.addAttribute("japaneseClassOptions",japaneseClassOptions);
        model.addAttribute("mathClassOptions",mathClassOptions);
        model.addAttribute("englishClassOptions",englishClassOptions);
        model.addAttribute("scienceClassOptions",scienceClassOptions);
        model.addAttribute("socialClassOptions",socialClassOptions);

        List<Klass> allClassOptions = new ArrayList<>();
        allClassOptions.addAll(japaneseClassOptions);
        allClassOptions.addAll(mathClassOptions);
        allClassOptions.addAll(englishClassOptions);
        allClassOptions.addAll(scienceClassOptions);
        allClassOptions.addAll(socialClassOptions);

        model.addAttribute("allClassOptions", allClassOptions);

        return "class/class_student_register";
    }

//    //全科目について"N/A"を作って保存する。
//    private void makeNAForAllSubjects(CramSchool cramSchool) {
//        String NA = "N/A";
//        List<String> subjects = List.of("国語", "英語", "数学", "社会", "理科");
//        for (String subject : subjects) {
//            Klass klass = new Klass();
//            klass.setCramSchool(cramSchool);
//            klass.setSubject(subject);
//            klass.setName(NA);
//            klass.setSortOrder(0);
//
//            try {
//                classRepository.save(klass);
//            } catch (DataIntegrityViolationException e) {
//               System.err.println("Duplicate entry for subject: " + subject);
//            }
//        }
//    }


    //"N/A"は絶対に全教室、全科目で作る必要がある。
    private void setCurrentKlassToClassRegistrationForm(List<ClassRegistrationForm> classRegistrationForms, Page<Student> studentPage) {
        LocalDate today = termAndYearService.getTodayAsLocalDate();
        for (Student student : studentPage.getContent()) {
            ClassRegistrationForm classRegistrationForm = new ClassRegistrationForm();
            classRegistrationForm.setStudentId(student.getId());
            classRegistrationForm.setStudentName(student.getName());
            classRegistrationForm.setCurrentGrade(termAndYearService.getCurrentGradeFromEl1(student.getEl1()));


            try {
                classRegistrationForm.setJapaneseClassName(classStudentService.findKlassForJapaneseByStudentAndDate(student, today).getName());
            } catch (Exception e) {
                classRegistrationForm.setJapaneseClassName(null);
            }

            try {
                classRegistrationForm.setMathClassName(classStudentService.findKlassForMathByStudentAndDate(student, today).getName());
            } catch (Exception e) {
                classRegistrationForm.setMathClassName(null);
            }

            try {
                classRegistrationForm.setEnglishClassName(classStudentService.findKlassForEnglishByStudentAndDate(student, today).getName());
            } catch (Exception e) {
                classRegistrationForm.setEnglishClassName(null);
            }

            try {
                classRegistrationForm.setScienceClassName(classStudentService.findKlassForScienceByStudentAndDate(student, today).getName());
            } catch (Exception e) {
                classRegistrationForm.setScienceClassName(null);
            }


            try {
                classRegistrationForm.setSocialClassName(classStudentService.findKlassForSocialByStudentAndDate(student, today).getName());
            } catch (Exception e) {
                classRegistrationForm.setSocialClassName(null);
            }

            classRegistrationForms.add(classRegistrationForm);
        }
    }


    @PostMapping("/submitClassRegistration")
    public String submitClassRegistration(@RequestParam Map<String, String> params,
                                          @RequestParam(name="grade",required = false) String grade,
                                          @RequestParam(name="name",required = false) String name,
                                          @RequestParam(name="currentPage",required = false) Integer currentPage, // 現在のページ番号を受け取る
                                          RedirectAttributes redirectAttributes) {
        LocalDate today = termAndYearService.getTodayAsLocalDate();
        AtomicBoolean isUpdated = new AtomicBoolean(false); // フラグを初期化
        for (String key : params.keySet()) {
            if (key.startsWith("studentId_")) {
                Integer studentId = Integer.valueOf(params.get(key));

                Student student = studentService.findById(studentId);

                System.out.println("処理中のstudent: " + student.getName());

                //空文字のときnullを入れる
                Integer japaneseKlassId = params.get("japaneseKlassId" + studentId).isEmpty()
                        ? null
                        : Integer.valueOf(params.get("japaneseKlassId" + studentId));
                Integer mathKlassId = params.get("mathKlassId" + studentId).isEmpty()
                        ? null
                        : Integer.valueOf(params.get("mathKlassId" + studentId));
                Integer englishKlassId = params.get("englishKlassId" + studentId).isEmpty()
                        ? null
                        : Integer.valueOf(params.get("englishKlassId" + studentId));
                Integer scienceKlassId = params.get("scienceKlassId" + studentId).isEmpty()
                        ? null
                        : Integer.valueOf(params.get("scienceKlassId" + studentId));
                Integer socialKlassId = params.get("socialKlassId" + studentId).isEmpty()
                        ? null
                        : Integer.valueOf(params.get("socialKlassId" + studentId));


                //更新前に生徒が所属していたクラスを取り出している
                List<KlassStudent> japaneseKlassList = classStudentRepository.findKlassForJapaneseByStudentAndDate(student, today);
                Integer prevJapaneseKlassId = getPrevKlassId(japaneseKlassList.isEmpty() ? null : japaneseKlassList.get(0));

                List<KlassStudent> mathKlassList = classStudentRepository.findKlassForMathByStudentAndDate(student, today);
                Integer prevMathKlassId = getPrevKlassId(mathKlassList.isEmpty() ? null : mathKlassList.get(0));

                List<KlassStudent> englishKlassList = classStudentRepository.findKlassForEnglishByStudentAndDate(student, today);
                Integer prevEnglishKlassId = getPrevKlassId(englishKlassList.isEmpty() ? null : englishKlassList.get(0));

                List<KlassStudent> scienceKlassList = classStudentRepository.findKlassForScienceByStudentAndDate(student, today);
                Integer prevScienceKlassId = getPrevKlassId(scienceKlassList.isEmpty() ? null : scienceKlassList.get(0));

                List<KlassStudent> socialKlassList = classStudentRepository.findKlassForSocialByStudentAndDate(student, today);
                Integer prevSocialKlassId = getPrevKlassId(socialKlassList.isEmpty() ? null : socialKlassList.get(0));

                // クラスの変更があった場合の処理
                updateClassIfChanged(student, today,  japaneseKlassId, prevJapaneseKlassId, isUpdated);
                updateClassIfChanged(student, today,  mathKlassId, prevMathKlassId, isUpdated);
                updateClassIfChanged(student, today,  englishKlassId, prevEnglishKlassId, isUpdated);
                updateClassIfChanged(student, today,  scienceKlassId, prevScienceKlassId, isUpdated);
                updateClassIfChanged(student, today,  socialKlassId, prevSocialKlassId, isUpdated);

            }
        }
        // ここで isUpdated.get() を使って、更新があったかどうかを確認できます
        if (isUpdated.get()) {
            redirectAttributes.addFlashAttribute("success", "クラスが正常に登録されました。");
        } else {
            redirectAttributes.addFlashAttribute("error", "変更がありませんでした。");
        }

        // Redirect with the same grade and name filters
        //これがおかしい？
        //return String.format("redirect:/studentClassEdit?grade=%s&name=%s", grade, name);
        redirectAttributes.addAttribute("grade",grade);
        redirectAttributes.addAttribute("name",name);
        redirectAttributes.addAttribute("currentPage", currentPage);
        return "redirect:/studentClassEdit";
    }

    private Integer getPrevKlassId(KlassStudent klassStudent) {
        return (klassStudent != null) ? klassStudent.getKlass().getKlassId() :null;
    }



    private void updateClassIfChanged(Student student, LocalDate today, Integer newKlassId, Integer prevKlassId, AtomicBoolean isUpdated) {

        if (prevKlassId != null && !newKlassId.equals(prevKlassId)) {

            Optional<KlassStudent> optionalPrevKlassStudent = Optional.ofNullable(classStudentRepository.findKlassStudentForKlassIdAndDate(student, today, prevKlassId));

            if (optionalPrevKlassStudent.isPresent()) {
                KlassStudent prevKlassStudent = optionalPrevKlassStudent.get();
                prevKlassStudent.setChangedAt(termAndYearService.getTodayAsLocalDate().minusDays(1));//その日から新しいクラスに所属するようにするために以前のクラスに所属していたのは昨日までにしている
                classStudentRepository.save(prevKlassStudent);
                log.info("以前のクラスの学生記録を更新しました。");

                Klass klass = classRepository.findByKlassId(newKlassId);
                if (klass != null) {
                    KlassStudent newKlassStudent = new KlassStudent();
                    newKlassStudent.setKlass(klass);
                    newKlassStudent.setStudent(student);
                    newKlassStudent.setCreatedAt(termAndYearService.getTodayAsLocalDate());
                    newKlassStudent.setChangedAt(termAndYearService.maxLocalDate);
                    classStudentRepository.save(newKlassStudent);
                    isUpdated.set(true); // 更新があったことをフラグで示す

                } else {
                    log.error("科目: {}、クラス名: {} が見つかりません", newKlassId);
                }
            } else {
                log.warn("学生: {} と科目: {} に対する以前のクラスの記録が見つかりませんでした", student.getName());
            }
        } else if (prevKlassId == null && newKlassId !=null) {
            log.info("以前のクラスが見つかりません。新しいクラスを追加しようとしています: {}", newKlassId);

            Klass klass = classRepository.findByKlassId(newKlassId);
            if (klass != null) {
                KlassStudent newKlassStudent = new KlassStudent();
                newKlassStudent.setKlass(klass);
                newKlassStudent.setChangedAt(termAndYearService.maxLocalDate);
                newKlassStudent.setCreatedAt(termAndYearService.minLocalDate);
                newKlassStudent.setStudent(student);
                classStudentRepository.save(newKlassStudent);
                isUpdated.set(true); // 新しいクラスが追加された場合もフラグを立てる
                log.info("新しいクラスの学生記録を追加しました: {}", newKlassId);
            } else {
                log.error("クラス名: {} が見つかりません",  newKlassId);
            }
        }
    }





}
