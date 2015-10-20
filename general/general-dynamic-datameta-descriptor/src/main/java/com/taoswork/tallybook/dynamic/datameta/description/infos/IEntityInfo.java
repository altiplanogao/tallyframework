package com.taoswork.tallybook.dynamic.datameta.description.infos;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;

/**
 * Created by Gao Yuan on 2015/8/9.
 */
public interface IEntityInfo extends NamedInfo {
    String getType();

    boolean isContainsHierarchy();

    String getEntityType();
}
/*
public enum EntityInfoType {
    Main(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_MAIN),
    Full(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_FULL),
    Form(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_FORM),
    Grid(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_GRID),

    PageGrid(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_PAGE_GRID),

    NA("");

    private String name;
    EntityInfoType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}


public class EntityInfoTypeNames {
    public final static String ENTITY_INFO_TYPE_NAME_MAIN = "main";
    public final static String ENTITY_INFO_TYPE_NAME_FULL = "full";
    public final static String ENTITY_INFO_TYPE_NAME_GRID = "grid";
    public final static String ENTITY_INFO_TYPE_NAME_FORM = "form";

    public final static String ENTITY_INFO_TYPE_NAME_PAGE_GRID = "pageGrid";

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
        return ENTITY_INFO_TYPES.get(name);
    }
}

* */