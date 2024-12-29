package com.example.demo.repository;

import com.example.demo.dto.AverageScoreForKlass;
import com.example.demo.entity.OtherTest;
import com.example.demo.entity.OtherTestResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OtherTestResultRepository extends JpaRepository<OtherTestResult,Integer> {

    @Query("SELECT otr FROM OtherTestResult otr " +
            "WHERE otr.otherTest = :otherTest " +
            "AND otr.student.name = 'ave' ")
    OtherTestResult getAverageResult(@Param("otherTest") OtherTest otherTest);

    Page<OtherTestResult> findAllByOtherTest(OtherTest otherTest,Pageable pageable);

    @Query("SELECT otr FROM OtherTestResult otr " +
            "WHERE otr.otherTest = :otherTest " +
            "AND otr.student.name LIKE %:studentName%")
    Page<OtherTestResult> findAllByOtherTestAndStudentName(@Param("otherTest")OtherTest otherTest,
                                                           @Param("studentName")String studentName,
                                                           Pageable pageable);

    @Query("SELECT otr FROM OtherTestResult otr " +
            "WHERE otr.otherTest.otherTestId = :otherTestId" )
    List<OtherTestResult> findAllByOtherTestId(@Param("otherTestId")Integer otherTestId);

    @Query("""
            SELECT otr FROM OtherTestResult otr
            JOIN otr.student s
            WHERE s.id = :studentId
            """)
    List<OtherTestResult> findAllByStudentId(@Param("studentId")Integer studentId);


    @Query("""
        SELECT otr FROM OtherTestResult otr WHERE otr.otherTest = :otherTest
        ORDER BY
        CASE WHEN :sort = 'japanese' AND :direction = 1 THEN otr.japanese END ASC,
        CASE WHEN :sort = 'japanese' AND :direction = 0 THEN otr.japanese END DESC,
        CASE WHEN :sort = 'math' AND :direction = 1 THEN otr.math END ASC,
        CASE WHEN :sort = 'math' AND :direction = 0 THEN otr.math END DESC,
        CASE WHEN :sort = 'english' AND :direction = 1 THEN otr.english END ASC,
        CASE WHEN :sort = 'english' AND :direction = 0 THEN otr.english END DESC,
        CASE WHEN :sort = 'science' AND :direction = 1 THEN otr.science END ASC,
        CASE WHEN :sort = 'science' AND :direction = 0 THEN otr.science END DESC,
        CASE WHEN :sort = 'social' AND :direction = 1 THEN otr.social END ASC,
        CASE WHEN :sort = 'social' AND :direction = 0 THEN otr.social END DESC,
        CASE WHEN :sort = 'music' AND :direction = 1 THEN otr.music END ASC,
        CASE WHEN :sort = 'music' AND :direction = 0 THEN otr.music END DESC,
        CASE WHEN :sort = 'pe' AND :direction = 1 THEN otr.pe END ASC,
        CASE WHEN :sort = 'pe' AND :direction = 0 THEN otr.pe END DESC,
        CASE WHEN :sort = 'art' AND :direction = 1 THEN otr.art END ASC,
        CASE WHEN :sort = 'art' AND :direction = 0 THEN otr.art END DESC,
        CASE WHEN :sort = 'tech' AND :direction = 1 THEN otr.tech END ASC,
        CASE WHEN :sort = 'tech' AND :direction = 0 THEN otr.tech END DESC,
        CASE WHEN :sort = 'total5' AND :direction = 1 THEN otr.total5 END ASC,
        CASE WHEN :sort = 'total5' AND :direction = 0 THEN otr.total5 END DESC,
        CASE WHEN :sort = 'total9' AND :direction = 1 THEN otr.total9 END ASC,
        CASE WHEN :sort = 'total9' AND :direction = 0 THEN otr.total9 END DESC,
        otr.otherTestResultId ASC
    """)
    List<OtherTestResult> findAllByOtherTestWithSort(@Param("otherTest")OtherTest otherTest,
                                                     @Param("sort")String sort,
                                                     @Param("direction")Integer direction);

    @Query("""
            SELECT otr FROM OtherTestResult otr WHERE otr.otherTest = :otherTest
            """)
    List<OtherTestResult> findAllResultByOtherTest(@Param("otherTest")OtherTest otherTest);


    @Query("""
                SELECT otr FROM OtherTestResult otr 
                JOIN otr.student student 
                WHERE otr.otherTest = :otherTest
                ORDER BY CASE WHEN :direction = 1 
                    THEN (
                        SELECT COALESCE(MAX(k.sortOrder), 0) 
                        FROM KlassStudent ks 
                        JOIN ks.klass k 
                        WHERE ks.student = student 
                        AND :date BETWEEN ks.createdAt AND ks.changedAt
                    )
                    ELSE -(
                        SELECT COALESCE(MAX(k.sortOrder), 0) 
                        FROM KlassStudent ks 
                        JOIN ks.klass k 
                        WHERE ks.student = student 
                        AND :date BETWEEN ks.createdAt AND ks.changedAt
                    )
                END
            """)
    List<OtherTestResult> findAllByOtherTestWithKlassSortAndDate(@Param("otherTest")OtherTest otherTest,
                                                                 @Param("date")LocalDate date,
                                                                 @Param("direction")Integer direction);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(otr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(otr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(otr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(otr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(otr.social), 1)
                    END,
                    COALESCE(k.name, '未所属'),
                    ''
                )
                FROM OtherTestResult otr
                JOIN otr.student student
                LEFT JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                LEFT JOIN ks.klass k ON k.subject = :subject
                WHERE otr.otherTest = :otherTest
                GROUP BY k.name
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreForKlass> findAverageForEachKlassForEachSubject(@Param("subject")String subject, 
                                                                     @Param("otherTest")OtherTest otherTest, 
                                                                     @Param("date")LocalDate date);


    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :totalSubject = 'total5' then ROUND(avg(otr.total5), 1)
                         WHEN :totalSubject = 'total9' then ROUND(avg(otr.total9), 1)
                    END,
                    COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属'),
                    ''
                )
                FROM OtherTestResult otr
                JOIN otr.student student
                LEFT JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                LEFT JOIN ks.klass k ON ks.klass = k
                    AND k.sortOrder = (
                        SELECT MAX(k2.sortOrder)
                        FROM KlassStudent ks2
                        JOIN ks2.klass k2
                        WHERE ks2.student = student
                        AND :date BETWEEN ks2.createdAt AND ks2.changedAt
                    )
                WHERE otr.otherTest = :otherTest
                GROUP BY COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属')
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreForKlass> findAverageForIntegratedKlassForTotalSubject(@Param("totalSubject")String totalSubject,
                                                                            @Param("otherTest")OtherTest otherTest,
                                                                            @Param("date") LocalDate date);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(otr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(otr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(otr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(otr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(otr.social), 1)
                    END,
                    '全て',
                    ''
                )
                FROM OtherTestResult otr
                JOIN otr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                LEFT JOIN ks.klass k ON k.subject = :subject
                WHERE otr.otherTest = :otherTest
            """)
    AverageScoreForKlass findAverageForEveryKlassForEachSubject(@Param("subject")String subject,
                                                                @Param("otherTest")OtherTest otherTest,
                                                                @Param("date")LocalDate date);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :totalSubject = 'total5' then ROUND(avg(otr.total5), 1)
                         WHEN :totalSubject = 'total9' then ROUND(avg(otr.total9), 1)
                    END,
                    '全て',
                    ''
                )
                FROM OtherTestResult otr
                JOIN otr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                WHERE otr.otherTest = :otherTest
            """)
    AverageScoreForKlass findAverageForEveryClassForEachTotalSubject(@Param("totalSubject")String totalSubject,
                                                                     @Param("otherTest")OtherTest otherTest,
                                                                     @Param("date") LocalDate date);
}
