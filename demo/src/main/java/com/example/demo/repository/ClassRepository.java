package com.example.demo.repository;

import com.example.demo.dto.ClassTeacherAssignment;
import com.example.demo.entity.CramSchool;
import com.example.demo.entity.Klass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ClassRepository extends JpaRepository<Klass,Integer> {
    public List<Klass> findAll();

    public Klass findBySubjectAndName(String subject,String name );

    @Query("SELECT k.name FROM Klass k " +
            "WHERE k.subject = :subject")
    public String[] findBySubject(@Param("subject")String subject);

    @Query("""
            SELECT k FROM Klass k
            WHERE k.cramSchool = :cramSchool
            ORDER BY k.subject DESC,k.sortOrder DESC 
            """)
    List<Klass> getAllKlassByCramSchool(CramSchool cramSchool);

    @Query("SELECT k FROM Klass k " +
            "WHERE k.subject = :subject " +
            "AND k.cramSchool = :cramSchool")
    List<Klass> findBySubjectAndCramSchool(@Param("subject")String subject, @Param("cramSchool")CramSchool cramSchool);


    Klass findByKlassId(Integer newKlassId);

    @Query("SELECT new com.example.demo.dto.ClassTeacherAssignment(" +
            "c.klassId, c.subject, c.name, cu.grade, " +
            "u.userId, u.name, cu.startDate, cu.endDate) " +
            "FROM Klass c " +
            "INNER JOIN KlassUser cu ON c.klassId = cu.klass.klassId " +
            "AND :date BETWEEN cu.startDate AND cu.endDate " +
            "LEFT JOIN User u ON cu.user.userId = u.userId " +
            "WHERE c.cramSchool.cramSchoolId = :cramSchoolId " +
            "ORDER BY c.sortOrder, c.subject, cu.grade")
    List<ClassTeacherAssignment> findCurrentAssignments(@Param("cramSchoolId") Integer cramSchoolId,
                                                        @Param("date")LocalDate date);
}
