package com.example.demo.repository;

import com.example.demo.dto.AverageScoreAndStandardScoreForKlass;
import com.example.demo.entity.MockTest;
import com.example.demo.entity.MockTestResult;
import com.example.demo.entity.MockTestResultId; // 複合主キーのインポート
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MockTestResultRepository extends JpaRepository<MockTestResult, MockTestResultId> {

    public Page<MockTestResult> findAllByMockTestId(Integer mockTestId, Pageable pageable);

    Page<MockTestResult> findAllByMockTestIdAndStudentNameContaining(Integer mockTestId, String studentName, Pageable pageable);

    @Query(value = """
                SELECT rank, subject_score, standard_score, student_name, student_id
                FROM (
                    SELECT 
                        r.student_id,
                        s.name as student_name,
                        CASE WHEN :subjectField = 'japanese' THEN r.japanese
                             WHEN :subjectField = 'math' THEN r.math
                             WHEN :subjectField = 'english' THEN r.english
                             WHEN :subjectField = 'science' THEN r.science
                             WHEN :subjectField = 'social' THEN r.social
                             WHEN :subjectField = 'total3' THEN r.total3
                             WHEN :subjectField = 'total5' THEN r.total5
                        END as subject_score,
                        CASE WHEN :subjectField = 'japanese' THEN r.japanese_ss
                             WHEN :subjectField = 'math' THEN r.math_ss
                             WHEN :subjectField = 'english' THEN r.english_ss
                             WHEN :subjectField = 'science' THEN r.science_ss
                             WHEN :subjectField = 'social' THEN r.social_ss
                             WHEN :subjectField = 'total3' THEN r.total3_ss
                             WHEN :subjectField = 'total5' THEN r.total5_ss                 
                        END as standard_score,
                        RANK() OVER (
                            ORDER BY 
                                CASE WHEN :isAsc = true THEN 
                                    CASE WHEN :subjectField = 'japanese' THEN r.japanese
                                         WHEN :subjectField = 'math' THEN r.math
                                         WHEN :subjectField = 'english' THEN r.english
                                         WHEN :subjectField = 'science' THEN r.science
                                         WHEN :subjectField = 'social' THEN r.social
                                         WHEN :subjectField = 'total3' THEN r.total3
                                         WHEN :subjectField = 'total5' THEN r.total5
                                    END
                                END ASC,
                                CASE WHEN :isAsc = false THEN 
                                    CASE WHEN :subjectField = 'japanese' THEN r.japanese
                                         WHEN :subjectField = 'math' THEN r.math
                                         WHEN :subjectField = 'english' THEN r.english
                                         WHEN :subjectField = 'science' THEN r.science
                                         WHEN :subjectField = 'social' THEN r.social
                                         WHEN :subjectField = 'total3' THEN r.total3
                                         WHEN :subjectField = 'total5' THEN r.total5                             
                                    END
                                END DESC
                        ) as rank
                    FROM mock_test_results r
                    JOIN students s ON s.student_id = r.student_id
                    WHERE r.mock_test_id = :mockTestId
                ) ranking
                WHERE ranking.rank <= :maxRank
                ORDER BY 
                    CASE WHEN :isAsc = true THEN ranking.subject_score END ASC,
                    CASE WHEN :isAsc = false THEN ranking.subject_score END DESC
            """, nativeQuery = true)
    List<Map<String, Object>> findSubjectRanking(
            @Param("mockTestId") Integer mockTestId,
            @Param("maxRank") Integer maxRank,
            @Param("isAsc") Boolean isAsc,
            @Param("subjectField") String subjectField
    );

    @Query("""
            SELECT mtr FROM MockTestResult mtr
            JOIN mtr.mockTest 
            WHERE mtr.student.id = :studentId
            ORDER BY mockTest.date ASC
            """)
    List<MockTestResult> findAllByStudentId(Integer studentId);

    @Query("""
            SELECT mtr FROM MockTestResult mtr
            JOIN mtr.mockTest mt
            WHERE mt.id = :mockTestId
            """)
    List<MockTestResult> findAllResultByMockTestId(@Param("mockTestId") Integer mockTestId);

    @Query("SELECT m FROM MockTestResult m WHERE m.mockTest.id = :mockTestId " +
            "ORDER BY " +
            "CASE WHEN :sortField = 'japanese' AND :sortDirection = 'asc' THEN m.japanese END ASC, " +
            "CASE WHEN :sortField = 'japanese' AND :sortDirection = 'desc' THEN m.japanese END DESC, " +
            "CASE WHEN :sortField = 'japaneseSs' AND :sortDirection = 'asc' THEN m.japaneseSs END ASC, " +
            "CASE WHEN :sortField = 'japaneseSs' AND :sortDirection = 'desc' THEN m.japaneseSs END DESC, " +
            "CASE WHEN :sortField = 'math' AND :sortDirection = 'asc' THEN m.math END ASC, " +
            "CASE WHEN :sortField = 'math' AND :sortDirection = 'desc' THEN m.math END DESC, " +
            "CASE WHEN :sortField = 'mathSs' AND :sortDirection = 'asc' THEN m.mathSs END ASC, " +
            "CASE WHEN :sortField = 'mathSs' AND :sortDirection = 'desc' THEN m.mathSs END DESC, " +
            "CASE WHEN :sortField = 'english' AND :sortDirection = 'asc' THEN m.english END ASC, " +
            "CASE WHEN :sortField = 'english' AND :sortDirection = 'desc' THEN m.english END DESC, " +
            "CASE WHEN :sortField = 'englishSs' AND :sortDirection = 'asc' THEN m.englishSs END ASC, " +
            "CASE WHEN :sortField = 'englishSs' AND :sortDirection = 'desc' THEN m.englishSs END DESC, " +
            "CASE WHEN :sortField = 'social' AND :sortDirection = 'asc' THEN m.social END ASC, " +
            "CASE WHEN :sortField = 'social' AND :sortDirection = 'desc' THEN m.social END DESC, " +
            "CASE WHEN :sortField = 'socialSs' AND :sortDirection = 'asc' THEN m.socialSs END ASC, " +
            "CASE WHEN :sortField = 'socialSs' AND :sortDirection = 'desc' THEN m.socialSs END DESC, " +
            "CASE WHEN :sortField = 'science' AND :sortDirection = 'asc' THEN m.science END ASC, " +
            "CASE WHEN :sortField = 'science' AND :sortDirection = 'desc' THEN m.science END DESC, " +
            "CASE WHEN :sortField = 'scienceSs' AND :sortDirection = 'asc' THEN m.scienceSs END ASC, " +
            "CASE WHEN :sortField = 'scienceSs' AND :sortDirection = 'desc' THEN m.scienceSs END DESC, " +
            "CASE WHEN :sortField = 'total3' AND :sortDirection = 'asc' THEN m.total3 END ASC, " +
            "CASE WHEN :sortField = 'total3' AND :sortDirection = 'desc' THEN m.total3 END DESC, " +
            "CASE WHEN :sortField = 'total3Ss' AND :sortDirection = 'asc' THEN m.total3Ss END ASC, " +
            "CASE WHEN :sortField = 'total3Ss' AND :sortDirection = 'desc' THEN m.total3Ss END DESC, " +
            "CASE WHEN :sortField = 'total5' AND :sortDirection = 'asc' THEN m.total5 END ASC, " +
            "CASE WHEN :sortField = 'total5' AND :sortDirection = 'desc' THEN m.total5 END DESC, " +
            "CASE WHEN :sortField = 'total5Ss' AND :sortDirection = 'asc' THEN m.total5Ss END ASC, " +
            "CASE WHEN :sortField = 'total5Ss' AND :sortDirection = 'desc' THEN m.total5Ss END DESC, " +
            "m.id ASC")
        // デフォルトソート
    List<MockTestResult> findAllResultsByMockTestIdWithSort(
            @Param("mockTestId") Integer mockTestId,
            @Param("sortField") String sortField,
            @Param("sortDirection") String sortDirection
    );


    @Query("""
                SELECT mtr FROM MockTestResult mtr 
                JOIN mtr.student student 
                WHERE mtr.mockTest = :mockTest 
                ORDER BY CASE WHEN :sortDirection = 'asc' 
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
    List<MockTestResult> findAllResultsByMockTestWithIntegratedKlassSortAndDate(
            @Param("mockTest") MockTest mockTest,
            @Param("sortDirection") String sortDirection,
            @Param("date") LocalDate date
    );

    //5科目のクラスごと平均を出してくるメソッドがいる。COALESCE(ku.user.name, '割り当て無し')
    //LEFT JOIN KlassUser ku ON ku.klass = k
    //                    AND :date BETWEEN ku.startDate AND ku.endDate
    //                    AND :grade = ku.grade
    //, COALESCE(ku.user.name,'割り当てなし')
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreAndStandardScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(mtr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(mtr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(mtr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(mtr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(mtr.social), 1)
                    END,
                    CASE WHEN :subject = 'japanese' then ROUND(avg(mtr.japaneseSs), 1)
                         WHEN :subject = 'math' then ROUND(avg(mtr.mathSs), 1)
                         WHEN :subject = 'english' then ROUND(avg(mtr.englishSs), 1)
                         WHEN :subject = 'science' then ROUND(avg(mtr.scienceSs), 1)
                         WHEN :subject = 'social' then ROUND(avg(mtr.socialSs), 1)
                    END,
                    COALESCE(k.name, '未所属'),
                    ''
                )
                FROM MockTestResult mtr
                JOIN mtr.student student
                LEFT JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                LEFT JOIN ks.klass k ON k.subject = :subject
                WHERE mtr.mockTest = :mockTest
                GROUP BY COALESCE(k.name,'未所属') 
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreAndStandardScoreForKlass> findAverageForEachKlassForEachSubject(@Param("subject") String subject,
                                                                                     @Param("mockTest") MockTest mockTest,
                                                                                     @Param("date") LocalDate date
    );

    //

    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreAndStandardScoreForKlass(
                    CASE WHEN :totalSubject = 'total3' then ROUND(avg(mtr.total3), 1)
                         WHEN :totalSubject = 'total5' then ROUND(avg(mtr.total5), 1)
                    END,
                    CASE WHEN :totalSubject = 'total3' then ROUND(avg(mtr.total3Ss), 1)
                         WHEN :totalSubject = 'total5' then ROUND(avg(mtr.total5Ss), 1)
                    END,
                    COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属'),
                    ''
                )
                FROM MockTestResult mtr
                JOIN mtr.student student
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
                WHERE mtr.mockTest = :mockTest
                GROUP BY COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属')
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreAndStandardScoreForKlass> findAverageForIntegratedKlassForTotalSubject(
            @Param("totalSubject") String totalSubject,
            @Param("mockTest") MockTest mockTest,
            @Param("date") LocalDate date
    );

    @Query("SELECT mtr FROM MockTestResult mtr WHERE mtr.mockTest = :mockTest ORDER BY mtr.student.id LIMIT 1")
    MockTestResult findFirstByMockTest(MockTest mockTest);


    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreAndStandardScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(mtr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(mtr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(mtr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(mtr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(mtr.social), 1)
                    END,
                    CASE WHEN :subject = 'japanese' then ROUND(avg(mtr.japaneseSs), 1)
                         WHEN :subject = 'math' then ROUND(avg(mtr.mathSs), 1)
                         WHEN :subject = 'english' then ROUND(avg(mtr.englishSs), 1)
                         WHEN :subject = 'science' then ROUND(avg(mtr.scienceSs), 1)
                         WHEN :subject = 'social' then ROUND(avg(mtr.socialSs), 1)
                    END,
                    '全て',
                    ''
                )
                FROM MockTestResult mtr
                JOIN mtr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                INNER JOIN ks.klass k ON k.subject = :subject
                WHERE mtr.mockTest = :mockTest
            """)
    AverageScoreAndStandardScoreForKlass findAverageForEveryKlassForEachSubject(
            @Param("subject") String subject,
            @Param("mockTest") MockTest mockTest,
            @Param("date") LocalDate testDate
    );


    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreAndStandardScoreForKlass(
                    CASE WHEN :totalSubject = 'total3' then ROUND(avg(mtr.total3), 1)
                         WHEN :totalSubject = 'total5' then ROUND(avg(mtr.total5), 1)
                    END,
                    CASE WHEN :totalSubject = 'total3' then ROUND(avg(mtr.total3Ss), 1)
                         WHEN :totalSubject = 'total5' then ROUND(avg(mtr.total5Ss), 1)
                    END,
                    '全て',
                    ''
                )
                FROM MockTestResult mtr
                JOIN mtr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                WHERE mtr.mockTest = :mockTest
            """)
    AverageScoreAndStandardScoreForKlass findAverageForEveryKlassForEachTotalSubject(
            @Param("totalSubject") String totalSubject,
            @Param("mockTest") MockTest mockTest,
            @Param("date") LocalDate testDate
    );
}
