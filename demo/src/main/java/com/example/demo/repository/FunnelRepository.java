package com.example.demo.repository;

import com.example.demo.entity.ActionHistory;
import com.example.demo.entity.Funnel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunnelRepository extends JpaRepository<Funnel,Integer> {
}
