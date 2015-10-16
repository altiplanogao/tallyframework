package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.MapFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;

public class MapFieldMetadata extends BaseCollectionFieldMetadata {

    private final Class keyBasicType;
    private final ClassMetadata keyEmbeddedClassMetadata;
    private final Class keyEntityType;
    private final Class valueBasicType;
    private final ClassMetadata valueEmbeddedClassMetadata;
    private final Class valueEntityType;

    public MapFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        MapFieldFacet mapFieldFacet = (MapFieldFacet) intermediate.getFacet(FieldFacetType.Map);

        this.keyBasicType = mapFieldFacet.getKeyBasicType();
        this.keyEmbeddedClassMetadata = mapFieldFacet.getKeyEmbeddedClassMetadata();
        this.keyEntityType = mapFieldFacet.getKeyEntityType();

        this.valueBasicType = mapFieldFacet.getValueBasicType();
        this.valueEmbeddedClassMetadata = mapFieldFacet.getValueEmbeddedClassMetadata();
        this.valueEntityType = mapFieldFacet.getValueEntityType();
    }

    public Class getKeyBasicType() {
        return keyBasicType;
    }

    public ClassMetadata getKeyEmbeddedClassMetadata() {
        return keyEmbeddedClassMetadata;
    }

    public Class getKeyEntityType() {
        return keyEntityType;
    }

    public Class getValueBasicType() {
        return valueBasicType;
    }

    public ClassMetadata getValueEmbeddedClassMetadata() {
        return valueEmbeddedClassMetadata;
    }

    public Class getValueEntityType() {
        return valueEntityType;
    }

}
