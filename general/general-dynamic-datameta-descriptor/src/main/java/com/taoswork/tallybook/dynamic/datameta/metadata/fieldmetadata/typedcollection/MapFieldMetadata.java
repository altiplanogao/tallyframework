package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.metadata.ElementTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.collections.MapFieldMetadataFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.BaseCollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.FieldMetadataIntermediate;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class MapFieldMetadata extends BaseCollectionFieldMetadata {
    private final Class mapImplementType;

    private final ElementTypeUnion keyType;
    private final ElementTypeUnion valueType;

    public MapFieldMetadata(FieldMetadataIntermediate intermediate) {
        super(intermediate);
        MapFieldMetadataFacet facet = (MapFieldMetadataFacet) intermediate.getFacet(FieldFacetType.Map);
        this.basicFieldMetadataObject.setFieldTypeIfUnknown(FieldType.MAP);

        this.keyType = facet.getKeyType();

        this.valueType = facet.getValueType();

        this.mapImplementType = workOutMapImplementType(facet.getMapType());
    }

    public ElementTypeUnion getKeyType() {
        return keyType;
    }

    public ElementTypeUnion getValueType() {
        return valueType;
    }

    private Class workOutMapImplementType(Class collectionType) {
        try {
            Constructor constructor = collectionType.getConstructor(new Class[]{});
            return collectionType;
        } catch (NoSuchMethodException e) {
            //Ignore this exception, because it is not an instantiatable object.
        }
        if (Map.class.equals(collectionType)) {
            return HashMap.class;
        }  else {
            return HashMap.class;
        }
    }

    public Class getMapImplementType() {
        return mapImplementType;
    }
}
