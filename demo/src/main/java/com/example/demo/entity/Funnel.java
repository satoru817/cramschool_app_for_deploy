package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "funnels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer funnelId;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "funnel",fetch=FetchType.LAZY)
    private List<Inquiry> inquiries;
}