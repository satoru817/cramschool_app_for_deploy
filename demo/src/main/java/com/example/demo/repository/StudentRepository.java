package com.example.demo.repository;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("select s from Student s " +
            "where s.id != 1")
    public List<Student> getAllWithoutAverageMan();

    Student getByCode(Long code);


    @Override
    public Student getById(Integer id);


    @Query("SELECT s FROM Student s " +
            "JOIN s.schoolStudents k " +
            "WHERE s.el1 = :el1 " +
            "AND k.school.schoolId = :schoolId " +
            "AND :specificDate BETWEEN k.createdAt AND k.changedAt")
    List<Student> findStudentsByEl1AndDateRangeAndSchoolId(
            @Param("el1") Integer el1,
            @Param("specificDate") LocalDate specificDate,
            @Param("schoolId") Integer schoolId);


//    @Query("SELECT s FROM Student s " +
//            "WHERE REPLACE(s.name, ' ', '') = :name " +
//            "AND s.el1 = :el1")
//    Optional<Student> findByEl1AndNameWithoutSpace(
//            @Param("el1") Integer el1,
//            @Param("name")String name
//    );

    @Query("SELECT s FROM Student s WHERE s.el1 = :el1 AND MOD(s.code, 10000) = :code")
    Optional<Student> findByEl1AndCode(
            @Param("el1") Integer el1,
            @Param("code") Integer code
    );


    List<Student> findAllByEl1(Integer el1);

    Page<Student> findAllByEl1AndName(Integer el1, String name, Pageable pageable);

    Page<Student> findAllByEl1(Integer el1, Pageable pageable);

    Page<Student> findByName(String name, Pageable pageable);

    @Query("SELECT s FROM Student s " +
            "WHERE s.el1 <= :currentTerm " +
            "AND s.el1 > :pastTerm")
    Page<Student> getStudentsInEl1Range(
            @Param("currentTerm") Integer currentTerm,
            @Param("pastTerm") Integer pastTerm,
            Pageable pageable);

    Page<Student> findByEl1AndNameContainingAndCramSchool(Integer el1, String name, CramSchool cramSchool, Pageable pageable);


    Page<Student> findAllByEl1AndCramSchool(Integer el1, CramSchool cramSchool, Pageable pageable);


    Page<Student> findByNameContainingAndCramSchool(String name, CramSchool cramSchool, Pageable pageable);

    @Query("SELECT s FROM Student s " +
            "WHERE s.el1 <= :currentTerm " +
            "AND s.el1 > :pastTerm " +
            "AND s.cramSchool = :cramSchool " +
            "ORDER BY s.code DESC")
    Page<Student> getStudentsInEl1RangeByCramSchool(@Param("currentTerm")Integer currentTerm, @Param("pastTerm")Integer pastTerm,
                                                    Pageable pageable, @Param("cramSchool")CramSchool cramSchool);

    @Query("SELECT s FROM Student s " +
            "WHERE ( s.name = :name OR s.furigana = :name)" +
            "AND s.cramSchool = :cramSchool " +
            "ORDER BY s.code DESC")
    Optional<Student> getByNameAndCramSchool(@Param("name")String name,@Param("cramSchool") CramSchool cramSchool);

    @Query("SELECT s FROM Student s " +
            "WHERE s.name = 'ave' " +
            "AND s.cramSchool = :cramSchool")
    Student getAverageManForCramSchool(@Param("cramSchool")CramSchool cramSchool);

    @Query("SELECT s FROM KlassStudent ks " +
            "JOIN ks.student s " +
            "WHERE s.el1 = :el1 " +
            "AND s.name LIKE %:name% " +
            "AND s.cramSchool = :cramSchool " +
            "AND :date BETWEEN ks.createdAt AND ks.changedAt " +
            "AND ks.klass.klassId = :klassId " +
            "ORDER BY s.code DESC")
    Page<Student> findByEl1AndNameContainingAndKlassIdAndCramSchool(@Param("el1")Integer el1,
                                                                    @Param("name")String name,
                                                                    @Param("klassId")Integer klassId,
                                                                    @Param("cramSchool")CramSchool cramSchool,
                                                                    @Param("date")LocalDate date,
                                                                    Pageable pageable);

    @Query("""
            SELECT s FROM KlassStudent ks 
            JOIN ks.student s
            WHERE s.el1 = :el1
            AND s.cramSchool = :cramSchool
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            AND ks.klass.klassId = :klassId
            """)
    Page<Student> findByEl1AndKlassIdAndCramSchool(@Param("el1")Integer el1,
                                                   @Param("klassId")Integer klassId,
                                                   @Param("cramSchool")CramSchool cramSchool,
                                                   @Param("date")LocalDate date,
                                                   Pageable pageable);

    @Query("""
            SELECT s FROM KlassStudent ks 
            JOIN ks.student s
            WHERE (s.name LIKE %:name% OR s.furigana LIKE %:name%)
            AND s.cramSchool = :cramSchool 
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            AND ks.klass.klassId = :klassId
            """)
    Page<Student> findByNameContainingAndKlassIdAndCramSchool(@Param("name")String name,
                                                              @Param("klassId")Integer klassId,
                                                              @Param("cramSchool")CramSchool cramSchool,
                                                              @Param("date")LocalDate date,
                                                              Pageable pageable);

    @Query("""
            SELECT s FROM KlassStudent ks
            JOIN ks.student s 
            WHERE s.cramSchool = :cramSchool
            AND :date BETWEEN ks.createdAt AND ks.changedAt
            AND ks.klass.klassId = :klassId
            """)
    Page<Student> findByKlassIdAndCramSchool(@Param("klassId")Integer klassId,
                                             @Param("cramSchool")CramSchool cramSchool,
                                             @Param("date")LocalDate date, Pageable pageable);

    Page<Student> findByEl1AndNameContainingAndFuriganaContainingAndCramSchool(Integer el1, String name, String name1, CramSchool cramSchool, Pageable pageable);

    Page<Student> findByNameContainingAndFuriganaContainingAndCramSchool(String name, String name1, CramSchool cramSchool, Pageable pageable);

    Page<Student> findByEl1AndNameContainingOrFuriganaContainingAndCramSchool(Integer el1, String name, String name1, CramSchool cramSchool, Pageable pageable);

    Page<Student> findByNameContainingOrFuriganaContainingAndCramSchool(String name, String name1, CramSchool cramSchool, Pageable pageable);

    @Query("""
            SELECT s FROM Student s
            WHERE s.el1 BETWEEN :pastTerm AND :currentTerm
            AND (s.name LIKE %:validName% OR s.furigana LIKE %:validName%) 
            """)
    Page<Student> getStudentsByNameAndEl1Range(@Param("validName")String validName,
                                               @Param("pastTerm")Integer pastTerm,
                                               @Param("currentTerm")Integer currentTerm, Pageable pageable);

    @Query("""
            SELECT s FROM Student s
            WHERE s.el1 = :el1
            """)
    Page<Student> getStudentsByEl1(@Param("el1")Integer el1, Pageable pageable);

    @Query("""
            SELECT s FROM Student s
            WHERE s.el1 = :el1
            AND (s.name LIKE %:validName% OR s.furigana LIKE %:validName%)
            """)
    Page<Student> getAllStudentsByEl1AndName(@Param("validName")String validName,
                                             @Param("el1")Integer el1, Pageable pageable);
}
