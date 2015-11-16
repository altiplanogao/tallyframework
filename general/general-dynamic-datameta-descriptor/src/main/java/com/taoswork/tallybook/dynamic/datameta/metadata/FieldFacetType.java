package com.taoswork.tallybook.dynamic.datameta.metadata;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public enum FieldFacetType {
    Basic,

    Enum,
    Boolean,
    ForeignEntity,
    ExternalForeignEntity,
    Date,

    Embedded,

    Collection,
    Map;
}
