package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionHistoryPostDTO {
    private Integer actionHistoryId;
    private Integer inquiryId;
    private Integer actionId;
    private Integer userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate actionDate;
    private String comment;

}
