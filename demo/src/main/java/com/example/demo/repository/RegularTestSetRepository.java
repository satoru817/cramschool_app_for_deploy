package com.example.demo.repository;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.RegularTestResult;
import com.example.demo.entity.RegularTestSet;
import com.example.demo.entity.SchoolRecordResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegularTestSetRepository extends JpaRepository<RegularTestSet,Integer> {
    public Optional<RegularTestSet> findByGradeAndTermAndIsMidAndSemester(Integer grade, Integer term, Integer isMid, Integer semester);

    Page<RegularTestSet> findAllByCramSchool(CramSchool cramSchool, Pageable pageable);

    Optional<RegularTestSet> findByGradeAndTermAndIsMidAndSemesterAndCramSchool(Integer grade, Integer thisTerm, Integer isMid, Integer semester, CramSchool cramSchool);

    Optional<RegularTestSet> findByRegularTestSetId(Integer regularTestSetId);

    @Query("SELECT rts FROM RegularTestResult rts " +
            "JOIN FETCH rts.student s " +
            "JOIN FETCH rts.regularTest rt " +
            "WHERE rts.regularTest.regularTestSet.regularTestSetId = :regularTestSetId")
    List<RegularTestResult> findAllRegularTestResultByRegularTestSetId(@Param("regularTestSetId")Integer regularTestSetId);

    @Query("SELECT rtr FROM RegularTestResult rtr " +
            "WHERE rtr.regularTest.regularTestId = :regularTestId")
    List<RegularTestResult> findAllRegularTestResultByRegularTestId(Integer regularTestId);
}
