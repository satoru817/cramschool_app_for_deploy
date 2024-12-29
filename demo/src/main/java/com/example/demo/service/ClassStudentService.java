package com.example.demo.service;

import com.example.demo.entity.Klass;
import com.example.demo.entity.KlassStudent;
import com.example.demo.entity.Student;
import com.example.demo.repository.ClassStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassStudentService {
    private final ClassStudentRepository classStudentRepository;


    //所属してきたクラスを全て得て、その中の最新を返す。何も無い時はnullを返すようになっている。
    private Klass findKlassBySubjectAndStudentAndDate(String subject, Student student, LocalDate date) {
        List<KlassStudent> classStudents;

        switch (subject.toLowerCase()) {
            case "japanese":
                classStudents = classStudentRepository.findKlassForJapaneseByStudentAndDate(student, date);
                break;
            case "math":
                classStudents = classStudentRepository.findKlassForMathByStudentAndDate(student, date);
                break;
            case "english":
                classStudents = classStudentRepository.findKlassForEnglishByStudentAndDate(student, date);
                break;
            case "science":
                classStudents = classStudentRepository.findKlassForScienceByStudentAndDate(student, date);
                break;
            case "social":
                classStudents = classStudentRepository.findKlassForSocialByStudentAndDate(student, date);
                break;
            default:
                return null; // サポートされていない科目の場合
        }

        return (classStudents != null && !classStudents.isEmpty()) ? classStudents.get(0).getKlass() : null;
    }

    public Klass findKlassForJapaneseByStudentAndDate(Student student, LocalDate date) {
        return findKlassBySubjectAndStudentAndDate("japanese", student, date);
    }

    public Klass findKlassForMathByStudentAndDate(Student student, LocalDate date) {
        return findKlassBySubjectAndStudentAndDate("math", student, date);
    }

    public Klass findKlassForEnglishByStudentAndDate(Student student, LocalDate date) {
        return findKlassBySubjectAndStudentAndDate("english", student, date);
    }

    public Klass findKlassForScienceByStudentAndDate(Student student, LocalDate date) {
        return findKlassBySubjectAndStudentAndDate("science", student, date);
    }

    public Klass findKlassForSocialByStudentAndDate(Student student, LocalDate date) {
        return findKlassBySubjectAndStudentAndDate("social", student, date);
    }



    //最もsortOrderの大きなクラスを取ってくる。全科目について同一のsortOrderに関しては同じクラス名がシステム上入るようになっている
    //何もないときはnullを返す
    public String findIntegratedKlassName(Student student, LocalDate date) {
        return classStudentRepository
                .findTopKlassNameByStudentAndDateOrderBySortOrderDesc(student, date)
                .orElse("未登録");
    }

    @Transactional
    public boolean updateDates(Integer klassStudentId, LocalDate createdAt, LocalDate changedAt) {
        KlassStudent klassStudent = classStudentRepository.getReferenceById(klassStudentId);

        Student student = klassStudent.getStudent();
        Klass klass = klassStudent.getKlass();
        String subject = klass.getSubject();

        //更新する前にその情報で更新して大丈夫なのか確認する。具体的には同じ科目のクラスには任意の瞬間に一つのクラスにしか所属できないようにする。（そうじゃないと集計時にエラーがでる。）

       Integer wrapCount = classStudentRepository.countWrapBySubjectAndStudentAndCreatedAndChangedAt(klassStudentId,subject,student,createdAt,changedAt);

       if(wrapCount.equals(0)){
           klassStudent.setCreatedAt(createdAt);
           klassStudent.setChangedAt(changedAt);
           classStudentRepository.save(klassStudent);
           classStudentRepository.flush();
           return true;
       }else{
           return false;//期間が重複するものが存在する場合はfalseを返して何もしない。
       }

    }
}


