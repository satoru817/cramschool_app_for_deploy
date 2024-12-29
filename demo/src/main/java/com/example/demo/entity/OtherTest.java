package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "other_tests", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"school_id", "semester", "grade"})
})
public class OtherTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "other_test_id")
    private Integer otherTestId;

    @Column(name="name", nullable = false)
    private String name;

    @Transient
    private Integer schoolId;

    @JoinColumn(name="school_id")
    @ManyToOne
    private School school;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "semester", nullable = false)
    private Byte semester;

    @NotNull
    @Column(name = "grade", nullable = false)
    private Byte grade;

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