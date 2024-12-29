package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "school_record_sets")
public class SchoolRecordSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_record_set_id")
    private Integer schoolRecordSetId;

    @Column(name = "term", nullable = false)
    private Integer term;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    @Column(name = "semester", nullable = false)
    private Integer semester;

    @JoinColumn(name="cram_school_id")
    @ManyToOne
    @JsonIgnore
    private CramSchool cramSchool;
}
