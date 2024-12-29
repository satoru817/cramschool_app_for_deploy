package com.example.demo.repository;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.Klass;
import com.example.demo.entity.KlassUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface KlassUserRepository extends JpaRepository<KlassUser, Integer> {


    @Query("""
            SELECT ku FROM KlassUser ku
            WHERE :date BETWEEN ku.startDate AND ku.endDate
            AND ku.klass.klassId = :klassId
            AND ku.grade = :grade
            """)
    Optional<KlassUser> findRecentKlassUserByKlassIdAndGrade(@Param("klassId") Integer klassId,
                                                             @Param("grade") Integer grade,
                                                             @Param("date") LocalDate date);

    @Query("""
            SELECT ku FROM KlassUser ku
            INNER JOIN ku.klass klass
            WHERE klass.cramSchool = :cramSchool
            AND klass.name = :klassName
            AND klass.subject = :subject
            AND ku.grade = :grade
            AND :testDate BETWEEN ku.startDate AND ku.endDate
            """)
    Optional<KlassUser> findByNameAndSubjectAndGradeAndTestDate(@Param("cramSchool") CramSchool cramSchool,
                                                                @Param("klassName") String klassName,
                                                                @Param("subject") String subject,
                                                                @Param("grade") Integer grade,
                                                                @Param("testDate") LocalDate testDate);

    @Query("""
            SELECT ku FROM KlassUser ku
            INNER JOIN ku.klass klass
            WHERE klass = :klass
            AND ku.grade = :grade
            """)
    List<KlassUser> findAllByKlassAndGrade(@Param("klass")Klass klass,
                                           @Param("grade")Integer grade);

    @Query("""
            SELECT COUNT(DISTINCT ku.classUserId) FROM KlassUser ku
            INNER JOIN ku.klass klass
            WHERE klass = :klass
            AND ku.grade = :grade
            AND NOT(ku.endDate < :startDate OR ku.startDate > :endDate)
            AND ku != :klassUser
            """)
    Integer countWrapByKlassAndGradeAndStartDateAndEndDate(@Param("klassUser")KlassUser klassUser,
                                                           @Param("klass")Klass klass,
                                                           @Param("grade")Integer grade,
                                                           @Param("startDate")LocalDate startDate,
                                                           @Param("endDate")LocalDate endDate);
}
