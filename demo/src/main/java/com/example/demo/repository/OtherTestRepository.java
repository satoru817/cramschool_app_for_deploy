package com.example.demo.repository;

import com.example.demo.entity.OtherTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OtherTestRepository extends JpaRepository<OtherTest,Integer> {

    //テスト名または学校名で部分一致検索
    @Query("SELECT o FROM OtherTest o " +
            "WHERE o.school.name LIKE %:searchQuery% " +
            "OR o.name LIKE %:searchQuery%")
    Page<OtherTest> findBySearchQuery(@Param("searchQuery")String searchQuery, Pageable pageable);

    OtherTest getByOtherTestId(Integer otherTestId);
}
