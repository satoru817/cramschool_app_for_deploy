package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRankingDto {
    private Integer rank;         // 順位
    private Integer score;        // 点数
    private Integer standardScore; // 偏差値
    private String studentName;   // 生徒名
    private Integer studentId;  // クラス名を取得するためのstudentId
    private String className;

    //コンストラクタ
    public SubjectRankingDto(Integer rank, Integer score, Integer standardScore, String studentName, Integer studentId) {
        this.rank = rank;
        this.score = score;
        this.standardScore = standardScore;
        this.studentName = studentName;
        this.studentId = studentId;
    }

}