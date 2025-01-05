package com.example.demo.controller.sales;

import com.example.demo.entity.Inquiry;
import com.example.demo.mapper.InquiryMapper;
import com.example.demo.service.sales.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 問合せに関する情報を担当するコントローラクラスです。
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/inquiry")
public class InquiryController {
    private final InquiryService inquiryService;
    private final InquiryMapper inquiryMapper;

    /**
     * 問合せ一覧画面を表示します。
     * @param model
     * @param pageable
     * @return 問合せ一覧画面のテンプレート名
     */
    @GetMapping("/index")
    public String inquiries(Model model,
                            @PageableDefault(page = 0, size = 10,sort = "inquiryDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Inquiry> inquiries = inquiryService.findAllAndSetField(pageable);
        model.addAttribute("inquiries",inquiries);
        return "sales/inquiry/inquiry_index";

    }
}
