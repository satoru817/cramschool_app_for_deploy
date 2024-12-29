package com.example.demo.constant;

import lombok.Getter;

@Getter
public enum TotalSubject {
    TOTAL3("total3"),
    TOTAL5("total5");

    private final String value;

    TotalSubject(String value){
        this.value = value;
    }
}
