package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InquiryPostDTO {
    @NotNull(message = "ファネルを選択してください")
    private int funnelId;

    @NotNull(message = "校舎を選択してください。")
    private int cramSchoolId;

    @NotBlank(message="問合せ者の名前を入力してください。")
    private String name;

    private String gradeStr;//サーバーサイドでel1に変換する


}
