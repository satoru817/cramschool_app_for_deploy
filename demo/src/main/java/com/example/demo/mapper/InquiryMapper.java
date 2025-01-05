package com.example.demo.mapper;

import com.example.demo.dto.InquiryDTO;
import com.example.demo.entity.Funnel;
import com.example.demo.entity.Inquiry;
import com.example.demo.service.TermAndYearService;
import com.example.demo.service.sales.FunnelService;
import com.example.demo.service.sales.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InquiryMapper {
    private final InquiryService inquiryService;
    private final FunnelService funnelService;
    private final TermAndYearService termAndYearService;



    public InquiryDTO from(Inquiry inquiry) {
        if (inquiry == null) {
            return null;
        }

        InquiryDTO dto = new InquiryDTO();

        // 必須フィールド（inquiryId）の設定
        dto.setInquiryId(inquiry.getInquiryId());

        // Optional fields with null checks
        dto.setName(inquiry.getNameKanji() != null ? inquiry.getNameKanji() : "");

        // el1からのgrade取得
        String gradeStr = null;
        if (inquiry.getEl1() != null) {
            try {
                gradeStr = termAndYearService.getCurrentGradeFromEl1(inquiry.getEl1());
            } catch (Exception e) {

                gradeStr = "";
            }
        }
        dto.setGradeStr(gradeStr != null ? gradeStr : "");

        // Funnel関連のNULLチェック
        if (inquiry.getFunnel() != null) {
            dto.setFunnelName(inquiry.getFunnel().getName() != null ?
                    inquiry.getFunnel().getName() : "");
        } else {
            dto.setFunnelName("");
        }

        // CramSchool関連のNULLチェック
        if (inquiry.getCramSchool() != null) {
            dto.setCramSchoolName(inquiry.getCramSchool().getName() != null ?
                    inquiry.getCramSchool().getName() : "");
        } else {
            dto.setCramSchoolName("");
        }

        // 日付のNULLチェック
        dto.setInquiryDate(inquiry.getInquiryDate() != null ?
                inquiry.getInquiryDate() : null);  // もしくは必要に応じてデフォルトの日付を設定

        return dto;
    }
}
