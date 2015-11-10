package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class ExternalForeignKeyFieldInfo extends ForeignKeyFieldInfo {

    public final String entityFieldName;

    public ExternalForeignKeyFieldInfo(String name, String friendlyName, boolean editable,
                                       String entityType, String entityFieldName,
                                       String entityFieldIdProperty,
                                       String entityFieldDisplayProperty) {
        super(name, friendlyName, editable, entityType,
            entityFieldIdProperty,
            entityFieldDisplayProperty);
        this.entityFieldName = entityFieldName;
    }
}
