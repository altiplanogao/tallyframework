package com.taoswork.tallybook.dynamic.dataservice.core.query.criteria.util;

import javax.persistence.criteria.Path;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class FieldPathBuilder {
    public static Path buildPath(Path root, String propertyName) {
        Path path = root.get(propertyName);
        return path;
    }
}