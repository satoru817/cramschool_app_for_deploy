package com.example.demo.service;

import com.example.demo.dto.SubjectRankingDto;
import com.example.demo.entity.MockTest;
import com.example.demo.entity.MockTestResult;
import com.example.demo.repository.MockTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MockTestResultService {
    private final MockTestResultRepository mockTestResultRepository;

    public Page<MockTestResult> getAllMockTestResult(Pageable pageable){
        return mockTestResultRepository.findAll(pageable);
    }

    public Page<MockTestResult> getAllMockTestResultByMockTestId(Pageable pageable, Integer mockTestId){
        return mockTestResultRepository.findAllByMockTestId(mockTestId,pageable);
    }
    //todo:nullpoを解消
    public List<SubjectRankingDto> findSubjectRankingDto(Integer mockTestId, Integer maxRank, Boolean isAsc, String subjectField) {
        List<Map<String, Object>> result = mockTestResultRepository.findSubjectRanking(mockTestId, maxRank, isAsc, subjectField);

        return result.stream()
                .map(map -> new SubjectRankingDto(
                        ((Number) map.get("rank")).intValue(),
                        map.get("subject_score")==null? null: parseInteger(map.get("subject_score").toString()),
                        map.get("standard_score")==null? null: parseInteger(map.get("standard_score").toString()),
                        (String) map.get("student_name"),
                        ((Number) map.get("student_id")).intValue()
                ))
                .collect(Collectors.toList());
    }

    private Integer parseInteger(String s){
        if(s==null||s.trim().isEmpty()){
            return null;
        }else{
            return Integer.valueOf(s);
        }
    }

    //実施日のクラスでソートする
    public List<MockTestResult> findAllResultsByMockTestWithIntegratedKlassSort(MockTest mockTest, String sortDirection) {
        LocalDate date = mockTest.getDate();
        return mockTestResultRepository.findAllResultsByMockTestWithIntegratedKlassSortAndDate(mockTest,sortDirection,date);
    }



}
