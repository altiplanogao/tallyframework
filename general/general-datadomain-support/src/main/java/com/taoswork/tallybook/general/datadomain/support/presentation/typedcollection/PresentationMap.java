package com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection;

import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * see comments in BaseCollectionFieldMetadata ({@link com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata}
 *
 *  2.1 (A.2) Map<Basic,Entity> / Map<Entity,Entity> (key saved in the entry): One-Many     [Entity]
 *  2.2 (B.2) Map<Basic,Entity> / Map<Entity,Entity> (key saved in the join table entry)    [Key, Entity]       (NOTE: not able to add on other side of relation)
 *  2.3 (C.2) Map<Basic,Basic> / Map<Entity, Basic> (key/val saved in the collection table entry)   [key, val]
 *
 *  1.1 (A.1) Collection<Entity>: One-Many  : Only entity saved. foreign key saved in the entity
 *  1.2 (B.1) Collection<Entity> (with join table): Many-Many (there must be a join table)
 *       Case a: the JavaType of the other side of this relation is Collection: Just add entity
 *       Case b: the JavaType of the other side of this relation is Map, Map key uses entity's column: Just add entity
 *       Case c: the JavaType of the other side of this relation is Map, use manual Map key, key saved in join-table: add entity + add key
 *  1.2 (C.1) Collection<Basic>: Element-Collection: add elements
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PresentationMap {
    EntryType keyEntryType () default EntryType.Unknown;

    Class<? extends ISimpleEntryDelegate> simpleKeyEntryDelegate() default ISimpleEntryDelegate.class;

    EntryType valueEntryType () default EntryType.Unknown;

    Class<? extends ISimpleEntryDelegate> simpleValueEntryDelegate() default ISimpleEntryDelegate.class;

}
