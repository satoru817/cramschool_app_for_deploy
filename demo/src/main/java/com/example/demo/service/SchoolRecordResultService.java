package com.example.demo.service;

import com.example.demo.dto.SchoolRecordResultData;
import com.example.demo.entity.SchoolRecord;
import com.example.demo.entity.SchoolRecordResult;
import com.example.demo.entity.SchoolRecordSet;
import com.example.demo.repository.SchoolRecordResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SchoolRecordResultService {
    private final TermAndYearService termAndYearService;
    private final SchoolRecordResultRepository schoolRecordResultRepository;
    private final Integer TOTAL_SCORE = 300;
    private final Integer DIVIDER = 65;
    public void save(SchoolRecordResult schoolRecordResult) {
        schoolRecordResultRepository.save(schoolRecordResult);
    }

    public List<SchoolRecordResultData> getData(List<SchoolRecordResult> results){
        List<SchoolRecordResultData> dataList = new ArrayList<>();
        results.stream().forEach(result->{
            dataList.add(convert(result));
        });

        return dataList;
    }

    public SchoolRecordResultData convert(SchoolRecordResult result){
        SchoolRecordResultData data = new SchoolRecordResultData();

        SchoolRecordSet set = result.getSchoolRecord().getSchoolRecordSet();
        String title = getTitle(set);

        Integer total5 = getTotal5(result);
        Integer total9 = getTotal9(result);
        Integer projectedSchoolScore = getProjectedSchoolScore(result);
        Integer weirdSum = getWeirdSum(result);



        data.setTitle(title);
        data.setJapanese(result.getJapanese());
        data.setMath(result.getMath());
        data.setEnglish(result.getEnglish());
        data.setScience(result.getScience());
        data.setSocial(result.getSocial());
        data.setMusic(result.getMusic());
        data.setArt(result.getArt());
        data.setTech(result.getTech());
        data.setPe(result.getPe());
        data.setTotal5(total5);
        data.setTotal9(total9);
        data.setWeirdSum(weirdSum);
        data.setProjectedSchoolScore(projectedSchoolScore);

        return data;


    }

    private Integer getWeirdSum(SchoolRecordResult rt) {
        return Optional.ofNullable(rt)
                .map(result -> {
                    Integer total5 = getTotal5(result);
                    Integer total9 = getTotal9(result);
                    if (total5 == null || total9 == null) {
                        return null;
                    }
                    return total9 * 2 - total5;
                })
                .orElse(null);
    }

    private Integer getProjectedSchoolScore(SchoolRecordResult rt) {
        return Optional.ofNullable(getWeirdSum(rt))
                .map(sum -> (int)(sum * TOTAL_SCORE / DIVIDER))
                .orElse(null);
    }



    private String getTitle(SchoolRecordSet set){
        return String.format("%s年度%s学年%s学期",
                set.getTerm(),
                set.getGrade(),
                set.getSemester()
        );
    }

    private Integer getTotal5(SchoolRecordResult rt) {
        List<Integer> values = Stream.of(
                        rt.getJapanese(),
                        rt.getMath(),
                        rt.getEnglish(),
                        rt.getScience(),
                        rt.getSocial()
                ).filter(Objects::nonNull)
                .toList();

        // 全てnullの場合はnullを返す
        if (values.isEmpty()) {
            return null;
        }

        return values.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private Integer getTotal9(SchoolRecordResult rt){
        List<Integer> values = Stream.of(
                rt.getJapanese(),
                rt.getMath(),
                rt.getEnglish(),
                rt.getSocial(),
                rt.getScience(),
                rt.getMusic(),
                rt.getPe(),
                rt.getArt(),
                rt.getTech()
        ).filter(Objects::nonNull)
                .toList();

        if(values.isEmpty()){
            return null;
        }

        return values.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }


    public List<SchoolRecordResult> getAllBySchoolRecordWithKlassSort(SchoolRecord schoolRecord, Integer direction) {

        Integer term = schoolRecord.getSchoolRecordSet().getTerm();
        Integer semester = schoolRecord.getSchoolRecordSet().getSemester();
        return schoolRecordResultRepository.findAllBySchoolRecordWithKlassSort(schoolRecord,direction, termAndYearService.getSemesterEndDate(term,semester));
    }

    public List<SchoolRecordResult> getAllBySchoolRecordSetWithKlassSort(SchoolRecordSet schoolRecordSet, Integer direction) {
        Integer term = schoolRecordSet.getTerm();
        Integer semester = schoolRecordSet.getSemester();
        return schoolRecordResultRepository.findAllBySchoolRecordSetWithKlassSort(schoolRecordSet,direction,termAndYearService.getSemesterEndDate(term,semester));
    }
}
