package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="cram_school_users")
public class CramSchoolUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cram_school_user_id")
    private Integer cramSchoolUserId;

    @JoinColumn(name="user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name="cram_school_id")
    @ManyToOne
    private CramSchool cramSchool;
}
