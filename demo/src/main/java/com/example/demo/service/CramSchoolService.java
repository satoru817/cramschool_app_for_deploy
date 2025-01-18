package com.example.demo.service;

import com.example.demo.entity.CramSchool;
import com.example.demo.repository.CramSchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CramSchoolService {
    private final CramSchoolRepository cramSchoolRepository;
    public List<CramSchool> getAllCramSchools(){
        return cramSchoolRepository.findAll();
    }
}
