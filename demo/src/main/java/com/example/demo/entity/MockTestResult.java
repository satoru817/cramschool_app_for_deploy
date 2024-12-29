package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
@Table(name = "mock_test_results")
@IdClass(MockTestResultId.class)
@Data
public class MockTestResult implements Serializable {
    @Transient
    private String klassName;//生徒が模試の日に所属していたクラスを入れる

    @Id
    @Column(name = "mock_test_id", nullable = false)
    private Integer mockTestId;

    @Id
    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @ManyToOne
    @JoinColumn(name = "mock_test_id", insertable = false, updatable = false)
    private MockTest mockTest;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;

    @Column(name = "japanese")
    private Integer japanese;

    @Column(name = "japanese_ss")
    private Integer japaneseSs;

    @Column(name = "math")
    private Integer math;

    @Column(name = "math_ss")
    private Integer mathSs;

    @Column(name = "english")
    private Integer english;

    @Column(name = "english_ss")
    private Integer englishSs;

    @Column(name = "science")
    private Integer science;

    @Column(name = "science_ss")
    private Integer scienceSs;

    @Column(name = "social")
    private Integer social;

    @Column(name = "social_ss")
    private Integer socialSs;

//    @Transient  // DBには保存しない
//    public Integer getTotal3() {
//        return (japanese != null && math != null && english != null)
//                ? japanese + math + english
//                : null;
//    }
//
//    @Transient
//    public Integer getTotal5() {
//        return (japanese != null && math != null && english != null
//                && science != null && social != null)
//                ? japanese + math + english + science + social
//                : null;
//    }


    @Column(name = "total3_ss")
    private Integer total3Ss;


    @Column(name = "total5_ss")
    private Integer total5Ss;

    @Column(name="total3")
    private Integer total3;

    @Column(name="total5")
    private Integer total5;

    @Column(name = "dream_school1")
    private String dreamSchool1;

    @Column(name = "dream_school1_probability")
    private Integer dreamSchool1Probability;

    @Column(name = "dream_school2")
    private String dreamSchool2;

    @Column(name = "dream_school2_probability")
    private Integer dreamSchool2Probability;

    @Column(name = "dream_school3")
    private String dreamSchool3;

    @Column(name = "dream_school3_probability")
    private Integer dreamSchool3Probability;

    @Column(name = "dream_school4")
    private String dreamSchool4;

    @Column(name = "dream_school4_probability")
    private Integer dreamSchool4Probability;

    @Column(name = "dream_school5")
    private String dreamSchool5;

    @Column(name = "dream_school5_probability")
    private Integer dreamSchool5Probability;

    @Column(name = "dream_school6")
    private String dreamSchool6;

    @Column(name = "dream_school6_probability")
    private Integer dreamSchool6Probability;

    // 志望校を設定するメソッド
    public void setDreamSchool(int index, String dreamSchool) {
        switch (index) {
            case 1:
                this.dreamSchool1 = dreamSchool;
                break;
            case 2:
                this.dreamSchool2 = dreamSchool;
                break;
            case 3:
                this.dreamSchool3 = dreamSchool;
                break;
            case 4:
                this.dreamSchool4 = dreamSchool;
                break;
            case 5:
                this.dreamSchool5 = dreamSchool;
                break;
            case 6:
                this.dreamSchool6 = dreamSchool;
                break;
            default:
                throw new IllegalArgumentException("Invalid index for dream school: " + index);
        }
    }

    // 合格可能性を設定するメソッド
    public void setDreamSchoolProbability(int index, Integer probability) {
        switch (index) {
            case 1:
                this.dreamSchool1Probability = probability;
                break;
            case 2:
                this.dreamSchool2Probability = probability;
                break;
            case 3:
                this.dreamSchool3Probability = probability;
                break;
            case 4:
                this.dreamSchool4Probability = probability;
                break;
            case 5:
                this.dreamSchool5Probability = probability;
                break;
            case 6:
                this.dreamSchool6Probability = probability;
                break;
            default:
                throw new IllegalArgumentException("Invalid index for dream school probability: " + index);
        }
    }

    public Integer getAdjustedScore() {
        return (int) Math.round(Stream.of(
                        this.getMath(),
                        this.getJapanese(),
                        this.getEnglish(),
                        this.getScience(),
                        this.getSocial()
                ).filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum() * 1.4);
    }
}