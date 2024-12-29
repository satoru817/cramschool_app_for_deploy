package com.example.demo.repository;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    User getByName(String username);

    Optional<User> findByName(String username);

    Page<User> findAllByName(String userName, Pageable pageable);

    @Query("""
            SELECT u FROM User u
            INNER JOIN CramSchoolUser csu
            ON csu.user = u
            WHERE csu.cramSchool = :cramSchool
            """)
    List<User> findAllByCramSchool(@Param("cramSchool")CramSchool cramSchool);


}
