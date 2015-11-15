package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata;

/**
 * For collection:
 * There are 3 kinds of Relationships/Element-Collections: (with different behaviors)
 * A. One-Many
 *      A.1 As Java Collection: Has ForeignKey on ManySide
 *      A.2 As Java Map: Map<Basic,Entity> / Map<Entity,Entity> (key saved in the entry)
 * B. Many-Many (there must be a join table)
 *      B.1 As Java Collection: Ids for both side saved in join table
 *      B.2 As Java Map: Map<Basic,Entity> / Map<Entity,Entity> (key saved in the join table entry)
 * C. Element-Collection
 *      C.1 As Java Collection/Array<Basic></>: Collection content saved in the collection table
 *      C.2 As Java Map: Map<Basic,Basic> / Map<Entity, Basic> (key saved in the collection table entry)
 *
 *
 *  In another point of view:
 *  1. Java Collection
 *      1.1 (A.1) Collection<Entity>: One-Many
 *      1.2 (B.1) Collection<Entity> (with join table): Many-Many (JoinObject needed in case of 2.2)
 *      1.3 (C.1) Collection<Basic>: Element-Collection
 *  2. Java Map
 *      2.1 (A.2) Map<Basic,Entity> / Map<Entity,Entity> (key saved in the entry): One-Many     [Entity]
 *      2.2 (B.2) Map<Basic,Entity> / Map<Entity,Entity> (key saved in the join table entry)    [Key, Entity]       (NOTE: not able to add on other side of relation)
 *      2.3 (C.2) Map<Basic,Basic> / Map<Entity, Basic> (key/val saved in the collection table entry)   [key, val]
 */

public abstract class BaseCollectionFieldMetadata extends BaseFieldMetadata {
    public BaseCollectionFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
    }

    @Override
    public boolean isPrimitiveField() {
        return false;
    }

    @Override
    public boolean isCollectionField() {
        return true;
    }

    @Override
    public boolean isNameField() {
        return false;
    }

    @Override
    public void setNameField(boolean b) {
        throw new IllegalStateException("Should never be called");
    }

    @Override
    final public boolean showOnGrid() {
        return false;
    }

    @Override
    final public boolean isId() {
        return false;
    }
}
