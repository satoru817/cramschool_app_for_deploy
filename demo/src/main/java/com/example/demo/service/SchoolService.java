package com.example.demo.service;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.School;
import com.example.demo.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {
    private SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository){
        this.schoolRepository = schoolRepository;
    }

    public void save(School school){
        schoolRepository.save(school);
    }

    public List<School> fetchAll(){
        return schoolRepository.findAll();
    }

    public School fetchById(Integer id){
        return schoolRepository.getSchoolBySchoolId(id);
    }

    public School findByName(String schoolName) {
        return schoolRepository.getSchoolByName(schoolName);
    }

    public void deleteById(Integer id) {
        schoolRepository.deleteById(id);
    }



    public List<School> findAllJuniorHighForCramSchool(CramSchool cramSchool) {
        Integer cramSchoolId = cramSchool.getCramSchoolId();
        String jh = "中学";
        return schoolRepository.findAllByNameLikeAndCramSchool(jh,cramSchoolId);
    }

    public Optional<School> getUnknownForCramSchool(CramSchool cramSchool) {
        String unknown ="不明";
        return  schoolRepository.findBySchoolNameAndCramSchool(unknown,cramSchool);
    }


}
