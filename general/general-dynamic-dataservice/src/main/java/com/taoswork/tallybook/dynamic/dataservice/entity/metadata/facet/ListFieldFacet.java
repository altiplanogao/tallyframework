package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.facet;

import java.lang.reflect.Type;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class ListFieldFacet extends CollectionFieldFacetBase{
    public ListFieldFacet(Type genericType) {

    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.List;
    }
}
