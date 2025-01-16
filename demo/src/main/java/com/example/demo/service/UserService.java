package com.example.demo.service;

import com.example.demo.dto.AverageScoreAndStandardScoreForKlass;
import com.example.demo.dto.AverageScoreForKlass;
import com.example.demo.dto.UserRegisterDto;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CramSchoolUserRepository cramSchoolUserRepository;
    private final CramSchoolRepository cramSchoolRepository;
    private final KlassUserRepository klassUserRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User getById(Integer userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("該当のuserは存在しません。"));
    }

    public User createUserFromUserRegisterDto(UserRegisterDto userRegisterDto, CramSchool cramSchool){
        User user = new User();
        user.setRole(roleRepository.getReferenceById(userRegisterDto.getRoleId()));
        user.setName(userRegisterDto.getName());
        user.setCramSchool(cramSchool);
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));
        return user;
    }


    public void tieUserToCramSchools(User user, List<Integer> selectedCramSchoolIds) {
        for(Integer cramSchoolId:selectedCramSchoolIds){
            CramSchool cramSchool = cramSchoolRepository.getReferenceById(cramSchoolId);
            tieUserToCramSchool(user,cramSchool);
        }
    }

    public void tieUserToCramSchool(User user,CramSchool cramSchool){
        CramSchoolUser cramSchoolUser = CramSchoolUser.builder()
                .cramSchool(cramSchool)
                .user(user)
                .build();

        cramSchoolUserRepository.save(cramSchoolUser);
    }

    public void untieUserFromCramSchool(User user, CramSchool cramSchool){

    }


    public void setCramSchoolIds(List<User> users) {
        for(User user: users){
            setCramSchoolId(user);
        }
    }

    public void setCramSchoolId(User user) {
        List<Integer> cramSchoolIds = user.getCramSchoolUsers().stream()
                .map(cramSchoolUser -> cramSchoolUser.getCramSchool().getCramSchoolId())
                .collect(Collectors.toList());
        user.setCramSchoolIds(cramSchoolIds);
    }

    @Transactional
    public void updateUser(Integer userId, String name, String password, Integer roleId, List<Integer> cramSchoolIds) {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("指定されたユーザーは見つかりません"));

        user.setName(name);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // パスワードが入力されている場合のみ更新
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        // 権限の更新
        Role role = roleRepository.findById(roleId)
                .orElseThrow(()->new RuntimeException("指定されたロールは見つかりません"));
        user.setRole(role);

        // 所属教室の更新
        updateUserCramSchools(user, cramSchoolIds);
    }

    public void updateUserCramSchools(User user, List<Integer> cramSchoolIds) {
        cramSchoolUserRepository.deleteAllByUser(user);
        cramSchoolUserRepository.flush();

        if (Objects.isNull(cramSchoolIds) || cramSchoolIds.isEmpty()) {
            return;  // 早期リターンで処理を終了
        }

        List<CramSchoolUser> cramSchoolUsers = cramSchoolIds.stream()
                .map(cramSchoolId -> CramSchoolUser.builder()
                        .cramSchool(cramSchoolRepository.getReferenceById(cramSchoolId))
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        cramSchoolUserRepository.saveAll(cramSchoolUsers);
    }

    //クラス名、科目を取得して、そのクラスのある学年をある時点testDateで担当している教師の名前をaverageに格納する
    public <T extends AverageScoreForKlass> void setTeacherName(CramSchool cramSchool, T average, String subject, Integer grade, LocalDate testDate) {
        String klassName = average.getKlassName();
        Optional<KlassUser> ku = klassUserRepository.findByNameAndSubjectAndGradeAndTestDate(cramSchool,klassName,subject,grade,testDate);
        String teacherName = (ku.isPresent())? ku.get().getUser().getName() : "不明";
        average.setTeacherName(teacherName);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByName(String username) {
        return userRepository.getByName(username);
    }
}
