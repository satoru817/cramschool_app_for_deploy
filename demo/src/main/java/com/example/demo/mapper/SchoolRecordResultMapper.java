package com.example.demo.mapper;

import com.example.demo.dto.SchoolRecordResultDTO;
import com.example.demo.entity.SchoolRecordResult;

public class SchoolRecordResultMapper {

    public static SchoolRecordResultDTO toDTO(SchoolRecordResult entity) {
        if (entity == null) {
            return null;
        }
        SchoolRecordResultDTO dto = new SchoolRecordResultDTO();
        dto.setSchoolRecordResultId(entity.getSchoolRecordResultId());
        dto.setSchoolRecordId(entity.getSchoolRecord().getSchoolRecordId());
        dto.setStudentName(entity.getStudent().getName()); // 学生名を取得
        dto.setStudentId(entity.getStudent().getId());
        dto.setJapanese(entity.getJapanese());
        dto.setMath(entity.getMath());
        dto.setEnglish(entity.getEnglish());
        dto.setScience(entity.getScience());
        dto.setSocial(entity.getSocial());
        dto.setMusic(entity.getMusic());
        dto.setArt(entity.getArt());
        dto.setTech(entity.getTech());
        dto.setPe(entity.getPe());
        return dto;
    }

//    public static SchoolRecordResult toEntity(SchoolRecordResultDTO dto) {
//        if (dto == null) {
//            return null;
//        }
//        SchoolRecordResult entity = new SchoolRecordResult();
//        entity.setSchoolRecordResultId(dto.getSchoolRecordResultId());
//        // 学生IDを設定する場合、学生エンティティを取得する必要があります
//        entity.setJapanese(dto.getJapanese());
//        entity.setMath(dto.getMath());
//        entity.setEnglish(dto.getEnglish());
//        entity.setScience(dto.getScience());
//        entity.setSocial(dto.getSocial());
//        entity.setMusic(dto.getMusic());
//        entity.setArt(dto.getArt());
//        entity.settech(dto.gettech());
//        entity.setPe(dto.getPe());
//        return entity;
//    }
}
