package com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection;

import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * see comments in BaseCollectionFieldMetadata ({@link com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata})
 *
 *  1.1 (A.1) Collection<Entity>: One-Many  : foreign key saved in the entity [save: entity]
 *  1.2 (B.1) Collection<Entity> (with join table): Many-Many (there must be a join table)
 *       Case a: the JavaType of the other side of this relation is Collection: [save: relation-link]
 *       Case b: the JavaType of the other side of this relation is Map, Map key uses entity's column: (same as case a)[save: relation-link]
 *       Case c: the JavaType of the other side of this relation is Map, use manual Map key, key saved in join-table: add entity + add key [save: relation-link + key]
 *  1.3 (C.1) Collection<Basic>: Element-Collection: add elements [save: collection-record]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PresentationCollection {

    CollectionModel collectionModel() default CollectionModel.Unknown;

    /**
     * Optional, and only useful on 1.2.Case C
     * @return
     */
    Class joinEntity() default void.class;

    Class<? extends ISimpleEntryDelegate> simpleEntryDelegate() default ISimpleEntryDelegate.class;
}
