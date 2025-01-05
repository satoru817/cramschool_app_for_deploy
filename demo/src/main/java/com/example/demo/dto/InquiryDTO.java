package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InquiryDTO {
    private int InquiryId;
    private String name;
    private String cramSchoolName;
    private String funnelName;
    private String gradeStr;
    private LocalDate inquiryDate;

}
