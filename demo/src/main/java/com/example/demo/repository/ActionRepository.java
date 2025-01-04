package com.example.demo.repository;

import com.example.demo.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action,Integer> {
}
