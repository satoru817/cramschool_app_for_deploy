package com.example.demo.constant;

import lombok.Getter;

@Getter
public enum TotalSubjectForNine {
    TOTAL5("total5"),
    TOTAL9("total9");

    private final String value;

    TotalSubjectForNine(String value){
        this.value = value;
    }
}
