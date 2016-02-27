package com.taoswork.tallybook.datadomain.base.entity;

/**
 * For collection:
 * There are 3 kinds of Relationships/Element-Collections: (with different behaviors)
 * A. Element-Collection (data saved in collection-record-table)
 *      A.1 [Java Collection]: Collection<Basic>: Collection content saved in the collection table
 *          (New Entry) [CollectionMode.Basic]: Create a new entry
 *      A.2 [Java Map]: Map<Basic,Basic> / Map<Entity, Basic> (key saved in the collection table entry)
 *          (New Entry) [CollectionMode.Basic]: Create a new entry
 * B. One-Many (data saved in entity-table)
 *      B.1 [Java Collection]: Collection<Entity>  Has ForeignKey on ManySide
 *          (New Entry) [CollectionMode.Entity]: Create a new entity with foreign-key pre-set
 *      B.2 [Java Map]: Map<Basic,Entity> / Map<Entity,Entity> (key saved in the entry-entity)
 *          (New Entry) [CollectionMode.Entity]: Create a new entity with foreign-key pre-set
 * C. Many-Many (there must be a join table)
 *      C.1 [Java Collection]: Collection<Entity>. Ids for both side saved in join-table-entry
 *          (Lookup/Adorned): A new record in join-table.
 *          Many-Many relation has 2 sides, so there are conditions:
 *              Case a (Lookup): the JavaType of the other side of this relation is Collection: [save: relation-link]
 *              Case b (Lookup): the JavaType of the other side of this relation is Map, map key uses entity's column: (same as case a)[save: relation-link]
 *              Case c (Adorned): the JavaType of the other side of this relation is Map, use manual Map key, key saved in join-table, [save: relation-link + key]
 *      C.2 [Java Map]: Map<Basic,Entity> / Map<Entity,Entity> (key saved in the join-table-entry)
 *          (Lookup/Adorned): A new record in join-table.
 *          There are conditions:
 *              Case a (Lookup): map-key uses entity's column. [save: relation-link]
 *              Case b (Adorned): map-key uses manual input. key saved in join-table, [save: relation-link + key]
 *
 *
 *  In another point of view:
 *  1. [Java Collection]
 *      1.1 (A.1) Collection<Basic>: {Element-Collection} (data saved in collection-record-table)
 *          (New Entry) [CollectionMode.Basic, CollectionMode.Primitive]: Create a new entry.
 *      1.2 (B.1) Collection<Entity>: {One-Many} (foreign-key in entry entity)
 *          (New Entry) [CollectionMode.Entity]: Create a new entity with foreign-key pre-set.
 *      1.3 (C.1) Collection<Entity> (with join table): Many-Many (JoinObject needed in case of 2.2)
 *          (Lookup/Adorned): A new record in join-table.
 *          Many-Many relation has 2 sides, so there are conditions:
 *              Case a (CollectionMode.Lookup): the JavaType of the other side of this relation is Collection: [save: relation-link]
 *              Case b (CollectionMode.Lookup): the JavaType of the other side of this relation is Map, map key uses entity's column: (same as case a)[save: relation-link]
 *              Case c (CollectionMode.AdornedLookup): the JavaType of the other side of this relation is Map, use manual Map key, key saved in join-table, [save: relation-link + key]
 *  2. [Java Map]
 *      2.1 (A.2) Map<Basic,Basic> / Map<Entity, Basic> (key/val saved in the collection table entry)   [key, val]
 *          (New Entry) [CollectionMode.Basic+]: Create a new entry (Entry type: key + value)
 *      2.2 (B.2) Map<Basic,Entity> / Map<Entity,Entity> (key saved in the entry): One-Many     [Entity]
 *          (New Entry) [CollectionMode.Entity]: Create a new entity with foreign-key pre-set
 *      2.3 (C.2) Map<Basic,Entity> / Map<Entity,Entity> (key saved in the join table entry)    [Key, Entity]       (NOTE: not able to add on other side of relation)
 *          (Lookup/Adorned): A new record in join-table.
 *          There are conditions:
 *              Case a (Lookup): map-key uses entity's column. [save: relation-link]
 *              Case b (Adorned): map-key uses manual input. key saved in join-table, [save: relation-link + key]
 */
public enum CollectionMode {
    Primitive,     //1.1 (A.1) // Primitive (a case of basic)
    Basic,          //1.1 (A.1) // basic
    Entity,        //1.2 (B.1)// new entity, one-many relation
    Lookup,        //1.3 (C.1-a,b)// link to other entity-type (many-many)
    AdornedLookup, //1.3 (C.1-c)// link to other entity-type (many-many, java-type: map)
    Unknown
}
