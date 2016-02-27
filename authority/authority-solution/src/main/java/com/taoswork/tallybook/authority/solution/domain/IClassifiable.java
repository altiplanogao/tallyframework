package com.taoswork.tallybook.authority.solution.domain;

import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/25.
 */
public interface IClassifiable {
    public static final String FN_CLASSIFICATIONS = "classifications";

    void clearClassifications();

    void addClassification(String classification);

    void addClassifications(String... classifications);

    List<String> getClassifications();

    void setClassifications(List<String> classifications);
}
