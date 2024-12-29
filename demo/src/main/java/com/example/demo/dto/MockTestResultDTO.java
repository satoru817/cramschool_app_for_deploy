package com.example.demo.dto;

import com.example.demo.entity.MockTestResult;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MockTestResultDTO {
    // テスト基本情報
    private String testName;      // 模試名
    private LocalDate date;          // 実施日

    // 科目別点数
    private Integer japanese;     // 国語点数
    private Integer japaneseSs;   // 国語偏差値
    private Integer math;         // 数学点数
    private Integer mathSs;       // 数学偏差値
    private Integer english;      // 英語点数
    private Integer englishSs;    // 英語偏差値
    private Integer science;      // 理科点数
    private Integer scienceSs;    // 理科偏差値
    private Integer social;       // 社会点数
    private Integer socialSs;     // 社会偏差値

    // 合計点・合計偏差値
    private Integer total3;       // 3科目合計点
    private Integer total3Ss;     // 3科目偏差値
    private Integer total5;       // 5科目合計点
    private Integer total5Ss;     // 5科目偏差値
    private Integer adjustedScore; // 700点換算

    // 志望校情報
    private String dreamSchool1;
    private Integer dreamSchool1Probability;
    private String dreamSchool2;
    private Integer dreamSchool2Probability;
    private String dreamSchool3;
    private Integer dreamSchool3Probability;
    private String dreamSchool4;
    private Integer dreamSchool4Probability;
    private String dreamSchool5;
    private Integer dreamSchool5Probability;
    private String dreamSchool6;
    private Integer dreamSchool6Probability;

    // エンティティからDTOを作成するstaticファクトリメソッド
    public static MockTestResultDTO from(MockTestResult entity) {
        MockTestResultDTO dto = new MockTestResultDTO();

        // 基本情報の設定
        if (entity.getMockTest() != null) {
            dto.setTestName(entity.getMockTest().getName());
            dto.setDate(entity.getMockTest().getDate());
        }

        // 科目別点数の設定
        dto.setJapanese(entity.getJapanese());
        dto.setJapaneseSs(entity.getJapaneseSs());
        dto.setMath(entity.getMath());
        dto.setMathSs(entity.getMathSs());
        dto.setEnglish(entity.getEnglish());
        dto.setEnglishSs(entity.getEnglishSs());
        dto.setScience(entity.getScience());
        dto.setScienceSs(entity.getScienceSs());
        dto.setSocial(entity.getSocial());
        dto.setSocialSs(entity.getSocialSs());

        // 合計点の設定
        dto.setTotal3(entity.getTotal3());
        dto.setTotal3Ss(entity.getTotal3Ss());
        dto.setTotal5(entity.getTotal5());
        dto.setTotal5Ss(entity.getTotal5Ss());
        dto.setAdjustedScore(entity.getAdjustedScore());

        // 志望校情報の設定
        dto.setDreamSchool1(entity.getDreamSchool1());
        dto.setDreamSchool1Probability(entity.getDreamSchool1Probability());
        dto.setDreamSchool2(entity.getDreamSchool2());
        dto.setDreamSchool2Probability(entity.getDreamSchool2Probability());
        dto.setDreamSchool3(entity.getDreamSchool3());
        dto.setDreamSchool3Probability(entity.getDreamSchool3Probability());
        dto.setDreamSchool4(entity.getDreamSchool4());
        dto.setDreamSchool4Probability(entity.getDreamSchool4Probability());
        dto.setDreamSchool5(entity.getDreamSchool5());
        dto.setDreamSchool5Probability(entity.getDreamSchool5Probability());
        dto.setDreamSchool6(entity.getDreamSchool6());
        dto.setDreamSchool6Probability(entity.getDreamSchool6Probability());

        return dto;
    }
}