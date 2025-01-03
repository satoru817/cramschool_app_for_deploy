package com.example.demo.constant;

import lombok.Data;
import lombok.Getter;
/**
 * 学校の教科を表す列挙型クラスです。
 * 各教科には文字列値が関連付けられており、getValue()メソッドで取得できます。
 *
 * @since 1.0
 */
@Getter
public enum Subject {
    /** 国語の教科を表します */
    JAPANESE("japanese"),

    /** 数学の教科を表します */
    MATH("math"),

    /** 英語の教科を表します */
    ENGLISH("english"),

    /** 理科の教科を表します */
    SCIENCE("science"),

    /** 社会の教科を表します */
    SOCIAL("social");

    /** 教科に関連付けられた文字列値 */
    private final String value;

    /**
     * Subjectクラスのコンストラクタです。
     * @param value 教科に関連付ける文字列値
     */
    Subject(String value) {
        this.value = value;
    }
}