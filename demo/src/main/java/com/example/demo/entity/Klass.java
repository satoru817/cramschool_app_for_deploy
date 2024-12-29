package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
//クラスは全学年をカバーするように作る。KlassUserで学年ごとに特定クラスと教師の結びつきを作る。
@Data
@Entity
@Table(name="classes")
public class Klass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="class_id")
    private Integer klassId;

    @Column(name="subject")
    private String subject;//japanese,english...

    @NotBlank(message="クラス名を入力してください")
    @Column(name="name")
    private String name;

    @Column(name="is_individual")
    private Boolean isIndividual;//個別の時はtrue,集団はfalse

    @Column(name="sort_order")
    private Integer sortOrder;//N/Aはorderを0にする。

    @JoinColumn(name="cram_school_id")
    @ManyToOne
    private CramSchool cramSchool;

    public String getSubjectNameInJapanese() {
        return switch(subject) {
            case "japanese" -> "国語";
            case "math" -> "数学";
            case "english" -> "英語";
            case "science" -> "理科";
            case "social" -> "社会";
            default -> "不明なクラス";
        };
    }
}
