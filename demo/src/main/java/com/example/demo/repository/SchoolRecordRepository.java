package com.example.demo.repository;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.SchoolRecord;

import com.example.demo.entity.SchoolRecordSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchoolRecordRepository extends JpaRepository<SchoolRecord, Integer>, PagingAndSortingRepository<SchoolRecord,Integer> {
    List<SchoolRecord> findAllBySchoolRecordSet(SchoolRecordSet schoolRecordSet);

    @Query("SELECT sr FROM SchoolRecord sr " +
            "JOIN sr.schoolRecordSet srs " +
            "WHERE srs.cramSchool = :cramSchool")
    Page<SchoolRecord> findAllByCramSchool(@Param("cramSchool") CramSchool cramSchool, Pageable pageable);
}
