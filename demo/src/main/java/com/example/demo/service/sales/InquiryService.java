package com.example.demo.service.sales;

import com.example.demo.dto.InquiryDTO;
import com.example.demo.entity.Inquiry;
import com.example.demo.repository.InquiryRepository;
import com.example.demo.service.TermAndYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final TermAndYearService termAndYearService;


    public Page<Inquiry> findAllAndSetField(Pageable pageable) {
        Page<Inquiry> inquiries = inquiryRepository.findAll(pageable);
        inquiries.getContent().forEach(inquiry -> {
            String gradeStr = "";
            if (inquiry.getEl1() != null) {
                try {
                    gradeStr = termAndYearService.getCurrentGradeFromEl1(inquiry.getEl1());
                } catch (Exception e) {
                    gradeStr = "";
                }
            }
            inquiry.setGradeStr(gradeStr != null ? gradeStr : "");
        });

        return inquiries;

    }
}
