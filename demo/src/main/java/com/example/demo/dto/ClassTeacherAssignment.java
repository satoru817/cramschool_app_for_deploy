package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassTeacherAssignment {
    private Integer classId;
    private String subject;
    private String className;
    private Integer grade;
    private Integer teacherId;
    private String teacherName;
    private LocalDate startDate;
    private LocalDate endDate;

}