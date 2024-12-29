package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AverageScoreForKlass {
    private Double avgScore;
    private String klassName;
    private String teacherName;

    public AverageScoreForKlass(Double avgScore,String klassName,String teacherName){
        this.avgScore = avgScore;
        this.klassName = klassName;
        this.teacherName = teacherName;
    }

}
