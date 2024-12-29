package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "school_record_results")
public class SchoolRecordResult implements TestResult {

    @Transient
    private String klassName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_record_result_id")
    private Integer schoolRecordResultId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    @ManyToOne
    @JoinColumn(name = "school_record_id")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private SchoolRecord schoolRecord;

    @Column(name = "japanese")
    private Integer japanese; // 国語の得点

    @Column(name = "math")
    private Integer math; // 数学の得点

    @Column(name = "english")
    private Integer english; // 英語の得点

    @Column(name = "science")
    private Integer science; // 理科の得点

    @Column(name = "social")
    private Integer social; // 社会の得点

    @Column(name = "music")
    private Integer music; // 音楽の得点

    @Column(name = "art")
    private Integer art; // 美術の得点

    @Column(name = "tech")
    private Integer tech; // 技術の得点

    @Column(name = "pe")
    private Integer pe; // 体育の得点

    @Column(name="total5",insertable=false,updatable = false)
    private Integer total5;

    @Column(name="total9",insertable = false,updatable = false)
    private Integer total9;

    @Column(name="adjusted_sum",insertable = false,updatable = false)
    private Integer adjustedSum;

    public Integer getExamValue() {
        // nullチェック
        if (adjustedSum == null) {
            return null;
        }

        // 計算時の精度を保つため、キャストのタイミングを調整
        return (adjustedSum * 300) / 65;
        // もしくは、より精度を重視する場合:
        // return (int)((adjustedSum.doubleValue() * 300) / 65);
    }


}
