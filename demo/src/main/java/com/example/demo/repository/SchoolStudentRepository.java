package com.example.demo.repository;

import com.example.demo.entity.KlassStudent;
import com.example.demo.entity.School;
import com.example.demo.entity.SchoolStudent;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SchoolStudentRepository extends JpaRepository<SchoolStudent,Integer> {
   List<SchoolStudent> findAllByOrderByCreatedAtAsc();

   List<SchoolStudent> findByStudentIdOrderByCreatedAtAsc(Integer studentId);



   //ある学校にある時点(date)で所属していた生徒のidのうち、小学一年生の年度がある値(el1）である物を全て選ぶメソッド
   @Query(value = "SELECT  s.student_id " +
           "FROM school_students k " +
           "JOIN students s ON k.student_id = s.student_id " +
           "JOIN schools sl ON k.school_id = sl.school_id " +
           "WHERE s.el1 = :el1 " +
           "AND sl.school_id = :schoolId " +
           "AND :date BETWEEN k.created_at AND k.changed_at",
           nativeQuery = true)
   List<Integer> findSchoolStudentBySchoolAndDateAndEl1(@Param("schoolId") Integer schoolId,
                                                        @Param("el1") Integer el1,
                                                        @Param("date") LocalDate date);

   @Query("SELECT s FROM Student s " +
           "JOIN s.schoolStudents ss " +
           "WHERE ss.school.schoolId = :schoolId " +
           "AND s.el1 = :el1 " +
           "AND :date BETWEEN ss.createdAt AND ss.changedAt")
   List<Student> findStudentBySchoolAndDateAndEl1(@Param("schoolId") Integer schoolId, @Param("el1") Integer el1, @Param("date") LocalDate date);
}

