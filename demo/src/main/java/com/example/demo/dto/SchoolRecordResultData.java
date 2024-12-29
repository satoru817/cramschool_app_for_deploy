package com.example.demo.dto;

import lombok.Data;

@Data
public class SchoolRecordResultData {
    private String title;
    private Integer japanese; // 国語の得点
    private Integer math; // 数学の得点
    private Integer english; // 英語の得点
    private Integer science; // 理科の得点
    private Integer social; // 社会の得点
    private Integer music; // 音楽の得点
    private Integer art; // 美術の得点
    private Integer tech; // 技術の得点
    private Integer pe; // 体育の得点
    private Integer total5;
    private Integer total9;
    private Integer projectedSchoolScore;//換算内申
    private Integer weirdSum;

}
