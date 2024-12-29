package com.example.demo.dto;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@RequiredArgsConstructor
public class UserRegisterDto {


    private String name;

    private String password;

    private Integer roleId;




}
