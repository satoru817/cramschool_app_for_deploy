package com.example.demo.service;

import com.example.demo.repository.CramSchoolUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CramSchoolUserService {
    private final CramSchoolUserRepository cramSchoolUserRepository;
}
