package com.example.demo.service;

import com.example.demo.dto.SchoolRecordDTO;
import com.example.demo.entity.SchoolRecord;
import com.example.demo.repository.SchoolRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolRecordService {
    private final SchoolRecordRepository schoolRecordRepository;

    public void save(SchoolRecord schoolRecord){
        schoolRecordRepository.save(schoolRecord);
    }

    public static SchoolRecordDTO convertToDTO(SchoolRecord schoolRecord) {
        SchoolRecordDTO schoolRecordDTO = new SchoolRecordDTO();
        schoolRecordDTO.setSchoolRecordId(schoolRecord.getSchoolRecordId());
        schoolRecordDTO.setSchoolName(schoolRecord.getSchool().getName());
        schoolRecordDTO.setTerm(schoolRecord.getSchoolRecordSet().getTerm());
        schoolRecordDTO.setGrade(schoolRecord.getSchoolRecordSet().getGrade());
        schoolRecordDTO.setSemester(schoolRecord.getSchoolRecordSet().getSemester());
        return schoolRecordDTO;
    }

    public SchoolRecord findById(Integer schoolRecordId) {
        return schoolRecordRepository.findById(schoolRecordId)
                .orElseThrow(()->new RuntimeException("該当の内申が見つかりません。"));
    }
}
