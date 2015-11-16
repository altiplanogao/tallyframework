package com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection;

/**
 * Created by Gao Yuan on 2015/11/13.
 */
public enum CollectionModel {
    Primitive,    // primitive types
    Embeddable,   // embeddable
    Entity,        // new entity, one-many relation
    Lookup,        // link to other entity-type (many-many)
    AdornedLookup, // link to other entity-type (many-many, java-type: map)
    Unknown
}
