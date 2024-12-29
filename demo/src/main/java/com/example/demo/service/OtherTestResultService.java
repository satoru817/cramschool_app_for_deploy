package com.example.demo.service;

import com.example.demo.entity.OtherTest;
import com.example.demo.entity.OtherTestResult;
import com.example.demo.repository.OtherTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OtherTestResultService {
    private final OtherTestResultRepository otherTestResultRepository;


    public List<OtherTestResult> getAllByOtherTestWithKlassSort(OtherTest otherTest, Integer direction) {
        LocalDate date = otherTest.getDate();

        return otherTestResultRepository.findAllByOtherTestWithKlassSortAndDate(otherTest,date,direction);
    }
}
