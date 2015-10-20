package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.collection;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.IFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

/**
 * Created by Gao Yuan on 2015/10/20.
 */
public class CollectionFacet implements IFieldFacet {
    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Collection;
    }
}
