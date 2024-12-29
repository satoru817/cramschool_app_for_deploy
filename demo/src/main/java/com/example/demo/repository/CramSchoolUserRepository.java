package com.example.demo.repository;


import com.example.demo.entity.CramSchoolUser;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CramSchoolUserRepository extends JpaRepository<CramSchoolUser,Integer> {
    void deleteAllByUser(User user);
}
