package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Integer id;

    @Column(name="code")//これが一意なことを信頼している。
    private Long code;

    @Column(name="el1")
    private Integer el1;

    @Column(name="name")
    private String name;

    @Column(name="furigana")
    private String furigana;

    @JoinColumn(name="cram_school_id")
    @ManyToOne
    private CramSchool cramSchool;

    @JsonIgnore // これで循環参照を防ぎます
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private List<SchoolStudent> schoolStudents;
}
