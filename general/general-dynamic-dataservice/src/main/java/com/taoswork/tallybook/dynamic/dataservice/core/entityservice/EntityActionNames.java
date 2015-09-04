package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

/**
 * May refer to {@link com.taoswork.tallybook.dynamic.dataservice.server.io.EntityActionPaths }
 */
public final class EntityActionNames {
    private EntityActionNames()throws IllegalAccessException{throw new IllegalAccessException("Not instanceable object");}

    public static final String ADD = "add";
    public static final String SEARCH = "search";
    public static final String INSPECT = "inspect";
    public static final String REORDER = "reorder";

    public static final String READ = "read";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
}
