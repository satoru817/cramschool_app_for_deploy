package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<Inquiry> inquiries;
}