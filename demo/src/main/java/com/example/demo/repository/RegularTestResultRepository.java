package com.example.demo.repository;

import com.example.demo.dto.AverageScoreForKlass;
import com.example.demo.entity.RegularTest;
import com.example.demo.entity.RegularTestResult;
import com.example.demo.entity.RegularTestSet;
import com.example.demo.entity.Student;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RegularTestResultRepository extends JpaRepository<RegularTestResult,Integer> {
    public Optional<RegularTestResult> findByRegularTestAndStudent(RegularTest regularTest, Student student);

    @Query("SELECT rtr FROM RegularTestResult rtr where " +
            "rtr.regularTest.regularTestId = :regularTestId ")
    Page<RegularTestResult> findByRegularTestId(@Param("regularTestId")Integer regularTestId, Pageable pageable);


    //これはページあぶるにしたい。
    @Query("SELECT rtr FROM RegularTestResult rtr " +
            "WHERE rtr.regularTest.regularTestSet.regularTestSetId = :regularTestSetId")
    Page<RegularTestResult> findByRegularTestSetId(@Param("regularTestSetId") Integer regularTestSetId, Pageable pageable);

    @Query("SELECT rtr FROM RegularTestResult rtr " +
            "WHERE rtr.regularTest.regularTestId = :regularTestId " +
            "AND ( rtr.student.name LIKE %:studentName% OR rtr.student.furigana LIKE %:studentName%　)")
    Page<RegularTestResult> findByRegularTestIdAndStudentName(@Param("regularTestId")Integer regularTestId, @Param("studentName")String studentName, Pageable pageable);

    @Query("SELECT rtr FROM RegularTestResult rtr " +
            "WHERE rtr.regularTest.regularTestSet.regularTestSetId = :regularTestSetId " +
            "AND  ( rtr.student.name LIKE %:studentName% OR rtr.student.furigana LIKE %:studentName%　)")
    Page<RegularTestResult> findByRegularTestSetIdAndStudentName(@Param("regularTestSetId") Integer regularTestSetId,
                                                                 @Param("studentName")String studentName,
                                                                 Pageable pageable);

    @Query("SELECT rtr FROM RegularTestResult rtr " +
            "WHERE rtr.regularTest.regularTestSet.regularTestSetId = :regularTestSetId")
    List<RegularTestResult> findAllByRegularTestSetId(@Param("regularTestSetId")Integer regularTestSetId);

    List<RegularTestResult> findAllByRegularTest(RegularTest regularTest);

    @Query("""
            SELECT rtr FROM RegularTestResult rtr
            JOIN rtr.student s 
            JOIN KlassStudent ks ON ks.student = s
            WHERE rtr.regularTest.regularTestId = :regularTestId 
            AND ks.klass.klassId = :klassId
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            """)
    Page<RegularTestResult> findByRegularTestIdAndKlassId(@Param("regularTestId")Integer regularTestId,
                                                          @Param("klassId")Integer klassId,
                                                          @Param("date") LocalDate date,
                                                          Pageable pageable);

    @Query("""
            SELECT rtr FROM RegularTestResult rtr 
            JOIN rtr.student s
            JOIN KlassStudent ks on ks.student = s
            WHERE rtr.regularTest.regularTestSet.regularTestSetId = :regularTestSetId
            AND ks.klass.klassId = :klassId
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            """)
    Page<RegularTestResult> findByRegularTestSetIdAndKlassId(@Param("regularTestSetId")Integer regularTestSetId,
                                                             @Param("klassId")Integer klassId,
                                                             @Param("date")LocalDate date,
                                                             Pageable pageable);

    @Query("""
            SELECT rtr FROM RegularTestResult rtr
            WHERE rtr.student.id = :studentId
            ORDER BY rtr.regularTest.date DESC
            """)
    List<RegularTestResult> findAllByStudentId(Integer studentId);

    @Query("""
            SELECT rtr FROM RegularTestResult rtr 
            WHERE rtr.student.name = 'ave' 
            AND rtr.regularTest.regularTestId = :regularTestId
            """)
    RegularTestResult getAvgResult(@Param("regularTestId")Integer regularTestId);

    @Query("""
            SELECT rtr FROM RegularTestResult rtr 
            JOIN rtr.student s
            JOIN KlassStudent ks on ks.student = s
            WHERE rtr.regularTest.regularTestSet.regularTestSetId = :regularTestSetId
            AND ks.klass.klassId = :klassId
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            """)
    List<RegularTestResult> findAllResultsByRegularTestSetIdAndKlassId(@Param("regularTestSetId")Integer regularTestSetId,
                                                                       @Param("klassId")Integer klassId,
                                                                       @Param("date") LocalDate date);


    @Query("""
        SELECT rtr FROM RegularTestResult rtr WHERE rtr.regularTest = :regularTest
        ORDER BY
        CASE WHEN :sort = 'japanese' AND :direction = 1 THEN rtr.japanese END ASC,
        CASE WHEN :sort = 'japanese' AND :direction = 0 THEN rtr.japanese END DESC,
        CASE WHEN :sort = 'math' AND :direction = 1 THEN rtr.math END ASC,
        CASE WHEN :sort = 'math' AND :direction = 0 THEN rtr.math END DESC,
        CASE WHEN :sort = 'english' AND :direction = 1 THEN rtr.english END ASC,
        CASE WHEN :sort = 'english' AND :direction = 0 THEN rtr.english END DESC,
        CASE WHEN :sort = 'science' AND :direction = 1 THEN rtr.science END ASC,
        CASE WHEN :sort = 'science' AND :direction = 0 THEN rtr.science END DESC,
        CASE WHEN :sort = 'social' AND :direction = 1 THEN rtr.social END ASC,
        CASE WHEN :sort = 'social' AND :direction = 0 THEN rtr.social END DESC,
        CASE WHEN :sort = 'music' AND :direction = 1 THEN rtr.music END ASC,
        CASE WHEN :sort = 'music' AND :direction = 0 THEN rtr.music END DESC,
        CASE WHEN :sort = 'pe' AND :direction = 1 THEN rtr.pe END ASC,
        CASE WHEN :sort = 'pe' AND :direction = 0 THEN rtr.pe END DESC,
        CASE WHEN :sort = 'art' AND :direction = 1 THEN rtr.art END ASC,
        CASE WHEN :sort = 'art' AND :direction = 0 THEN rtr.art END DESC,
        CASE WHEN :sort = 'tech' AND :direction = 1 THEN rtr.tech END ASC,
        CASE WHEN :sort = 'tech' AND :direction = 0 THEN rtr.tech END DESC,
        CASE WHEN :sort = 'total5' AND :direction = 1 THEN rtr.total5 END ASC,
        CASE WHEN :sort = 'total5' AND :direction = 0 THEN rtr.total5 END DESC,
        CASE WHEN :sort = 'total9' AND :direction = 1 THEN rtr.total9 END ASC,
        CASE WHEN :sort = 'total9' AND :direction = 0 THEN rtr.total9 END DESC,
        rtr.regularTestResultId ASC
    """)
    List<RegularTestResult> findAllByRegularTestWithSort(@Param("regularTest") RegularTest regularTest,
                                                         @Param("sort") String sort,
                                                         @Param("direction") Integer direction);
    @Query("""
        SELECT rtr FROM RegularTestResult rtr WHERE rtr.regularTest.regularTestSet = :regularTestSet
        ORDER BY
        CASE WHEN :sort = 'japanese' AND :direction = 1 THEN rtr.japanese END ASC,
        CASE WHEN :sort = 'japanese' AND :direction = 0 THEN rtr.japanese END DESC,
        CASE WHEN :sort = 'math' AND :direction = 1 THEN rtr.math END ASC,
        CASE WHEN :sort = 'math' AND :direction = 0 THEN rtr.math END DESC,
        CASE WHEN :sort = 'english' AND :direction = 1 THEN rtr.english END ASC,
        CASE WHEN :sort = 'english' AND :direction = 0 THEN rtr.english END DESC,
        CASE WHEN :sort = 'science' AND :direction = 1 THEN rtr.science END ASC,
        CASE WHEN :sort = 'science' AND :direction = 0 THEN rtr.science END DESC,
        CASE WHEN :sort = 'social' AND :direction = 1 THEN rtr.social END ASC,
        CASE WHEN :sort = 'social' AND :direction = 0 THEN rtr.social END DESC,
        CASE WHEN :sort = 'music' AND :direction = 1 THEN rtr.music END ASC,
        CASE WHEN :sort = 'music' AND :direction = 0 THEN rtr.music END DESC,
        CASE WHEN :sort = 'pe' AND :direction = 1 THEN rtr.pe END ASC,
        CASE WHEN :sort = 'pe' AND :direction = 0 THEN rtr.pe END DESC,
        CASE WHEN :sort = 'art' AND :direction = 1 THEN rtr.art END ASC,
        CASE WHEN :sort = 'art' AND :direction = 0 THEN rtr.art END DESC,
        CASE WHEN :sort = 'tech' AND :direction = 1 THEN rtr.tech END ASC,
        CASE WHEN :sort = 'tech' AND :direction = 0 THEN rtr.tech END DESC,
        CASE WHEN :sort = 'total5' AND :direction = 1 THEN rtr.total5 END ASC,
        CASE WHEN :sort = 'total5' AND :direction = 0 THEN rtr.total5 END DESC,
        CASE WHEN :sort = 'total9' AND :direction = 1 THEN rtr.total9 END ASC,
        CASE WHEN :sort = 'total9' AND :direction = 0 THEN rtr.total9 END DESC,
        rtr.regularTestResultId ASC
    """)
    List<RegularTestResult> findAllResultsBySetIdWithSort(@Param("regularTestSet")RegularTestSet regularTestSet,
                                                          @Param("sort")String sort,
                                                          @Param("direction")Integer direction);
    @Query("""
                SELECT rtr FROM RegularTestResult rtr 
                JOIN rtr.student student 
                WHERE rtr.regularTest = :regularTest
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
    List<RegularTestResult> findAllByRegularTestWithKlassSortAndDate(@Param("regularTest")RegularTest regularTest,
                                                                     @Param("date")LocalDate date,
                                                                     @Param("direction")Integer direction);

    @Query("""
                SELECT rtr FROM RegularTestResult rtr 
                JOIN rtr.student student 
                WHERE rtr.regularTest.regularTestSet = :regularTestSet
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
    List<RegularTestResult> findAllByRegularTestSetWithKlassSortAndDate(@Param("regularTestSet")RegularTestSet regularTestSet,
                                                                        @Param("date")LocalDate date,
                                                                        @Param("direction")Integer direction);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(rtr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(rtr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(rtr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(rtr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(rtr.social), 1)
                    END,
                    COALESCE(k.name, '未所属'),
                    ''
                )
                FROM RegularTestResult rtr
                JOIN rtr.student student
                LEFT JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                LEFT JOIN ks.klass k ON k.subject = :subject
                WHERE rtr.regularTest = :regularTest
                GROUP BY k.name
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreForKlass> findAverageForEachKlassForEachSubject(@Param("subject")String subject,
                                                                     @Param("regularTest")RegularTest regularTest,
                                                                     @Param("date")LocalDate date);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :totalSubject = 'total5' then ROUND(avg(rtr.total5), 1)
                         WHEN :totalSubject = 'total9' then ROUND(avg(rtr.total9), 1)
                    END,
                    COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属'),
                    ''
                )
                FROM RegularTestResult rtr
                JOIN rtr.student student
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
                WHERE rtr.regularTest = :regularTest
                GROUP BY COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属')
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreForKlass> findAverageForIntegratedKlassForTotalSubject(@Param("totalSubject")String totalSubject,
                                                                            @Param("regularTest")RegularTest regularTest,
                                                                            @Param("date")LocalDate date);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(rtr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(rtr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(rtr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(rtr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(rtr.social), 1)
                    END,
                    COALESCE(k.name, '未所属'),
                    ''
                )
                FROM RegularTestResult rtr
                JOIN rtr.student student
                LEFT JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                LEFT JOIN ks.klass k ON k.subject = :subject
                WHERE rtr.regularTest.regularTestSet = :regularTestSet
                GROUP BY k.name
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreForKlass> findAverageForEachKlassForEachSubjectForSet(@Param("subject")String subject,
                                                                           @Param("regularTestSet")RegularTestSet regularTestSet,
                                                                           @Param("date")LocalDate calcDate);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :totalSubject = 'total5' then ROUND(avg(rtr.total5), 1)
                         WHEN :totalSubject = 'total9' then ROUND(avg(rtr.total9), 1)
                    END,
                    COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属'),
                    ''
                )
                FROM RegularTestResult rtr
                JOIN rtr.student student
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
                WHERE rtr.regularTest.regularTestSet = :regularTestSet
                GROUP BY COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属')
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreForKlass> findAverageForIntegratedKlassForTotalSubjectForSet(@Param("totalSubject")String value,
                                                                                  @Param("regularTestSet")RegularTestSet regularTestSet,
                                                                                  @Param("date")LocalDate calcDate);
    @Query("SELECT rtr FROM RegularTestResult rtr " +
            "WHERE rtr.regularTest.regularTestSet.regularTestSetId = :regularTestSetId " +
            "AND rtr.student.name != 'ave'")
    List<RegularTestResult> findAllByRegularTestSetIdExcludingAverageMan(@Param("regularTestSetId")Integer regularTestSetId);

    @Query("""
            SELECT rtr FROM RegularTestResult rtr 
            JOIN rtr.student s
            JOIN KlassStudent ks on ks.student = s
            WHERE rtr.regularTest.regularTestSet.regularTestSetId = :regularTestSetId
            AND s.name != 'ave'
            AND ks.klass.klassId = :klassId
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            """)
    List<RegularTestResult> findAllResultsByRegularTestSetIdAndKlassIdExcludingAverageMan(@Param("regularTestSetId")Integer regularTestSetId,
                                                                                          @Param("klassId")Integer klassId,
                                                                                          @Param("date")LocalDate date);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(rtr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(rtr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(rtr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(rtr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(rtr.social), 1)
                    END,
                    '全て',
                    ''
                )
                FROM RegularTestResult rtr
                JOIN rtr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                LEFT JOIN ks.klass k ON k.subject = :subject
                WHERE rtr.regularTest.regularTestSet = :regularTestSet
            """)
    AverageScoreForKlass findAverageForEveryKlassForEachSubjectForSet(@Param("subject")String subject,
                                                                      @Param("regularTestSet")RegularTestSet regularTestSet,
                                                                      @Param("date")LocalDate calcDate);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :totalSubject = 'total5' then ROUND(avg(rtr.total5), 1)
                         WHEN :totalSubject = 'total9' then ROUND(avg(rtr.total9), 1)
                    END,
                    '全て',
                    ''
                )
                FROM RegularTestResult rtr
                JOIN rtr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                WHERE rtr.regularTest.regularTestSet = :regularTestSet
            """)
    AverageScoreForKlass findAverageForEveryKlassForEachTotalSubjectForSet(@Param("totalSubject")String value,
                                                                           @Param("regularTestSet")RegularTestSet regularTestSet,
                                                                           @Param("date")LocalDate calcDate);

    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(rtr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(rtr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(rtr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(rtr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(rtr.social), 1)
                    END,
                    '全て',
                    ''
                )
                FROM RegularTestResult rtr
                JOIN rtr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                LEFT JOIN ks.klass k ON k.subject = :subject
                WHERE rtr.regularTest = :regularTest
            """)
    AverageScoreForKlass findAverageForEveryKlassForEachSubject(@Param("subject")String subject,
                                                                @Param("regularTest")RegularTest regularTest,
                                                                @Param("date")LocalDate date);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :totalSubject = 'total5' then ROUND(avg(rtr.total5), 1)
                         WHEN :totalSubject = 'total9' then ROUND(avg(rtr.total9), 1)
                    END,
                    '全て',
                    ''
                )
                FROM RegularTestResult rtr
                JOIN rtr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                WHERE rtr.regularTest = :regularTest
            """)
    AverageScoreForKlass findAverageForEveryClassForEachTotalSubject(@Param("totalSubject")String totalSubject,
                                                                     @Param("regularTest")RegularTest regularTest,
                                                                     @Param("date")LocalDate date);
}



