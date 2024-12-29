package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegularTestResultDtoForChart {
    private Integer regularTestResultId;
    private String testName;
    private LocalDate testPeriod;

    // 生徒の点数
    private Integer japanese;
    private Integer math;
    private Integer english;
    private Integer science;
    private Integer social;
    private Integer music;
    private Integer art;
    private Integer tech;
    private Integer pe;
    private Integer total5;
    private Integer total9;

    // 平均点
    private Integer japaneseAverageScore;
    private Integer mathAverageScore;
    private Integer englishAverageScore;
    private Integer scienceAverageScore;
    private Integer socialAverageScore;
    private Integer musicAverageScore;
    private Integer artAverageScore;
    private Integer techAverageScore;
    private Integer peAverageScore;
    private Integer total5AverageScore;
    private Integer total9AverageScore;

    // 平均との差（表示用）
    public String getJapaneseWithDiff() {
        if (japanese == null || japaneseAverageScore == null) return "-";
        int diff = japanese - japaneseAverageScore;
        return String.format("%d (%+d)", japanese, diff);
    }

    public String getMathWithDiff() {
        if (math == null || mathAverageScore == null) return "-";
        int diff = math - mathAverageScore;
        return String.format("%d (%+d)", math, diff);
    }

    public String getEnglishWithDiff() {
        if (english == null || englishAverageScore == null) return "-";
        int diff = english - englishAverageScore;
        return String.format("%d (%+d)", english, diff);
    }

    public String getScienceWithDiff() {
        if (science == null || scienceAverageScore == null) return "-";
        int diff = science - scienceAverageScore;
        return String.format("%d (%+d)", science, diff);
    }

    public String getSocialWithDiff() {
        if (social == null || socialAverageScore == null) return "-";
        int diff = social - socialAverageScore;
        return String.format("%d (%+d)", social, diff);
    }

    public String getMusicWithDiff() {
        if (music == null || musicAverageScore == null) return "-";
        int diff = music - musicAverageScore;
        return String.format("%d (%+d)", music, diff);
    }

    public String getArtWithDiff() {
        if (art == null || artAverageScore == null) return "-";
        int diff = art - artAverageScore;
        return String.format("%d (%+d)", art, diff);
    }

    public String getTechWithDiff() {
        if (tech == null || techAverageScore == null) return "-";
        int diff = tech - techAverageScore;
        return String.format("%d (%+d)", tech, diff);
    }

    public String getPeWithDiff() {
        if (pe == null || peAverageScore == null) return "-";
        int diff = pe - peAverageScore;
        return String.format("%d (%+d)", pe, diff);
    }

    public String getTotal5WithDiff() {
        if (total5 == null || total5AverageScore == null) return "-";
        int diff = total5 - total5AverageScore;
        return String.format("%d (%+d)", total5, diff);
    }

    public String getTotal9WithDiff() {
        if (total9 == null || total9AverageScore == null) return "-";
        int diff = total9 - total9AverageScore;
        return String.format("%d (%+d)", total9, diff);
    }
}