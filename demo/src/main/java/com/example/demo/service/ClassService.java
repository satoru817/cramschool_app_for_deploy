package com.example.demo.service;

import com.example.demo.constant.Subject;
import com.example.demo.dto.ClassTeacherAssignment;
import com.example.demo.entity.CramSchool;
import com.example.demo.entity.Klass;
import com.example.demo.entity.User;
import com.example.demo.repository.ClassRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClassService {
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    public List<Klass> getAll(){
        return classRepository.findAll();
    }

    @Transactional
    public void deleteById(Integer id){
        classRepository.deleteById(id);
    }

    public void save(Klass klass){
        classRepository.save(klass);
    }


    public void saveIndividualKlass(Klass klass, CramSchool cramSchool) {
        klass.setSortOrder(-1);
        klass.setIsIndividual(true);
        klass.setCramSchool(cramSchool);
        try{
            classRepository.save(klass);
        } catch (RuntimeException e) {
            throw new RuntimeException("クラスの登録処理に失敗しました");
        }
    }

    public void saveGroupKlass(Klass klass, CramSchool cramSchool) {
        List<Klass> klasses = Arrays.stream(Subject.values())
                .map(subject -> {
                    Klass newKlass = new Klass();
                    newKlass.setName(klass.getName());
                    newKlass.setIsIndividual(false);
                    newKlass.setSortOrder(klass.getSortOrder());
                    newKlass.setCramSchool(cramSchool);
                    newKlass.setSubject(subject.getValue());
                    return newKlass;
                })
                .collect(Collectors.toList());

        // バルクインサート
        classRepository.saveAll(klasses);
    }

    // クエリ結果をMap<String, Map<Integer, User>>に変換
    public Map<String, Map<Integer, User>> convertToUserMap(List<ClassTeacherAssignment> assignments) {
        Map<String, Map<Integer, User>> result = new HashMap<>();

        for (ClassTeacherAssignment assignment : assignments) {
            String classId = assignment.getClassId().toString();

            // クラスIDに対応するMapがまだない場合は新しいMapを作成
            if (!result.containsKey(classId)) {
                result.put(classId, new HashMap<>());
            }

            // 学年と教師の情報を追加
            Map<Integer, User> gradeTeacherMap = result.get(classId);
            gradeTeacherMap.put(assignment.getGrade(),
                    userRepository.getReferenceById(assignment.getTeacherId()));
        }

        return result;
    }

    public Map<String, Map<Integer, User>> getCurrentAssignments(Integer cramSchoolId) {

        List<ClassTeacherAssignment> assignments = classRepository.findCurrentAssignments(cramSchoolId, LocalDate.now());
        return  convertToUserMap(assignments);
    }
}
