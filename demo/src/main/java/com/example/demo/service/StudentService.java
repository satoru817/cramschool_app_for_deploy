package com.example.demo.service;

import com.example.demo.entity.CramSchool;
import com.example.demo.entity.School;
import com.example.demo.entity.Student;
import com.example.demo.repository.SchoolStudentRepository;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StudentService {
    private final TermAndYearService termAndYearService;
    private final StudentRepository studentRepository;
    private final SchoolStudentRepository schoolStudentRepository;



    public void save(Student student){
        studentRepository.save(student);
    }

    public List<Student> fetchAll(){
        return studentRepository.findAll();
    }

    public Student getById(Integer id){return studentRepository.getById(id);}

    public Student findByCode(Long code){
        return studentRepository.getByCode(code);
    }


    public List<Student> getStudentsByEl1AndDateRangeAndSchoolId(Integer el1, LocalDate date, Integer schoolId) {
        return studentRepository.findStudentsByEl1AndDateRangeAndSchoolId(el1, date, schoolId);
    }

    public Page<Student> findCurrentStudents(Pageable pageable){
        Integer currentTerm = termAndYearService.getTerm();
        return studentRepository.getStudentsInEl1Range(currentTerm,currentTerm-12,pageable);
    }

    public Page<Student> findCurrentStudentsByCramSchool(CramSchool cramSchool, Pageable pageable) {
        Integer currentTerm = termAndYearService.getTerm();
        Integer pastTerm = currentTerm -12;
        return  studentRepository.getStudentsInEl1RangeByCramSchool(currentTerm,pastTerm, pageable, cramSchool);
    }


    public Optional<Student> getAverageStudent(CramSchool cramSchool) {
        return studentRepository.getByNameAndCramSchool("ave",cramSchool);
    }

    public List<Student> getStudentForSchoolIdAndGradeAndDate(Integer schoolId,Integer jhGrade,LocalDate date){
        Integer el1 = termAndYearService.getWhenEnteredElementarySchoolForJh(date,jhGrade);
        return schoolStudentRepository.findStudentBySchoolAndDateAndEl1(schoolId,el1,date);
    }

    public Student findById(Integer studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(()->new RuntimeException("該当の生徒は見つかりませんでした"));
    }

    public Page<Student> findAllCurrentStudent(Pageable pageable) {
        Integer currentTerm = termAndYearService.getTerm();
        Integer pastTerm = currentTerm -12;
        return studentRepository.getStudentsInEl1Range(currentTerm,pastTerm,pageable);
    }

    public Page<Student> findAllCurrentStudentsByName(String validName, Pageable pageable) {
        Integer currentTerm = termAndYearService.getTerm();
        Integer pastTerm = currentTerm -12;
        return studentRepository.getStudentsByNameAndEl1Range(validName,pastTerm,currentTerm,pageable);
    }

    public Page<Student> findAllStudentsByEl1(Integer el1, Pageable pageable) {
        return studentRepository.getStudentsByEl1(el1,pageable);
    }

    public Page<Student> findAllStudentsByEl1AndName(String validName, Integer el1, Pageable pageable) {
        return studentRepository.getAllStudentsByEl1AndName(validName,el1,pageable);
    }
}
