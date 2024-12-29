package com.example.demo.util;

import com.example.demo.dto.AverageScoreForKlass;

import java.util.LinkedHashMap;
import java.util.List;

public class UtilForNullRemoval {
    public static <T extends AverageScoreForKlass> void eraseNullEntry(LinkedHashMap<String, List<T>> subjectAverages) {
        subjectAverages.entrySet().removeIf(entry ->
                entry.getValue().stream().allMatch(avg -> avg.getAvgScore() == null));
    }
}