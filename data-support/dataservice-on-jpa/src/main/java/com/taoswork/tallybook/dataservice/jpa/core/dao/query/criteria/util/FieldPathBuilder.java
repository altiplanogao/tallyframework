package com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.util;

import javax.persistence.criteria.Path;
import java.util.StringJoiner;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class FieldPathBuilder {
    public static Path buildPath(Path root, String propertyName) {
        Path path = root;
        String[] pieces = propertyName.split("\\.");
        for (String p : pieces) {
            path = path.get(p);
        }
        return path;
    }

    public static Path buildPathBySegments(Path root, String... propertyNames) {
        StringJoiner sj = new StringJoiner(".", "", "");
        for (String pn : propertyNames) {
            sj.add(pn);
        }
        return buildPath(root, sj.toString());
    }
}
