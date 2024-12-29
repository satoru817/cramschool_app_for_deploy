package com.example.demo.service;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.RegularTestResult;
import com.example.demo.entity.RegularTestSet;
import com.example.demo.entity.Student;
import com.example.demo.repository.RegularTestResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegularTestResultService {
    private final RegularTestResultRepository regularTestResultRepository;
    private final TermAndYearService termAndYearService;

    public Optional<RegularTestResult> getRegularTestResultByRegularTestAndStudent(RegularTest regularTest,Student student){
        return regularTestResultRepository.findByRegularTestAndStudent(regularTest, student);
    }

    public Optional<RegularTestResult> getRegularTestResultById(Integer regularTestResultId){
        return regularTestResultRepository.findById(regularTestResultId);
    }


    public void save(RegularTestResult regularTestResult) {
        regularTestResultRepository.save(regularTestResult);
    }

    public List<RegularTestResult> findAllByRegularTestSetWithKlassSort(RegularTestSet regularTestSet, Integer direction) {
        Integer term = regularTestSet.getTerm();
        Integer semester = regularTestSet.getSemester();
        Integer isMid  = regularTestSet.getIsMid();
        LocalDate date = termAndYearService.getSemesterDate(term,semester,isMid);

        return regularTestResultRepository.findAllByRegularTestSetWithKlassSortAndDate(regularTestSet,date,direction);
    }

    //regularTestにはdateがあるのでそれを所属クラス判定の基準にする
    public List<RegularTestResult> findAllByRegularTestWithKlassSort(RegularTest regularTest, Integer direction) {
        LocalDate date = regularTest.getDate();

        return regularTestResultRepository.findAllByRegularTestWithKlassSortAndDate(regularTest,date,direction);
    }
}
