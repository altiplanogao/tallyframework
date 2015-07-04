package com.taoswork.tallybook.dynamic.datameta.description.descriptor;

/**
 * Created by Gao Yuan on 2015/7/4.
 */
public enum EntityInfoType {
    Full(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_FULL),
    Form(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_FORM),
    Grid(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_GRID);

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
