package com.example.demo.dto;

import lombok.Data;

@Data
public class ClassRegistrationForm {
    private Integer studentId;

    private Long code;//sort順を維持するためのもの

    private String currentGrade;//el1から計算する

    private String studentName;

    private String japaneseClassName;

    private String mathClassName;

    private String englishClassName;

    private String scienceClassName;

    private String socialClassName;


}
