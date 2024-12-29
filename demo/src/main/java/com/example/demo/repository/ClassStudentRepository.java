package com.example.demo.repository;

import com.example.demo.entity.Klass;
import com.example.demo.entity.KlassStudent;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClassStudentRepository extends JpaRepository<KlassStudent, Integer> {
    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = 'japanese' " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassForJapaneseByStudentAndDate(@Param("student") Student student, @Param("date") LocalDate date);


    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = 'math' " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassForMathByStudentAndDate(@Param("student") Student student, @Param("date") LocalDate date);

    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = 'science' " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassForScienceByStudentAndDate(@Param("student") Student student, @Param("date") LocalDate date);

    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = 'english' " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassForEnglishByStudentAndDate(@Param("student") Student student, @Param("date") LocalDate date);


    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = 'social' " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassForSocialByStudentAndDate(@Param("student") Student student, @Param("date") LocalDate date);

    @Query(value = "SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.subject = :subject " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "ORDER BY ks.id DESC")
    List<KlassStudent> findKlassStudentForASubjectAndDate(@Param("student") Student student, @Param("date") LocalDate date, @Param("subject") String subject);

    @Query("SELECT ks FROM KlassStudent ks " +
            "JOIN ks.klass k " +
            "WHERE ks.student = :student " +
            "AND k.klassId = :klassId " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt")
    KlassStudent findKlassStudentForKlassIdAndDate(@Param("student") Student student, @Param("date") LocalDate date, @Param("klassId") Integer klassId);


    @Query("""
            SELECT (CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name end) FROM KlassStudent ks 
            JOIN ks.klass k
            WHERE ks.student = :student
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            ORDER BY k.sortOrder DESC
            LIMIT 1
            """)
    Optional<String> findTopKlassNameByStudentAndDateOrderBySortOrderDesc(
            @Param("student") Student student,
            @Param("date") LocalDate date
    );

    @Query("""
            SELECT ks FROM KlassStudent ks
            INNER JOIN ks.klass k
            WHERE ks.student = :student
            AND k.subject = :subject
            ORDER by ks.createdAt
            """)
    List<KlassStudent> findKlassStudentForEachSubjectAndStudent(@Param("subject") String subject,
                                                                @Param("student") Student student);
    @Query("""
        SELECT COUNT(DISTINCT ks.id)
        FROM KlassStudent ks
        INNER JOIN ks.klass k
        WHERE k.subject = :subject
        AND ks.student = :student
        AND NOT (ks.changedAt < :createdAt OR ks.createdAt > :changedAt)
        AND ks.id != :klassStudentId
        """)
    Integer countWrapBySubjectAndStudentAndCreatedAndChangedAt(
            @Param("klassStudentId") Integer klassStudentId,
            @Param("subject") String subject,
            @Param("student") Student student,
            @Param("createdAt") LocalDate createdAt,
            @Param("changedAt") LocalDate changedAt
    );
}
