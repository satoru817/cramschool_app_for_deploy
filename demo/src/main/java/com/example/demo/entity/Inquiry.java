package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

// Inquiry.java
@Entity
@Table(name = "inquiries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inquiryId;

    @ManyToOne
    @JoinColumn(name = "funnel_id", nullable = false)
    private Funnel funnel;

    @ManyToOne
    @JoinColumn(name = "school_branch_id", nullable = false)
    private CramSchool cramSchool;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "introducer_id")
    private Student introducer;

    private Integer el1;

    @Column(nullable = false)
    private String nameKanji;

    @Column(nullable = false)
    private String nameKana;

    @Column(nullable = false)
    private LocalDate inquiryDate;

    private Integer code;

    private LocalDate enrollmentDate;

    private LocalDate withdrawalDate;

    @OneToMany(mappedBy = "inquiry",fetch = FetchType.LAZY)
    private List<ActionHistory> actionHistories;
}