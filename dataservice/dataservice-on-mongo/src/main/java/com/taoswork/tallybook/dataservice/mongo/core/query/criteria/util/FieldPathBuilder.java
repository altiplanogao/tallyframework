package com.taoswork.tallybook.dataservice.mongo.core.query.criteria.util;

import org.apache.commons.lang3.StringUtils;

import java.util.StringJoiner;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class FieldPathBuilder {
    public static String buildPath(String root, String propertyName) {
        String path = root;
        String[] pieces = propertyName.split("\\.");
        StringJoiner sj = new StringJoiner(".", "", "");
        if (StringUtils.isNotEmpty(root)) {
            sj.add(root);
        }
        for (String p : pieces) {
            sj.add(p);
        }
        return sj.toString();
    }

    public static String buildPathBySegments(String root, String... propertyNames) {
        StringJoiner sj = new StringJoiner(".", "", "");
        for (String pn : propertyNames) {
            sj.add(pn);
        }
        return buildPath(root, sj.toString());
    }
}
