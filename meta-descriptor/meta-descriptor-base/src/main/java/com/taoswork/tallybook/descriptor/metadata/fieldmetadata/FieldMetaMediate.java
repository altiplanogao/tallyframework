package com.taoswork.tallybook.descriptor.metadata.fieldmetadata;

import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFacet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FieldMetaMediate implements Serializable {
    private final static Logger LOGGER = LoggerFactory.getLogger(FieldMetaMediate.class);
    private final Map<String, IFacet> facets = new HashMap<String, IFacet>();
//    public final Map<FieldFacetType, IFieldMetaSeed> facets = new HashMap<FieldFacetType, IFieldMetaSeed>();
    private final MutableClassMeta mutableClassMetadata;
    private final BasicFieldMetaObject basicFieldMetaObject;
//    private Class<? extends IFieldMeta> targetMetadataType;
    private IFieldMetaSeed metaSeed;

    public FieldMetaMediate(MutableClassMeta mutableClassMetadata, int originalOrder, Field field) {
        this.mutableClassMetadata = mutableClassMetadata;
        this.basicFieldMetaObject = new BasicFieldMetaObject(originalOrder, field);
    }

    public void pushFacet(IFacet facet){
        if(null == facet)
            return;
        Class facetCls = facet.getClass();
        facets.put(facetCls.getName(), facet);
    }

    public <T extends IFacet> T getFacet(Class<T> facetClz){
        if(null == facetClz)
            return null;
        IFacet facet = facets.get(facetClz.getName());
        if(facet == null)
            return null;
        if(facetClz.isAssignableFrom(facet.getClass())){
            return (T)facet;
        }
        return null;
    }

    public IFieldMetaSeed getMetaSeed() {
        return metaSeed;
    }

    public boolean noSeed(){
        return metaSeed == null;
    }
    public void setMetadataSeedIfNot(IFieldMetaSeed seed) {
        if(this.metaSeed == null)
            setMetaSeed(seed);
    }

    public void setMetaSeed(IFieldMetaSeed seed) {
        if(this.metaSeed != null){
            throw new IllegalAccessError("setMetaSeed accessed multiple times: " + basicFieldMetaObject.getDeclaringClassName() + "." + basicFieldMetaObject.getField());
        }
        this.metaSeed = seed;
    }

    public MutableClassMeta getMutableClassMetadata() {
        return mutableClassMetadata;
    }

    public BasicFieldMetaObject getBasicFieldMetaObject() {
        return basicFieldMetaObject;
    }
}
