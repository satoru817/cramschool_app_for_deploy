package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InquiryFirstPostDTO {
    private Integer inquiryId;

    @NotNull(message = "ファネルを選択してください")
    private Integer funnelId;

    @NotNull(message = "校舎を選択してください。")
    private Integer cramSchoolId;

    @NotBlank(message="問合せ者の名前を入力してください。")
    private String name;

    private String kana;//これは必須ではない。

    private String gradeStr;//サーバーサイドでel1に変換する

    @DateTimeFormat(pattern = "yyyy-MM-dd")//date picker利用の為にこうしている
    private LocalDate inquiryDate;


    @NotNull(message="担当者を選択して下さい。")
    private Integer userId;

    private String comment;
}
