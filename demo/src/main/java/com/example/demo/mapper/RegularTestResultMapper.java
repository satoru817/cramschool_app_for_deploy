package com.example.demo.mapper;

import com.example.demo.dto.RegularTestResultDto;
import com.example.demo.dto.RegularTestResultDtoForChart;
import com.example.demo.entity.RegularTestResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RegularTestResultMapper {

    public RegularTestResultDtoForChart toChartDTO(RegularTestResult entity) {
        if (entity == null) return null;

        RegularTestResultDtoForChart dto = new RegularTestResultDtoForChart();

        // 基本情報
        dto.setRegularTestResultId(entity.getRegularTestResultId());

        // RegularTestの情報を設定
        if (entity.getRegularTest() != null) {
            dto.setTestName(entity.getRegularTest().getTitle());
            dto.setTestPeriod(entity.getRegularTest().getDate());

            // 平均点の設定
            dto.setJapaneseAverageScore(entity.getRegularTest().getJapaneseAverageScore());
            dto.setMathAverageScore(entity.getRegularTest().getMathAverageScore());
            dto.setEnglishAverageScore(entity.getRegularTest().getEnglishAverageScore());
            dto.setScienceAverageScore(entity.getRegularTest().getScienceAverageScore());
            dto.setSocialAverageScore(entity.getRegularTest().getSocialAverageScore());
            dto.setMusicAverageScore(entity.getRegularTest().getMusicAverageScore());
            dto.setArtAverageScore(entity.getRegularTest().getArtAverageScore());
            dto.setTechAverageScore(entity.getRegularTest().getTechAverageScore());
            dto.setPeAverageScore(entity.getRegularTest().getPeAverageScore());

            // 合計の平均点を計算
            // 5教科
            int total5Avg = Stream.of(
                            entity.getRegularTest().getJapaneseAverageScore(),
                            entity.getRegularTest().getMathAverageScore(),
                            entity.getRegularTest().getEnglishAverageScore(),
                            entity.getRegularTest().getScienceAverageScore(),
                            entity.getRegularTest().getSocialAverageScore()
                    )
                    .filter(Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum();
            dto.setTotal5AverageScore(total5Avg);

            // 9教科
            int total9Avg = Stream.of(
                            entity.getRegularTest().getJapaneseAverageScore(),
                            entity.getRegularTest().getMathAverageScore(),
                            entity.getRegularTest().getEnglishAverageScore(),
                            entity.getRegularTest().getScienceAverageScore(),
                            entity.getRegularTest().getSocialAverageScore(),
                            entity.getRegularTest().getMusicAverageScore(),
                            entity.getRegularTest().getArtAverageScore(),
                            entity.getRegularTest().getTechAverageScore(),
                            entity.getRegularTest().getPeAverageScore()
                    )
                    .filter(Objects::nonNull)
                    .mapToInt(Integer::intValue)
                    .sum();
            dto.setTotal9AverageScore(total9Avg);
        }

        // 点数の設定
        dto.setJapanese(entity.getJapanese());
        dto.setMath(entity.getMath());
        dto.setEnglish(entity.getEnglish());
        dto.setScience(entity.getScience());
        dto.setSocial(entity.getSocial());
        dto.setMusic(entity.getMusic());
        dto.setArt(entity.getArt());
        dto.setTech(entity.getTech());
        dto.setPe(entity.getPe());
        dto.setTotal5(entity.getTotal5());
        dto.setTotal9(entity.getTotal9());

        return dto;
    }

    public List<RegularTestResultDtoForChart> toChartDTOList(List<RegularTestResult> results) {
        return results.stream()
                .map(this::toChartDTO)
                .collect(Collectors.toList());
    }
}