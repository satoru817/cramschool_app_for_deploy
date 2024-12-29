package com.example.demo.service;

import com.example.demo.entity.Klass;
import com.example.demo.entity.KlassUser;
import com.example.demo.repository.ClassRepository;
import com.example.demo.repository.KlassUserRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class KlassUserService {
    private final KlassUserRepository klassUserRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    private final static LocalDate START_DATE = LocalDate.parse("1000-01-01");
    private final static LocalDate END_DATE = LocalDate.parse("9999-12-31");

//    public void terminateCurrentAssignment(Integer klassId, Integer grade) {
//        Optional<KlassUser> optionalKlassUser = klassUserRepository.findRecentKlassUserByKlassIdAndGrade(klassId, grade,LocalDate.now());
//
//        if (optionalKlassUser.isPresent()) {
//            KlassUser klassUser = optionalKlassUser.get();
//            klassUser.setEndDate(LocalDate.now());
//            klassUserRepository.save(klassUser);
//        }
//
//    }

    public void terminateCurrentAssignmentAndMakeNewAssignmentBasedOnCondition(Integer klassId, Integer grade, Integer selectedTeacherId) {
        Optional<KlassUser> optionalKlassUser = klassUserRepository.findRecentKlassUserByKlassIdAndGrade(klassId, grade,LocalDate.now());

        if(optionalKlassUser.isPresent()){//今まで担当教師がいた場合
            KlassUser klassUser = optionalKlassUser.get();
            if(klassUser.getUser().getUserId() == selectedTeacherId){
                return;//クラスを担当する講師が変わらない場合。
            }else{
                terminateAssignment(klassUser);
                createNewAssignment(klassId,grade,selectedTeacherId,true);
            }
        }else{
            createNewAssignment(klassId,grade,selectedTeacherId,false);
        }

    }

    private void terminateAssignment(KlassUser klassUser){
        klassUser.setEndDate(LocalDate.now().minusDays(1));
        klassUserRepository.save(klassUser);
    }

    private void createNewAssignment(Integer klassId,Integer grade,Integer selectedTeacherId,Boolean isChange){
        // 新しい担当教師を登録
        KlassUser classUser = new KlassUser();
        classUser.setKlass(classRepository.findById(klassId)
                .orElseThrow(() -> new RuntimeException("クラスが見つかりません")));

        classUser.setUser(userRepository.findById(selectedTeacherId)
                .orElseThrow(() -> new RuntimeException("教師が見つかりません")));
        classUser.setGrade(grade);

        if(isChange){
            classUser.setStartDate(LocalDate.now());
        }else{
            classUser.setStartDate(START_DATE);
        }

        classUser.setEndDate(END_DATE);

        klassUserRepository.save(classUser);
    }

    @Transactional
    public boolean updateDates(Integer grade, KlassUser klassUser, LocalDate startDate, LocalDate endDate) {
        Klass klass = klassUser.getKlass();

        Integer wrapCount = klassUserRepository.countWrapByKlassAndGradeAndStartDateAndEndDate(klassUser,klass,grade,startDate,endDate);

        if(wrapCount.equals(0)){
            klassUser.setStartDate(startDate);
            klassUser.setEndDate(endDate);
            klassUserRepository.save(klassUser);
            klassUserRepository.flush();
            return true;
        }else{
            return false;
        }
    }
}