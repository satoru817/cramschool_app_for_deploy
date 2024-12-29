package com.example.demo.repository;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.MockTestResult;
import com.example.demo.entity.MockTestResultId;
import com.example.demo.entity.SchoolRecordSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SchoolRecordSetRepository extends JpaRepository<SchoolRecordSet,Integer>, PagingAndSortingRepository<SchoolRecordSet, Integer> {
    Page<SchoolRecordSet> findAllByCramSchool(CramSchool cramSchool, Pageable pageable);
}
