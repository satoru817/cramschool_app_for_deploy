package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final static String ROLE_ADMIN = "ROLE_ADMIN";
    private final static String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    private final static String ROLE_GENERAL = "ROLE_GENERAL";
    private final RoleRepository roleRepository;

    public Role getByName(String roleName){
        return roleRepository.findByName(roleName);
    }

    public Role getGeneral(){
        return getByName(ROLE_GENERAL);
    }

    public Role getAdmin(){
        return getByName(ROLE_ADMIN);
    }

    public Role getSuperAdmin(){
        return getByName(ROLE_SUPER_ADMIN);
    }
}
