package com.example.demo.repository;

import com.example.demo.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ActionRepository extends JpaRepository<Action,Integer> {
    public Optional<Action> findByActionName(String name);

    public Action getByActionName(String actionName);
}
