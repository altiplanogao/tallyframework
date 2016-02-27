package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map;

import com.taoswork.tallybook.datadomain.base.entity.MapMode;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BaseCollectionFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/21.
 */
public abstract class MapFieldMeta<Mf extends MapFacet> extends BaseCollectionFieldMeta {
    protected final Mf facet;
    public MapFieldMeta(BasicFieldMetaObject bfmo, Mf facet) {
        super(bfmo);
        this.facet = facet;
    }

    public abstract MapMode getMapMode();

    public Class getMapImplementClass(){
        return facet.getMapImplType();
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.MAP;
    }

    @Override
    public int gatherReferencingTypes(Collection<Class> collection) {
        Class presentCls = getPresentationClass();
        if(presentCls != null){
            collection.add(presentCls);
            return 1;
        }
        return 0;
    }
}
