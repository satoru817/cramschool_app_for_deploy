package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="regular_test_sets")
public class RegularTestSet {
    @Column(name="regular_test_set_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer regularTestSetId;

    @Column(name="term")
    private Integer term;

    @Column(name="grade")
    private Integer grade;

    @Column(name="semester")
    private Integer semester;

    @Column(name="is_mid")
    private Integer isMid;

    @JoinColumn(name="cram_school_id")
    @ManyToOne
    @JsonIgnore
    private CramSchool cramSchool;

    public String getMorE(){//isMidから中間あるいは期末をとる表示用のメソッド
        if(this.isMid==1){
            return "中間";
        }else{
            return "期末";
        }
    }
}
