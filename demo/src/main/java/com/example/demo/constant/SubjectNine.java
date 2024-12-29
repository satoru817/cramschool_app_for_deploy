package com.example.demo.constant;

import lombok.Getter;

@Getter
public enum SubjectNine {
    JAPANESE("japanese"),
    MATH("math"),
    ENGLISH("english"),
    SCIENCE("science"),
    SOCIAL("social"),
    ART("art"),
    PE("pe"),
    TECH("tech"),
    MUSIC("music");

    private final String value;

    SubjectNine(String value){
        this.value = value;
    }
}
