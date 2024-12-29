package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.time.LocalDate;

@Data
@Entity
@Table(name="class_users")
public class KlassUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="class_user_id")
    private Integer classUserId;

    @ManyToOne
    @JoinColumn(name="class_id")
    private Klass klass;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

    @Column(name="grade")
    private Integer grade;//１から１２で担当クラスの学年を保持する。

}
