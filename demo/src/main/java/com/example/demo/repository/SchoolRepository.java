package com.example.demo.repository;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School,Integer> {
    public School getSchoolBySchoolId(Integer id);

    public School getSchoolByName(String name);

    @Query("SELECT s FROM School s WHERE s.name LIKE %:keyword%")
    List<School> findBySchoolNameContaining(@Param("keyword") String keyword);

    List<School> getAllSchoolByCramSchool(CramSchool cramSchool);



    @Query("SELECT s FROM School s join s.cramSchool c " +
            "WHERE s.name LIKE %:name% " +
            "AND c.cramSchoolId = :cramSchoolId")
    List<School> findAllByNameLikeAndCramSchool(@Param("name")String name,@Param("cramSchoolId")Integer cramSchoolId);

    List<School> findAllByCramSchool(CramSchool cramSchool);

    @Query("SELECT s FROM School s " +
            "WHERE s.name = :schoolName " +
            "AND s.cramSchool = :cramSchool")
    Optional<School> findBySchoolNameAndCramSchool(@Param("schoolName")String schoolName, @Param("cramSchool")CramSchool cramSchool);

    //schoolNameだけでなくCramSchoolを入れているのは、同じ学校から複数の塾校舎に通っている場合、その塾校舎ごとにSchoolを仮想的に
    //作成しているから。
    School findByNameAndCramSchool(String schoolName, CramSchool cramSchool);
}
