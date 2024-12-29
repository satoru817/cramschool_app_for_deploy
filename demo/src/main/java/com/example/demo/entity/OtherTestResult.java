package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.stream.Stream;


@Data
@Entity
@Table(name = "other_test_results", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"other_test_id", "student_id"})
})
public class OtherTestResult implements TestResult{

    @Transient
    private String klassName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "other_test_result_id")
    private Integer otherTestResultId;

    @ManyToOne
    @JoinColumn(name = "other_test_id")
    private OtherTest otherTest;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "japanese")
    private Integer japanese;

    @Column(name = "math")
    private Integer math;

    @Column(name = "english")
    private Integer english;

    @Column(name = "science")
    private Integer science;

    @Column(name = "social")
    private Integer social;

    @Column(name = "music")
    private Integer music;

    @Column(name = "art")
    private Integer art;

    @Column(name = "tech")
    private Integer tech;

    @Column(name = "pe")
    private Integer pe;

    @Column(name="total5",insertable = false,updatable = false)
    private Integer total5;

    @Column(name="total9",insertable = false,updatable = false)
    private Integer total9;


}
