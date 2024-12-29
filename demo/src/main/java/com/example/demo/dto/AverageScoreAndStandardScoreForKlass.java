package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;

//クラスごとの平均を保持する
//模試の結果分析でのみ利用

@Getter
public class AverageScoreAndStandardScoreForKlass extends AverageScoreForKlass {
    private final Double avgStandardScore;

    public AverageScoreAndStandardScoreForKlass(Double avgScore,Double avgStandardScore,String klassName,String teacherName){
        super(avgScore,klassName,teacherName);
        this.avgStandardScore = avgStandardScore;
    }

}