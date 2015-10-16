package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.CollectionFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;

public class CollectionFieldMetadata extends BaseCollectionFieldMetadata {
    private final Class basicType;
    private final ClassMetadata embeddedClassMetadata;
    private final Class entityType;

    private transient ClassMetadata entityClassMetadata;

    public CollectionFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        CollectionFieldFacet facet = (CollectionFieldFacet) intermediate.getFacet(FieldFacetType.Collection);

        this.basicType = facet.getBasicType();
        this.embeddedClassMetadata = facet.getEmbeddedClassMetadata();
        this.entityType = facet.getEntityType();
    }

    public Class getBasicType() {
        return basicType;
    }

    public ClassMetadata getEmbeddedClassMetadata() {
        return embeddedClassMetadata;
    }

    public Class getEntityType() {
        return entityType;
    }

    public ClassMetadata getEntityClassMetadata() {
        return entityClassMetadata;
    }

    public void setEntityClassMetadata(ClassMetadata entityClassMetadata) {
        this.entityClassMetadata = entityClassMetadata;
    }

}
