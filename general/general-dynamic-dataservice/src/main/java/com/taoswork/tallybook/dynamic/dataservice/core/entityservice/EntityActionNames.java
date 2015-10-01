package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

import com.taoswork.tallybook.general.authority.core.basic.Access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * May refer to {@link com.taoswork.tallybook.dynamic.dataservice.server.io.EntityActionPaths }
 */
public final class EntityActionNames {
    private EntityActionNames()throws IllegalAccessException{throw new IllegalAccessException("Not instanceable object");}

    public static final String CREATE = "create";
    public static final String READ = "read";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String QUERY = "query";

    public static final String INSPECT = "inspect";

    public static final String SAVE = "save";
    public static final String REORDER = "reorder";
}
