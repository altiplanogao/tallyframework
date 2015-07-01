package com.taoswork.tallybook.dynamic.datameta.description.descriptor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class EntityInfoTypes {
    public final static String ENTITY_INFO_TYPE_FULL = "full";
    public final static String ENTITY_INFO_TYPE_GRID = "grid";
    public final static String ENTITY_INFO_TYPE_FORM = "form";


    private final static Set<String> ENTITY_INFO_TYPES;

    static {
        ENTITY_INFO_TYPES = new HashSet<String>();
        ENTITY_INFO_TYPES.add(ENTITY_INFO_TYPE_FULL);
        ENTITY_INFO_TYPES.add(ENTITY_INFO_TYPE_GRID);
        ENTITY_INFO_TYPES.add(ENTITY_INFO_TYPE_FORM);
    }

    public static boolean isEntityInfoType(String infoType){
        return ENTITY_INFO_TYPES.contains(infoType);
    }
}
