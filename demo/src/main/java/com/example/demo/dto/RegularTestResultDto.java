package com.example.demo.dto;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.RegularTestResult;
import com.example.demo.entity.RegularTestSet;
import com.example.demo.repository.RegularTestResultRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.internal.Function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.lang.Math.round;

@Data
@RequiredArgsConstructor
public class RegularTestResultDto {
    private final RegularTestResultRepository regularTestResultRepository;


    private String title;
    private String japanese;
    private String math;
    private String english;
    private String social;
    private String science;
    private String tech;
    private String music;
    private String pe;
    private String art;
    private String total5;
    private String total9;


    public RegularTestResultDto convert(RegularTestResult rtr){
        //RegularTestResult avg = regularTestResultRepository.getAvgResult(rtr.getRegularTest().getRegularTestId());
        RegularTest rt = rtr.getRegularTest();

        // thisを使用して現在のインスタンスのフィールドを設定
        this.title = createTitle(rtr);

        this.japanese = createResultString(rtr.getJapanese(), rt.getJapaneseAverageScore(), rt.getJapaneseFullScore());
        this.math = createResultString(rtr.getMath(), rt.getMathAverageScore(), rt.getMathFullScore());
        this.english = createResultString(rtr.getEnglish(), rt.getEnglishAverageScore(), rt.getEnglishFullScore());
        this.social = createResultString(rtr.getSocial(), rt.getSocialAverageScore(), rt.getSocialFullScore());
        this.science = createResultString(rtr.getScience(), rt.getScienceAverageScore(), rt.getScienceFullScore());
        this.tech = createResultString(rtr.getTech(), rt.getTechAverageScore(), rt.getTechFullScore());
        this.music = createResultString(rtr.getMusic(), rt.getMusicAverageScore(), rt.getMusicFullScore());
        this.pe = createResultString(rtr.getPe(), rt.getPeAverageScore(), rt.getPeFullScore());
        this.art = createResultString(rtr.getArt(), rt.getArtAverageScore(), rt.getArtFullScore());
        //todo:total5とtotal9のメソッドにミスがある。
        this.total5 = getTotalString(
                getTotal5(rtr,rt),//換算された五科目の得点
                getAdjustedTotal5OfAverage(rt)//平均に関して換算された5科目の得点の合
        );
        this.total9 =getTotalString(
                getTotal9(rtr,rt),
                getAdjustedTotal9OfAverage(rt)
        );

        return this;
    }

    private String getTotalString(Integer score,Integer avgScore){
        String diff = avgScore != null?
                (score-avgScore >0 ? "+" + (score-avgScore) :String.valueOf(score-avgScore))
                :String.valueOf(score);
        return String.format("%s ( %s )",score,diff);
    }

    private Integer getAdjustedTotal5OfAverage(RegularTest rt) {
        return Stream.of(
                        calculateScore.apply(rt.getJapaneseAverageScore(), rt.getJapaneseFullScore()),
                        calculateScore.apply(rt.getMathAverageScore(), rt.getMathFullScore()),
                        calculateScore.apply(rt.getEnglishAverageScore(), rt.getEnglishFullScore()),
                        calculateScore.apply(rt.getSocialAverageScore(), rt.getSocialFullScore()),
                        calculateScore.apply(rt.getScienceAverageScore(), rt.getScienceFullScore())
                ).filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getAdjustedTotal9OfAverage(RegularTest rt) {
        return Stream.of(
                        calculateScore.apply(rt.getJapaneseAverageScore(), rt.getJapaneseFullScore()),
                        calculateScore.apply(rt.getMathAverageScore(), rt.getMathFullScore()),
                        calculateScore.apply(rt.getEnglishAverageScore(), rt.getEnglishFullScore()),
                        calculateScore.apply(rt.getSocialAverageScore(), rt.getSocialFullScore()),
                        calculateScore.apply(rt.getScienceAverageScore(), rt.getScienceFullScore()),
                        calculateScore.apply(rt.getTechAverageScore(), rt.getTechFullScore()),
                        calculateScore.apply(rt.getMusicAverageScore(), rt.getMusicFullScore()),
                        calculateScore.apply(rt.getPeAverageScore(), rt.getPeFullScore()),
                        calculateScore.apply(rt.getArtAverageScore(), rt.getArtFullScore())
                ).filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }

    //定期テストのタイトルを作成する
    public String createTitle(RegularTestResult rtr){
        RegularTestSet rts = rtr.getRegularTest().getRegularTestSet();
        return String.format("%s年度%s年生%s学期%s",
                rts.getTerm(),
                rts.getGrade(),
                rts.getSemester(),
                rts.getIsMid() == 0 ? "期末" : "中間"
        );
    }

    //定期テストの結果のStringを"点数 平均との差'という形で作成する 満点の情報を利用する(nullでなければ)
    public String createResultString(Integer score, Integer avgScore, Integer fullScore){
        if(score == null){
            return null;
        }

        if(fullScore == null || fullScore == 0){
            String diff = avgScore != null?
                    (score-avgScore >0 ? "+" + (score-avgScore) :String.valueOf(score-avgScore))
                    :"+"+String.valueOf(score);
            return String.format("%s ( %s )",score,diff);

        }else{
            String diff = avgScore != null?
                    (score-avgScore>0 ? "+" + round((float) ((score - avgScore) * 100) /fullScore) : String.valueOf(round((float) ((score - avgScore) * 100) /fullScore)))
                    :"+"+String.valueOf(round((float) (score * 100) /fullScore));
            float adjustedScore = (float) (score * 100) /fullScore;
            Integer adjustedInteger = round(adjustedScore);
            return String.format("%s ( %s )",adjustedInteger,diff);
        }

    }

    public Integer getTotal5(RegularTestResult rtr, RegularTest rt) {
        // 点数計算のヘルパーメソッド
        if(rtr == null) return null;

        return Stream.of(
                calculateScore.apply(rtr.getJapanese(),rt.getJapaneseFullScore()),
                calculateScore.apply(rtr.getMath(),rt.getMathFullScore()),
                calculateScore.apply(rtr.getEnglish(),rt.getEnglishFullScore()),
                calculateScore.apply(rtr.getSocial(),rt.getSocialFullScore()),
                calculateScore.apply(rtr.getScience(),rt.getScienceFullScore())
        ).filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Integer getTotal9(RegularTestResult rtr, RegularTest rt) {
        // 点数計算のヘルパーメソッド
        if (rtr == null) return null;

        return Stream.of(
                        calculateScore.apply(rtr.getJapanese(), rt.getJapaneseFullScore()),
                        calculateScore.apply(rtr.getMath(), rt.getMathFullScore()),
                        calculateScore.apply(rtr.getEnglish(), rt.getEnglishFullScore()),
                        calculateScore.apply(rtr.getSocial(), rt.getSocialFullScore()),
                        calculateScore.apply(rtr.getScience(), rt.getScienceFullScore()),
                        calculateScore.apply(rtr.getTech(), rt.getTechFullScore()),
                        calculateScore.apply(rtr.getMusic(), rt.getMusicFullScore()),
                        calculateScore.apply(rtr.getPe(), rt.getPeFullScore()),
                        calculateScore.apply(rtr.getArt(), rt.getArtFullScore())
                ).filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }



    BiFunction<Integer, Integer, Integer> calculateScore = (score, fullScore) -> {
        if (score == null) return 0;
        if (fullScore == null || fullScore == 0) return score;
        return round(score * 100 / fullScore);
    };
}