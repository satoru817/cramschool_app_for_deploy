package com.example.demo.constant;

import lombok.Getter;

/**
 * 9教科における合計科目数の種類を表す列挙型クラスです。
 * 主要5教科と全9教科の区分を定義します。
 *
 * @since 1.0
 */
@Getter
public enum TotalSubjectForNine {
    /** 主要5教科（国語・数学・英語・理科・社会）の合計を表します */
    TOTAL5("total5"),

    /** 全9教科（主要5教科に加えて美術・体育・技術・音楽）の合計を表します */
    TOTAL9("total9");

    /** 合計科目区分に関連付けられた文字列値 */
    private final String value;

    /**
     * TotalSubjectForNineクラスのコンストラクタです。
     * @param value 合計科目区分に関連付ける文字列値
     */
    TotalSubjectForNine(String value) {
        this.value = value;
    }
}