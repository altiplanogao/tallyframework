package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.BasicFieldInfoBase;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class ForeignKeyFieldInfo extends BasicFieldInfoBase {
    public final String entityType;
    public final String idFieldName;
    public final String displayFieldName;

    public ForeignKeyFieldInfo(String name, String friendlyName, boolean editable,
                               String entityType, String idFieldName, String displayFieldName) {
        super(name, friendlyName, editable);
        this.entityType = entityType;
        this.idFieldName = idFieldName;
        this.displayFieldName = displayFieldName;
    }
}
