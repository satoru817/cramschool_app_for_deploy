package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
@Table(name="class_students")
public class KlassStudent {
    @Id
    @Column(name="class_student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//classStudentIdにしたい

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name="class_id")
    private Klass klass;

    @Column(name="created_at")
    private LocalDate createdAt;

    @Column(name="changed_at")
    private LocalDate changedAt;
}
