package com.example.demo.dto;



import lombok.Data;

@Data
public class SchoolRecordResultDTO {

    private Integer schoolRecordResultId;
    private Integer schoolRecordId;
    private String studentName; // 学生の名前
    private Integer studentId;
    private Integer japanese; // 国語の得点
    private Integer math; // 数学の得点
    private Integer english; // 英語の得点
    private Integer science; // 理科の得点
    private Integer social; // 社会の得点
    private Integer music; // 音楽の得点
    private Integer art; // 美術の得点
    private Integer tech; // 技術の得点
    private Integer pe; // 体育の得点
}


