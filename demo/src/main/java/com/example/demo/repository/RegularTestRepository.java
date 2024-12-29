package com.example.demo.repository;

import com.example.demo.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegularTestRepository extends JpaRepository<RegularTest,Integer> {
    public List<RegularTest> findAll();
    public RegularTest getById(Integer id);

//    @Query("SELECT rt FROM RegularTest rt " +
//            "JOIN rt.regularTestSet rts " +
//            "WHERE rt.school = :school " +
//            "AND rts.grade = :grade " +
//            "AND rts.semester = :semester " +
//            "AND rts.isMid = :isMid" )
//    public List<RegularTest> getBySchoolAndGradeAndSemesterAndIsMidOrderByDateDesc(@Param("school") School school,@Param("grade")Integer grade,@Param("semester")Integer semester,@Param("isMid")Integer isMid);

    Optional<RegularTest> getBySchoolAndRegularTestSet(School school, RegularTestSet regularTestSet);

    @Query("SELECT rt FROM RegularTest rt " +
            "JOIN rt.regularTestSet rts " +
            "WHERE rts.cramSchool = :cramSchool")
    Page<RegularTest> findAllByCramSchool(@Param("cramSchool") CramSchool cramSchool, Pageable pageable);
}
