package com.example.demo.constant;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Subject {
    JAPANESE("japanese"),
    MATH("math"),
    ENGLISH("english"),
    SCIENCE("science"),
    SOCIAL("social");

    private final String value;

    Subject(String value) {
        this.value = value;
    }

}