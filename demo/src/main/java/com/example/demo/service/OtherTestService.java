package com.example.demo.service;

import com.example.demo.entity.OtherTest;
import com.example.demo.repository.OtherTestRepository;
import com.example.demo.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OtherTestService {
    private final OtherTestRepository otherTestRepository;
    private final SchoolRepository schoolRepository;


    public OtherTest findById(Integer otherTestId){
        return otherTestRepository.findById(otherTestId)
                .orElseThrow(()->new RuntimeException("指定されたその他のテストは見つかりませんでした"));
    }


    @Transactional
    public void save(OtherTest otherTest) {

        otherTest.setSchool(schoolRepository.getReferenceById(otherTest.getSchoolId()));

        otherTestRepository.save(otherTest);
    }
}
