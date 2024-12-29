package com.example.demo.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "cram_schools")
public class CramSchool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cram_school_id")
    private int cramSchoolId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
