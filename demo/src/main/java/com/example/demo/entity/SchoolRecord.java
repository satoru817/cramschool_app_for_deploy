package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "school_records")
public class SchoolRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_record_id")
    private Integer schoolRecordId;

    @ManyToOne
    @JoinColumn(name="school_record_set_id",nullable = false)
    @JsonIgnore
    private SchoolRecordSet schoolRecordSet;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    @JsonIgnore
    private School school;




}
