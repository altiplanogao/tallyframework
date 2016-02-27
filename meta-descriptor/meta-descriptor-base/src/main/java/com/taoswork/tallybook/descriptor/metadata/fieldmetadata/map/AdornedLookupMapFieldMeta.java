package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map;

import com.taoswork.tallybook.datadomain.base.entity.MapField;
import com.taoswork.tallybook.datadomain.base.entity.MapMode;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/21.
 */
public class AdornedLookupMapFieldMeta extends MapFieldMeta<AdornedLookupMapFieldMeta.Facet> {

    public AdornedLookupMapFieldMeta(BasicFieldMetaObject bfmo, Facet facet) {
        super(bfmo, facet);
    }

    @Override
    public MapMode getMapMode() {
        return MapMode.AdornedLookup;
    }

    @Override
    public Class getPresentationClass() {
        return facet.valueTargetType;
    }

    @Override
    public Class getPresentationCeilingClass() {
        return facet.valueTargetType;
    }

    public static class Facet extends MapFacet {

        public Facet(MapFacet facet) {
            super(facet);
        }

        public Facet(Class mapType, Class mapImplType, Class keyType, Class valueTargetType) {
            super(mapType, mapImplType, keyType, valueTargetType);
        }

        public Facet(Field field, boolean useAnnotation,Class explicitValType) {
            super(field, useAnnotation, explicitValType);
            if(useAnnotation){
                MapField mf = field.getDeclaredAnnotation(MapField.class);
                if(!MapMode.Lookup.equals(mf.mode())){
                    MetadataException.throwFieldConfigurationException(field);
                }
            }
        }

        public static Facet byAnnotation(Field field){
            return new Facet(field, true, null);
        }
    }

    public static class Seed implements IFieldMetaSeed {
        protected final Facet facet;

        public Seed(Facet facet) {
            this.facet = facet;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new AdornedLookupMapFieldMeta(bfmo, facet);
        }
    }


}
