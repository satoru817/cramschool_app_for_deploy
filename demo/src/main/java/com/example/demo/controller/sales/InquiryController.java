package com.example.demo.controller.sales;

import com.example.demo.entity.*;
import com.example.demo.mapper.InquiryMapper;
import com.example.demo.repository.CramSchoolRepository;
import com.example.demo.repository.FunnelRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TermAndYearService;
import com.example.demo.service.UserService;
import com.example.demo.service.sales.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
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
        pageable = PageRequest.of(page,pageable.getPageSize());
        Page<Inquiry> inquiries = inquiryService.findAllAndSetField(pageable);

        List<CramSchool> cramSchools = cramSchoolRepository.findAll();

        List<User> users = userRepository.findAll();

        List<Funnel> funnels = funnelRepository.findAll(); // Add this line
        model.addAttribute("funnels", funnels);

        model.addAttribute("inquiries",inquiries);
        model.addAttribute("cramSchools",cramSchools);
        model.addAttribute("users",users);
        return "sales/inquiry/inquiry_index";

    }

    @PostMapping("/create")
    public String create(RedirectAttributes redirectAttributes,
                         @ModelAttribute InquiryPostDTO inquiryPostDTO
                         ){
        Inquiry inquiry = new Inquiry();
        inquiry.setInquiryDate(LocalDate.now());
        if(inquiryPostDTO.getGradeStr() != null){
            Integer el1 = termAndYearService.getEl1FromGradeStr(inquiryPostDTO.getGradeStr());
            inquiry.setEl1(el1);
        }

        inquiry.setCramSchool(cramSchoolRepository.getReferenceById(inquiryPostDTO.getCramSchoolId()));
        inquiry.setFunnel(funnelRepository.getReferenceById(inquiryPostDTO.getFunnelId()));

        boolean b = inquiryService.save(inquiry);
        String message;
        if(b){
            message = "新規問合せの保存に成功しました。";
        }else{
            message="問合せの保存に失敗しました。";
        }

        redirectAttributes.addFlashAttribute("message",message);

        return "redirect:/sales/inquiry/index";


    }



}
