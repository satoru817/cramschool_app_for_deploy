package com.example.demo.controller.sales;

import com.example.demo.entity.*;
import com.example.demo.mapper.InquiryMapper;
import com.example.demo.repository.*;
import com.example.demo.service.TermAndYearService;
import com.example.demo.service.UserService;
import com.example.demo.service.sales.ActionService;
import com.example.demo.service.sales.InquiryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;


/**
 * 問合せに関する情報を担当するコントローラクラスです。
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/sales/inquiry")
public class InquiryController {
    private final InquiryService inquiryService;
    private final InquiryMapper inquiryMapper;
    private final CramSchoolRepository cramSchoolRepository;
    private final UserRepository userRepository;
    private final FunnelRepository funnelRepository;
    private final TermAndYearService termAndYearService;
    private final ActionService actionService;
    private final ActionRepository actionRepository;
    private final ActionHistoryRepository actionHistoryRepository;

    private static final String FIRST_ACTION = "初回問合せ";

    @PostConstruct
    public void init() {
        try {
            // "初回問合せ"アクションが存在するか確認
            if (actionRepository.findByActionName(FIRST_ACTION).isEmpty()) {
                Action initialAction = Action.builder()
                        .actionName(FIRST_ACTION)
                        .build();
                actionRepository.save(initialAction);
            }
        } catch (Exception e) {
            // ログ出力
            // 初期データ作成失敗時のハンドリング
            throw new RuntimeException("Failed to initialize default action", e);
        }
    }

    /**
     * 問合せ一覧画面を表示します。
     * @param model
     * @param pageable
     * @return 問合せ一覧画面のテンプレート名
     */
    @GetMapping("/index")
    public String inquiries(Model model,
                            @RequestParam(name="page",required = false)Integer curPage,
                            @PageableDefault(page = 0, size = 10,sort = "inquiryDate", direction = Sort.Direction.DESC) Pageable pageable){
        int page = (curPage != null)? curPage:pageable.getPageNumber();
        pageable = PageRequest.of(
                page,
                pageable.getPageSize(),
                pageable.getSort()  // 既存のソート情報を保持
        );

        Page<Inquiry> inquiries = inquiryService.findAllAndSetField(pageable);
        List<User> users = userRepository.findAll();
        List<Action> actions = actionService.getAll();
        List<CramSchool> cramSchools = cramSchoolRepository.findAll();


        model.addAttribute("inquiries",inquiries);
        model.addAttribute("users",users);
        model.addAttribute("actions",actions);
        model.addAttribute("cramSchools",cramSchools);
        model.addAttribute("inquiryFirstPostDTO",new InquiryFirstPostDTO());


        setFunnelsAndUsersAndCramSchoolsToModel(model);
        return "sales/inquiry/inquiry_index";

    }

    /**
     * 一つの問合せに結びつく全てのアクションの履歴を表示します。
     * また、このページからアクションの基本的なcrud操作が出来るようにします。
     * @param model
     * @param curPage
     * @param pageable
     * @return ある問合せに結びついたアクションの一覧画面
     */
    @GetMapping("/actions/{id}")
    public String actionsOfAnInquiry(Model model,
                                     @PathVariable(name="id")Integer inquiryId,
                                     @RequestParam(name="page",required = false)Integer curPage,
                                     @PageableDefault(page=0,size=10,sort="actionDate")Pageable pageable){
        int page = (curPage != null) ? curPage:pageable.getPageNumber();
        pageable = PageRequest.of(
                page,
                pageable.getPageSize(),
                pageable.getSort()
        );

        Inquiry inquiry = inquiryService.findById(inquiryId);
        List<Action> actions = actionRepository.findAll();

        Page<ActionHistory> actionHistories = actionService.getAllActionHistory(inquiry,pageable);

        model.addAttribute("actionHistories",actionHistories);
        model.addAttribute("actions",actions);
        return "sales/action_history/of_one_inquiry";
    }



    private void setFunnelsAndUsersAndCramSchoolsToModel(Model model){

        List<CramSchool> cramSchools = cramSchoolRepository.findAll();
        List<User> users = userRepository.findAll();
        List<Funnel> funnels = funnelRepository.findAll();

        model.addAttribute("funnels", funnels);
        model.addAttribute("cramSchools",cramSchools);
        model.addAttribute("users",users);
    }

    private boolean upsertInquiryPostDTO(InquiryPostDTO inquiryPostDTO){
        Inquiry inquiry = new Inquiry();
        inquiry.setInquiryId(inquiryPostDTO.getInquiryId());
        inquiry.setNameKanji(inquiryPostDTO.getName());
        inquiry.setNameKana(inquiryPostDTO.getKana());
        inquiry.setInquiryDate(inquiryPostDTO.getInquiryDate());
        if(inquiryPostDTO.getGradeStr() != null){
            Integer el1 = termAndYearService.getEl1FromGradeStr(inquiryPostDTO.getGradeStr());
            inquiry.setEl1(el1);
        }

        inquiry.setCramSchool(cramSchoolRepository.getReferenceById(inquiryPostDTO.getCramSchoolId()));
        inquiry.setFunnel(funnelRepository.getReferenceById(inquiryPostDTO.getFunnelId()));

        return inquiryService.save(inquiry);
    }

    @Transactional
    private boolean insertInquiryFirstPostDTOAndFirstActionHistory(InquiryFirstPostDTO inquiryFirstPostDTO){
        Inquiry inquiry = Inquiry.builder()
                .funnel(funnelRepository.getReferenceById(inquiryFirstPostDTO.getFunnelId()))
                .cramSchool(cramSchoolRepository.getReferenceById(inquiryFirstPostDTO.getCramSchoolId()))
                .nameKanji(inquiryFirstPostDTO.getName())
                .nameKana(inquiryFirstPostDTO.getKana())
                .inquiryDate(inquiryFirstPostDTO.getInquiryDate())
                .build();

        if(inquiryFirstPostDTO.getGradeStr() != null){
            Integer el1 = termAndYearService.getEl1FromGradeStr(inquiryFirstPostDTO.getGradeStr());
            inquiry.setEl1(el1);
        }

        try{
            inquiryService.save(inquiry);
        }catch (RuntimeException e){
            return false;
        }

        Action firstAction = actionRepository.getByActionName(FIRST_ACTION);

        ActionHistory firstActionHistory = ActionHistory.builder()
                .action(firstAction)
                .inquiry(inquiry)
                .actionDate(inquiryFirstPostDTO.getInquiryDate())
                .user(userRepository.getReferenceById(inquiryFirstPostDTO.getUserId()))
                .comment(inquiryFirstPostDTO.getComment())
                .build();

        boolean b = actionHistoryRepository.save(firstActionHistory) != null;
        return b;
    }

    //todo:新規問合せのActionHistoryも同時に作るように修正する必要がある。
    @PostMapping("/create")
    public String create(RedirectAttributes redirectAttributes,
                         @ModelAttribute InquiryFirstPostDTO inquiryFirstPostDTO
                         ){

        boolean b = insertInquiryFirstPostDTOAndFirstActionHistory(inquiryFirstPostDTO);

        String message;

        if(b){
            message = "新規問合せの保存に成功しました。";
        }else{
            message="問合せの保存に失敗しました。";
        }

        redirectAttributes.addFlashAttribute("message",message);

        return "redirect:/sales/inquiry/index";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttributes){
        String message;
        String deleted = inquiryService.delete(id);
        if(deleted != null){
            message = "該当の問合せの削除に成功しました:"+deleted;
        }else{
            message = "問合せの削除に失敗しました。";
        }

        redirectAttributes.addFlashAttribute("message",message);

        return "redirect:/sales/inquiry/index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,
                       Model model){
        Inquiry inquiry = inquiryService.findById(id);
        setFunnelsAndUsersAndCramSchoolsToModel(model);

        InquiryPostDTO inquiryPostDTO = InquiryPostDTO.builder()
                .inquiryId(inquiry.getInquiryId())
                .inquiryDate(inquiry.getInquiryDate())
                .kana(inquiry.getNameKana())
                .cramSchoolId(inquiry.getCramSchool().getCramSchoolId())
                .gradeStr(inquiry.getGradeStr())
                .funnelId(inquiry.getFunnel().getFunnelId())
                .name(inquiry.getNameKanji())
                .build();

        model.addAttribute("inquiryPostDTO",inquiryPostDTO);
        return "sales/inquiry/inquiry_edit";

    }

    @PostMapping("/edit/{id}/update")
    public String update(@ModelAttribute InquiryPostDTO inquiryPostDTO,
                         RedirectAttributes redirectAttributes){
        boolean b = upsertInquiryPostDTO(inquiryPostDTO);

        String message;

        if(b){
            message = "問合せの更新に成功しました。";
        }else{
            message="問合せの更新に失敗しました。";
        }

        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/sales/inquiry/index";
    }





}
