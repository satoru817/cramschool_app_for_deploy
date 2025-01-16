package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

// Inquiry.java
@Builder
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
    @JoinColumn(name = "cram_school_id", nullable = false)
    private CramSchool cramSchool;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "introducer_id")
    private Student introducer;

    private Integer el1;

    @Transient
    private String gradeStr;

    @Column(nullable = false)
    private String nameKanji;

    @Column(nullable = true)
    private String nameKana;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")//date picker利用の為にこうしている
    private LocalDate inquiryDate;

    private Integer code;

    private LocalDate enrollmentDate;

    private LocalDate withdrawalDate;

    @OneToMany(mappedBy = "inquiry",fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<ActionHistory> actionHistories;
}