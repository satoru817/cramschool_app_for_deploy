package com.example.demo.service;

import com.example.demo.entity.RegularTest;
import com.example.demo.repository.RegularTestRepository;
import com.example.demo.repository.RegularTestSetRepository;
import com.example.demo.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RegularTestService {
    private final RegularTestRepository regularTestRepository;
    private final RegularTestSetRepository regularTestSetRepository;
    private final SchoolRepository schoolRepository;
    private final TermAndYearService termAndYearService;



    public RegularTest save(RegularTest regularTest){

        regularTest.setRegularTestSet(regularTestSetRepository.getReferenceById(regularTest.getRegularTestSetId()));
        regularTest.setSchool(schoolRepository.getReferenceById(regularTest.getSchoolId()));

        regularTestRepository.save(regularTest);
        return regularTest;
    }

    public List<RegularTest> fetchAll(){
        return regularTestRepository.findAll();
    }

    public RegularTest fetchById(Integer id){
        return regularTestRepository.getById(id);
    }

    //テストの実施日を適当に決めるメソッド(java.sql.Date型)
    public static LocalDate getTestDate(int term, int semester, int isMid) {
        LocalDate testDate = null;

        switch (semester) {
            case 1: // 1学期
                if (isMid == 1) {
                    // 中間テストは6月中旬
                    testDate = LocalDate.of(term, 6, 15); // 6月15日を仮定
                } else {
                    // 期末テストは7月上旬
                    testDate = LocalDate.of(term, 7, 5); // 7月5日を仮定
                }
                break;

            case 2: // 2学期
                if (isMid == 1) {
                    // 中間テストは9月下旬〜10月上旬
                    testDate = LocalDate.of(term, 9, 25); // 9月25日を仮定
                } else {
                    // 期末テストは12月中旬〜下旬
                    testDate = LocalDate.of(term, 12, 20); // 12月20日を仮定
                }
                break;

            case 3: // 3学期
                if (isMid == 1) {
                    // 中間テストは1月中旬〜下旬
                    testDate = LocalDate.of(term + 1, 1, 20); // 1月20日を仮定（次年度）
                } else {
                    // 期末テストは3月中旬
                    testDate = LocalDate.of(term + 1, 3, 15); // 3月15日を仮定（次年度）
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid semester: " + semester);
        }

        // LocalDateをjava.sql.Dateに変換して返す
        return testDate;
    }


    public RegularTest findById(Integer regularTestId) {
        return regularTestRepository.findById(regularTestId)
                .orElseThrow(()->new RuntimeException("該当する定期テストが見つかりません"));
    }
}
