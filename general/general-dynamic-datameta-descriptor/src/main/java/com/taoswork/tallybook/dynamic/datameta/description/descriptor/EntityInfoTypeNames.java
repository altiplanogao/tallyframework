package com.taoswork.tallybook.dynamic.datameta.description.descriptor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class EntityInfoTypeNames {
    public final static String ENTITY_INFO_TYPE_NAME_FULL = "full";
    public final static String ENTITY_INFO_TYPE_NAME_GRID = "grid";
    public final static String ENTITY_INFO_TYPE_NAME_FORM = "form";

    private final static Map<String, EntityInfoType> ENTITY_INFO_TYPES;

    static {
        ENTITY_INFO_TYPES = new HashMap<String, EntityInfoType>();
        for (EntityInfoType infoType : EntityInfoType.values()){
            ENTITY_INFO_TYPES.put(infoType.getName(), infoType);
        }
    }

    public static boolean isEntityInfoType(String infoType){
        return ENTITY_INFO_TYPES.containsKey(infoType);
    }

    public static EntityInfoType entityInfoTypeOf(String name){
        return ENTITY_INFO_TYPES.getOrDefault(name, null);
    }
}
