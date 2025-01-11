package com.example.demo.service.sales;

import com.example.demo.dto.InquiryDTO;
import com.example.demo.entity.Inquiry;
import com.example.demo.repository.InquiryRepository;
import com.example.demo.service.TermAndYearService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional
    public boolean save(Inquiry inquiry){
        try{
            inquiryRepository.save(inquiry);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Transactional
    public String delete(Integer inquiryId){
        Inquiry inquiry = inquiryRepository.getReferenceById(inquiryId);
        try{
            inquiryRepository.deleteById(inquiryId);
            return inquiry.getNameKanji();
        } catch (Exception e) {
            return "";
        }
    }

    public Inquiry findById(Integer inquiryId){
       Inquiry inquiry = inquiryRepository.findById(inquiryId)
               .orElseThrow(()->new RuntimeException("該当の問合せが見つかりませんでした。"));

        String gradeStr = "";
        if (inquiry.getEl1() != null) {
            try {
                gradeStr = termAndYearService.getCurrentGradeFromEl1(inquiry.getEl1());
            } catch (Exception e) {
                gradeStr = "";
            }
        }
        inquiry.setGradeStr(gradeStr != null ? gradeStr : "");

        return inquiry;
    }
}
