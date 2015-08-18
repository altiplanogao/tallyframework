package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

/**
 * Created by Gao Yuan on 2015/8/18.
 */
public final class EntityActionNames {
    private EntityActionNames()throws IllegalAccessException{throw new IllegalAccessException("Not instanceable object");}

    public static final String ADD = "add";
    public static final String SEARCH = "search";
    public static final String INSPECT = "inspect";

    public static final String READ = "read";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
}
