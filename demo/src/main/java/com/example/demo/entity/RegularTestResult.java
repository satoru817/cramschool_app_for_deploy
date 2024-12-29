package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name="regular_test_results")
public class RegularTestResult implements TestResult{
    @Transient
    private String klassName;

    @Id
    @Column(name="regular_test_result_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regularTestResultId;

    @ManyToOne
    @JoinColumn(name="regular_test_id")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RegularTest regularTest;


    @ManyToOne
    @JoinColumn(name="student_id")
    @JsonIgnore
    private Student student;

    @Column(name="japanese")
    private Integer japanese;

    @Column(name="math")
    private Integer math;

    @Column(name="english")
    private Integer english;

    @Column(name="science")
    private Integer science;

    @Column(name="social")
    private Integer social;

    @Column(name="music")
    private Integer music;

    @Column(name="art")
    private Integer art;

    @Column(name="tech")
    private Integer tech;

    @Column(name="pe")
    private Integer pe;

    @Column(name="total5",insertable = false,updatable = false)
    private Integer total5;

    @Column(name="total9",insertable = false,updatable = false)
    private Integer total9;

}
