package com.example.demo.service;

import com.example.demo.entity.SchoolStudent;
import com.example.demo.repository.SchoolStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SchoolStudentService {
    private SchoolStudentRepository schoolStudentRepository;

    public SchoolStudentService(SchoolStudentRepository schoolStudentRepository){
        this.schoolStudentRepository = schoolStudentRepository;
    }

    public void save(SchoolStudent schoolStudent){
        schoolStudentRepository.save(schoolStudent);
    }

    public List<SchoolStudent> getAllSchoolStudentsOrderedByCreatedAt(){
        return schoolStudentRepository.findAllByOrderByCreatedAtAsc();
    }

    public List<SchoolStudent> getSchoolStudentsByStudentIdOrdered(Integer studentId){
        return schoolStudentRepository.findByStudentIdOrderByCreatedAtAsc(studentId);
    }

    public SchoolStudent getLatestSchoolStudentById(Integer studentId){
        List<SchoolStudent> schoolStudents = getSchoolStudentsByStudentIdOrdered(studentId);
        return schoolStudents.stream().reduce((first,second)->second).orElseThrow(()->new NoSuchElementException("生徒ID:"+studentId+"の生徒の所蔵する学校は何らかのエラーで設定されていません"));

    }


}
