package com.example.demo.repository;

import com.example.demo.entity.ActionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionHistoryRepository extends JpaRepository<ActionHistory,Integer> {
}
