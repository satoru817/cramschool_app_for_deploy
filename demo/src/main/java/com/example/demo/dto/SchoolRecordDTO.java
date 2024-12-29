package com.example.demo.dto;

import lombok.Data;

@Data
public class SchoolRecordDTO {
    private Integer schoolRecordId;
    private Integer term;
    private String schoolName;
    private Integer grade;
    private Integer semester;

}
