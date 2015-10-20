package com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.facet.IFieldMetadataFacet;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FieldMetadataIntermediate implements Serializable {
    private final static Logger LOGGER = LoggerFactory.getLogger(FieldMetadataIntermediate.class);
    public final Map<FieldFacetType, IFieldMetadataFacet> facets = new HashMap<FieldFacetType, IFieldMetadataFacet>();
    private final BasicFieldMetadataObject basicFieldMetadataObject;
    private Class<? extends IFieldMetadata> targetMetadataType;

    public FieldMetadataIntermediate(int originalOrder, Field field) {
        this.basicFieldMetadataObject = new BasicFieldMetadataObject(originalOrder, field);
    }

    public boolean isCollectionField() {
        return facets.containsKey(FieldFacetType.Collection);
    }

    public void addFacet(IFieldMetadataFacet facet) {
        IFieldMetadataFacet existingFacet = facets.get(facet.getType());
        if (existingFacet == null) {
            facets.put(facet.getType(), facet);
        } else {
            existingFacet.merge(facet);
        }
    }

    public IFieldMetadataFacet getFacet(FieldFacetType facetType) {
        IFieldMetadataFacet existingFacet = facets.get(facetType);
        return existingFacet;
    }

    public BasicFieldMetadataObject getBasicFieldMetadataObject() {
        return basicFieldMetadataObject;
    }

    public boolean showOnGrid() {
        return (!isCollectionField()) && Visibility.gridVisible(basicFieldMetadataObject.getVisibility());
    }

    public Class<? extends IFieldMetadata> getTargetMetadataType() {
        return targetMetadataType;
    }

    public FieldMetadataIntermediate setTargetMetadataType(Class<? extends IFieldMetadata> targetMetadataType) {
        this.targetMetadataType = targetMetadataType;
        return this;
    }
}
