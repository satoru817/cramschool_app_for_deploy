package com.example.demo.repository;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.MockTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MockTestRepository extends JpaRepository<MockTest,Integer> , PagingAndSortingRepository<MockTest,Integer> {
    Page<MockTest> findAllByCramSchool(CramSchool cramSchool, Pageable pageable);
}
