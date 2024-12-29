package com.example.demo.service;

import com.example.demo.entity.SchoolRecordSet;
import com.example.demo.repository.SchoolRecordSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolRecordSetService {
    private final SchoolRecordSetRepository schoolRecordSetRepository;

    public SchoolRecordSet findById(Integer schoolRecordSetId) {
        return schoolRecordSetRepository.findById(schoolRecordSetId)
                .orElseThrow(()->new RuntimeException("該当の模試セットが見つかりません"));
    }


}
