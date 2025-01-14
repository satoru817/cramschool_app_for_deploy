package com.example.demo.repository;

import com.example.demo.entity.ActionHistory;
import com.example.demo.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionHistoryRepository extends JpaRepository<ActionHistory,Integer> {
    Page<ActionHistory> findByInquiry(Inquiry inquiry, Pageable pageable);
}
