package com.example.demo.constant;

import lombok.Getter;

/**
 * 合計科目数の種類を表す列挙型クラスです。
 * 主要3教科（国語・数学・英語）と主要5教科（国語・数学・英語・理科・社会)の区分を定義します。
 *
 * @since 1.0
 */
@Getter
public enum TotalSubject {
    /** 主要3教科（国語・数学・英語）の合計を表します */
    TOTAL3("total3"),

    /** 主要5教科（国語・数学・英語・理科・社会）の合計を表します */
    TOTAL5("total5");

    /** 合計科目区分に関連付けられた文字列値 */
    private final String value;

    /**
     * TotalSubjectクラスのコンストラクタです。
     * @param value 合計科目区分に関連付ける文字列値
     */
    TotalSubject(String value) {
        this.value = value;
    }
}