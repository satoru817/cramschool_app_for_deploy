package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="regular_tests")
public class RegularTest {
    @Id
    @Column(name="regular_test_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regularTestId;

    @ManyToOne
    @JoinColumn(name="school_id")
    @JsonIgnore
    private School school;

    @Transient
    private Integer schoolId;

    @ManyToOne
    @JoinColumn(name="regular_test_set_id")
    @JsonIgnore
    private RegularTestSet regularTestSet;

    @Transient
    private Integer regularTestSetId;

    public String getTitle() {
        return String.format("%s年度%s年生%s学期%s",
                regularTestSet.getTerm(),
                regularTestSet.getGrade(),
                regularTestSet.getSemester(),
                regularTestSet.getIsMid() == 0 ? "期末" : "中間"
        );
    }



    @Column(name="date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    // 満点の情報
    @Column(name="japanese_full_score")
    private Integer japaneseFullScore;

    @Column(name="math_full_score")
    private Integer mathFullScore;

    @Column(name="english_full_score")
    private Integer englishFullScore;

    @Column(name="science_full_score")
    private Integer scienceFullScore;

    @Column(name="social_full_score")
    private Integer socialFullScore;

    @Column(name="music_full_score")
    private Integer musicFullScore;

    @Column(name="art_full_score")
    private Integer artFullScore;

    @Column(name="tech_full_score")
    private Integer techFullScore;

    @Column(name="pe_full_score")
    private Integer peFullScore;

    // 平均点の情報
    @Column(name="japanese_average_score")
    private Integer japaneseAverageScore;

    @Column(name="math_average_score")
    private Integer mathAverageScore;

    @Column(name="english_average_score")
    private Integer englishAverageScore;

    @Column(name="science_average_score")
    private Integer scienceAverageScore;

    @Column(name="social_average_score")
    private Integer socialAverageScore;

    @Column(name="music_average_score")
    private Integer musicAverageScore;

    @Column(name="art_average_score")
    private Integer artAverageScore;

    @Column(name="tech_average_score")
    private Integer techAverageScore;

    @Column(name="pe_average_score")
    private Integer peAverageScore;
}
