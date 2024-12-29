package com.example.demo.repository;

import com.example.demo.dto.AverageScoreForKlass;
import com.example.demo.entity.SchoolRecord;
import com.example.demo.entity.SchoolRecordResult;
import com.example.demo.entity.SchoolRecordSet;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SchoolRecordResultRepository extends JpaRepository<SchoolRecordResult, Integer> {


    List<SchoolRecordResult> findAllBySchoolRecord(SchoolRecord schoolRecord);

    @Query("SELECT srr FROM SchoolRecordResult srr " +
            "WHERE srr.schoolRecord.schoolRecordId = :schoolRecordId")
    Page<SchoolRecordResult> findAllBySchoolRecordId(@Param("schoolRecordId") Integer schoolRecordId, Pageable pageable);

    @Query("SELECT srr FROM SchoolRecordResult srr " +
            "JOIN srr.schoolRecord sr " +
            "JOIN sr.schoolRecordSet srs " +
            "WHERE srs.schoolRecordSetId = :schoolRecordSetId")
    Page<SchoolRecordResult> findAllSchoolRecordBySchoolRecordSetId(@Param("schoolRecordSetId") Integer schoolRecordSetId, Pageable pageable);

    @Query("SELECT srr FROM SchoolRecordResult srr " +
            "WHERE srr.schoolRecord.schoolRecordSet.schoolRecordSetId = :schoolRecordSetId")
    List<SchoolRecordResult> findAllBySchoolRecordSetId(@Param("schoolRecordSetId")Integer schoolRecordSetId);


    @Query("SELECT srr FROM SchoolRecordResult srr " +
            "WHERE srr.schoolRecord.schoolRecordSet.schoolRecordSetId = :schoolRecordSetId " +
            "AND srr.student.name LIKE %:studentName%")
    Page<SchoolRecordResult> findAllBySchoolRecordSetIdAndNameLike(@Param("schoolRecordSetId")Integer schoolRecordSetId,
                                                                   @Param("studentName")String studentName, Pageable pageable);

    @Query("SELECT srr FROM SchoolRecordResult srr " +
            "WHERE srr.schoolRecord.schoolRecordId = :schoolRecordId " +
            "AND srr.student.name LIKE %:studentName%")
    Page<SchoolRecordResult> findAllBySchoolRecordIdAndNameLike(@Param("schoolRecordId")Integer schoolRecordId,
                                                                @Param("studentName")String studentName, Pageable pageable);

    @Query("""
            SELECT srr FROM SchoolRecordResult srr 
            JOIN srr.student s 
            JOIN KlassStudent ks 
            ON ks.student = s 
            WHERE srr.schoolRecord.schoolRecordId = :schoolRecordId
            AND :date BETWEEN ks.createdAt AND ks.changedAt 
            AND ks.klass.klassId = :klassId
            """)
    Page<SchoolRecordResult> findAllBySchoolRecordIdAndKlassId(@Param("schoolRecordId")Integer schoolRecordId,
                                                               @Param("klassId")Integer klassId,
                                                               @Param("date") LocalDate date,
                                                               Pageable pageable);
    //現在の所属クラスを利用することに注意（内申が作成されたときのものではない。）
    @Query("""
            SELECT srr FROM SchoolRecordResult srr 
            JOIN srr.schoolRecord sr 
            JOIN sr.schoolRecordSet srs 
            JOIN srr.student s
            JOIN KlassStudent ks
            ON ks.student = s
            WHERE srs.schoolRecordSetId = :schoolRecordSetId
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            AND ks.klass.klassId = :klassId
            """)
    Page<SchoolRecordResult> findAllBySchoolRecordSetIdAndKlassId(@Param("schoolRecordSetId")Integer schoolRecordSetId,
                                                                  @Param("klassId")Integer klassId,
                                                                  @Param("date")LocalDate date, Pageable pageable);

    @Query("""
            SELECT srr FROM SchoolRecordResult srr
            WHERE srr.student.id = :studentId
            """)
    List<SchoolRecordResult> findAllByStudentId(Integer studentId);

    @Query("""
            SELECT srr FROM SchoolRecordResult srr 
            JOIN srr.schoolRecord sr 
            JOIN sr.schoolRecordSet srs 
            JOIN srr.student s
            JOIN KlassStudent ks
            ON ks.student = s
            WHERE srs.schoolRecordSetId = :schoolRecordSetId
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            AND ks.klass.klassId = :klassId
            """)
    List<SchoolRecordResult> findAllResultBySchoolRecordSetIdAndKlassId(@Param("schoolRecordSetId")Integer schoolRecordSetId,
                                                                        @Param("klassId")Integer klassId,
                                                                        @Param("date")LocalDate date);

    @Query("""
        SELECT srr FROM SchoolRecordResult srr WHERE srr.schoolRecord.schoolRecordId = :schoolRecordId
        ORDER BY
        CASE WHEN :sort = 'japanese' AND :direction = 1 THEN srr.japanese END ASC,
        CASE WHEN :sort = 'japanese' AND :direction = 0 THEN srr.japanese END DESC,
        CASE WHEN :sort = 'math' AND :direction = 1 THEN srr.math END ASC,
        CASE WHEN :sort = 'math' AND :direction = 0 THEN srr.math END DESC,
        CASE WHEN :sort = 'english' AND :direction = 1 THEN srr.english END ASC,
        CASE WHEN :sort = 'english' AND :direction = 0 THEN srr.english END DESC,
        CASE WHEN :sort = 'science' AND :direction = 1 THEN srr.science END ASC,
        CASE WHEN :sort = 'science' AND :direction = 0 THEN srr.science END DESC,
        CASE WHEN :sort = 'social' AND :direction = 1 THEN srr.social END ASC,
        CASE WHEN :sort = 'social' AND :direction = 0 THEN srr.social END DESC,
        CASE WHEN :sort = 'music' AND :direction = 1 THEN srr.music END ASC,
        CASE WHEN :sort = 'music' AND :direction = 0 THEN srr.music END DESC,
        CASE WHEN :sort = 'pe' AND :direction = 1 THEN srr.pe END ASC,
        CASE WHEN :sort = 'pe' AND :direction = 0 THEN srr.pe END DESC,
        CASE WHEN :sort = 'art' AND :direction = 1 THEN srr.art END ASC,
        CASE WHEN :sort = 'art' AND :direction = 0 THEN srr.art END DESC,
        CASE WHEN :sort = 'tech' AND :direction = 1 THEN srr.tech END ASC,
        CASE WHEN :sort = 'tech' AND :direction = 0 THEN srr.tech END DESC,
        CASE WHEN :sort = 'total5' AND :direction = 1 THEN srr.total5 END ASC,
        CASE WHEN :sort = 'total5' AND :direction = 0 THEN srr.total5 END DESC,
        CASE WHEN :sort = 'total9' AND :direction = 1 THEN srr.total9 END ASC,
        CASE WHEN :sort = 'total9' AND :direction = 0 THEN srr.total9 END DESC,
        CASE WHEN :sort = 'adjustedSum' AND :direction = 1 THEN srr.adjustedSum END ASC,
        CASE WHEN :sort = 'adjustedSum' AND :direction = 0 THEN srr.adjustedSum END DESC,
        srr.schoolRecordResultId ASC
        """)
    List<SchoolRecordResult> findAllBySchoolRecordIdWithSort(@Param("schoolRecordId")Integer schoolRecordId,
                                                             @Param("sort")String sort,
                                                             @Param("direction")Integer direction);
    @Query("""
        SELECT srr FROM SchoolRecordResult srr WHERE srr.schoolRecord.schoolRecordSet.schoolRecordSetId = :schoolRecordSetId
        ORDER BY
        CASE WHEN :sort = 'japanese' AND :direction = 1 THEN srr.japanese END ASC,
        CASE WHEN :sort = 'japanese' AND :direction = 0 THEN srr.japanese END DESC,
        CASE WHEN :sort = 'math' AND :direction = 1 THEN srr.math END ASC,
        CASE WHEN :sort = 'math' AND :direction = 0 THEN srr.math END DESC,
        CASE WHEN :sort = 'english' AND :direction = 1 THEN srr.english END ASC,
        CASE WHEN :sort = 'english' AND :direction = 0 THEN srr.english END DESC,
        CASE WHEN :sort = 'science' AND :direction = 1 THEN srr.science END ASC,
        CASE WHEN :sort = 'science' AND :direction = 0 THEN srr.science END DESC,
        CASE WHEN :sort = 'social' AND :direction = 1 THEN srr.social END ASC,
        CASE WHEN :sort = 'social' AND :direction = 0 THEN srr.social END DESC,
        CASE WHEN :sort = 'music' AND :direction = 1 THEN srr.music END ASC,
        CASE WHEN :sort = 'music' AND :direction = 0 THEN srr.music END DESC,
        CASE WHEN :sort = 'pe' AND :direction = 1 THEN srr.pe END ASC,
        CASE WHEN :sort = 'pe' AND :direction = 0 THEN srr.pe END DESC,
        CASE WHEN :sort = 'art' AND :direction = 1 THEN srr.art END ASC,
        CASE WHEN :sort = 'art' AND :direction = 0 THEN srr.art END DESC,
        CASE WHEN :sort = 'tech' AND :direction = 1 THEN srr.tech END ASC,
        CASE WHEN :sort = 'tech' AND :direction = 0 THEN srr.tech END DESC,
        CASE WHEN :sort = 'total5' AND :direction = 1 THEN srr.total5 END ASC,
        CASE WHEN :sort = 'total5' AND :direction = 0 THEN srr.total5 END DESC,
        CASE WHEN :sort = 'total9' AND :direction = 1 THEN srr.total9 END ASC,
        CASE WHEN :sort = 'total9' AND :direction = 0 THEN srr.total9 END DESC,
        CASE WHEN :sort = 'adjustedSum' AND :direction = 1 THEN srr.adjustedSum END ASC,
        CASE WHEN :sort = 'adjustedSum' AND :direction = 0 THEN srr.adjustedSum END DESC,
        srr.schoolRecordResultId ASC
        """)
    List<SchoolRecordResult> findAllBySchoolRecordSetIdWithSort(@Param("schoolRecordSetId")Integer schoolRecordSetId,
                                                                @Param("sort")String sort,
                                                                @Param("direction")Integer direction);

    @Query("""
                SELECT srr FROM SchoolRecordResult srr 
                JOIN srr.student student 
                WHERE srr.schoolRecord = :schoolRecord 
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
    List<SchoolRecordResult> findAllBySchoolRecordWithKlassSort(@Param("schoolRecord")SchoolRecord schoolRecord,
                                                                @Param("direction")Integer direction,
                                                                @Param("date")LocalDate date);


    @Query("""
                SELECT srr FROM SchoolRecordResult srr 
                JOIN srr.student student 
                WHERE srr.schoolRecord.schoolRecordSet = :schoolRecordSet
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
    List<SchoolRecordResult> findAllBySchoolRecordSetWithKlassSort(@Param("schoolRecordSet")SchoolRecordSet schoolRecordSet,
                                                                   @Param("direction")Integer direction,
                                                                   @Param("date")LocalDate semesterEndDate);

    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(srr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(srr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(srr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(srr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(srr.social), 1)
                    END,
                    COALESCE(k.name, '未所属'),
                    ''
                )
                FROM SchoolRecordResult srr
                JOIN srr.student student
                LEFT JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                LEFT JOIN ks.klass k ON k.subject = :subject
                WHERE srr.schoolRecord = :schoolRecord
                GROUP BY k.name
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreForKlass> findAverageForEachKlassForEachSubject(@Param("subject")String subject,
                                                                     @Param("schoolRecord")SchoolRecord schoolRecord,
                                                                     @Param("date")LocalDate calcDate);


    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :totalSubject = 'total5' then ROUND(avg(srr.total5), 1)
                         WHEN :totalSubject = 'total9' then ROUND(avg(srr.total9), 1)
                    END,
                    COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属'),
                    ''
                )
                FROM SchoolRecordResult srr
                JOIN srr.student student
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
                WHERE srr.schoolRecord = :schoolRecord
                GROUP BY COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属')
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreForKlass> findAverageForIntegratedKlassForTotalSubject(@Param("totalSubject")String totalSubject,
                                                                            @Param("schoolRecord")SchoolRecord schoolRecord, 
                                                                            @Param("date")LocalDate calcDate);


    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(srr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(srr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(srr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(srr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(srr.social), 1)
                    END,
                    COALESCE(k.name, '未所属'),
                    ''
                )
                FROM SchoolRecordResult srr
                JOIN srr.student student
                LEFT JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                LEFT JOIN ks.klass k ON k.subject = :subject
                WHERE srr.schoolRecord.schoolRecordSet = :schoolRecordSet
                GROUP BY k.name
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreForKlass> findAverageForEachKlassForEachSubjectForSet(@Param("subject")String subject, 
                                                                           @Param("schoolRecordSet")SchoolRecordSet schoolRecordSet, 
                                                                           @Param("date")LocalDate calcDate);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :totalSubject = 'total5' then ROUND(avg(srr.total5), 1)
                         WHEN :totalSubject = 'total9' then ROUND(avg(srr.total9), 1)
                    END,
                    COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属'),
                    ''
                )
                FROM SchoolRecordResult srr
                JOIN srr.student student
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
                WHERE srr.schoolRecord.schoolRecordSet = :schoolRecordSet
                GROUP BY COALESCE((CASE WHEN k.sortOrder = -1 THEN '個別' ELSE k.name END), '未所属')
                ORDER BY CASE WHEN k.name IS NULL THEN 1 ELSE 0 END,
                         k.sortOrder DESC NULLS LAST
            """)
    List<AverageScoreForKlass> findAverageForIntegratedKlassForTotalSubjectForSet(@Param("totalSubject")String totalSubject,
                                                                                  @Param("schoolRecordSet")SchoolRecordSet schoolRecordSet,
                                                                                  @Param("date")LocalDate calcDate);
    @Query("SELECT srr FROM SchoolRecordResult srr " +
            "WHERE srr.schoolRecord.schoolRecordSet.schoolRecordSetId = :schoolRecordSetId " +
            "AND srr.student.name != 'ave'")
    List<SchoolRecordResult> findAllBySchoolRecordSetIdExcludingAverageMan(@Param("schoolRecordSetId")Integer schoolRecordSetId);


    @Query("""
            SELECT srr FROM SchoolRecordResult srr 
            JOIN srr.schoolRecord sr 
            JOIN sr.schoolRecordSet srs 
            JOIN srr.student s
            JOIN KlassStudent ks
            ON ks.student = s
            WHERE srs.schoolRecordSetId = :schoolRecordSetId
            AND s.name != 'ave'
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            AND ks.klass.klassId = :klassId
            """)
    List<SchoolRecordResult> findAllResultBySchoolRecordSetIdAndKlassIdExcludingAverageMan(@Param("schoolRecordSetId")Integer schoolRecordSetId,
                                                                                           @Param("klassId")Integer klassId,
                                                                                           @Param("date")LocalDate date);


    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(srr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(srr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(srr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(srr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(srr.social), 1)
                    END,
                    '全て',
                    ''
                )
                FROM SchoolRecordResult srr
                JOIN srr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                INNER JOIN ks.klass k ON k.subject = :subject
                WHERE srr.schoolRecord.schoolRecordSet = :schoolRecordSet
            """)
    AverageScoreForKlass findAverageForEveryKlassForEachSubjectForSet(@Param("subject")String subject,
                                                                      @Param("schoolRecordSet")SchoolRecordSet schoolRecordSet,
                                                                      @Param("date")LocalDate calcDate);

    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :totalSubject = 'total5' then ROUND(avg(srr.total5), 1)
                         WHEN :totalSubject = 'total9' then ROUND(avg(srr.total9), 1)
                    END,
                    '全て',
                    ''
                )
                FROM SchoolRecordResult srr
                JOIN srr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                WHERE srr.schoolRecord.schoolRecordSet = :schoolRecordSet
            """)
    AverageScoreForKlass findAverageForEveryKlassForEachTotalSubjectForSet(@Param("totalSubject")String totalSubject,
                                                                           @Param("schoolRecordSet")SchoolRecordSet schoolRecordSet,
                                                                           @Param("date")LocalDate calcDate);
    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :subject = 'japanese' then ROUND(avg(srr.japanese), 1)
                         WHEN :subject = 'math' then ROUND(avg(srr.math), 1)
                         WHEN :subject = 'english' then ROUND(avg(srr.english), 1)
                         WHEN :subject = 'science' then ROUND(avg(srr.science), 1)
                         WHEN :subject = 'social' then ROUND(avg(srr.social), 1)
                    END,
                    '全て',
                    ''
                )
                FROM SchoolRecordResult srr
                JOIN srr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                INNER JOIN ks.klass k ON k.subject = :subject
                WHERE srr.schoolRecord = :schoolRecord
            """)
    AverageScoreForKlass findAverageForEveryKlassForEachSubject(@Param("subject")String subject,
                                                                @Param("schoolRecord")SchoolRecord schoolRecord,
                                                                @Param("date")LocalDate calcDate);

    @Query("""
                SELECT NEW com.example.demo.dto.AverageScoreForKlass(
                    CASE WHEN :totalSubject = 'total5' then ROUND(avg(srr.total5), 1)
                         WHEN :totalSubject = 'total9' then ROUND(avg(srr.total9), 1)
                    END,
                    '全て',
                    ''
                )
                FROM SchoolRecordResult srr
                JOIN srr.student student
                INNER JOIN KlassStudent ks ON ks.student = student
                    AND :date BETWEEN ks.createdAt AND ks.changedAt
                WHERE srr.schoolRecord = :schoolRecord
            """)
    AverageScoreForKlass findAverageForEveryClassForEachTotalSubject(@Param("totalSubject")String totalSubject,
                                                                     @Param("schoolRecord")SchoolRecord schoolRecord,
                                                                     @Param("date")LocalDate calcDate);
}
