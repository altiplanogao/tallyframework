package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map;

import com.taoswork.tallybook.datadomain.base.entity.MapField;
import com.taoswork.tallybook.datadomain.base.entity.MapMode;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.mapentry.IMapEntryDelegate;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.mapentry.MapEntryDelegates;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/21.
 */
public class BasicMapFieldMeta extends MapFieldMeta<BasicMapFieldMeta.Facet> {

    public BasicMapFieldMeta(BasicFieldMetaObject bfmo, Facet facet) {
        super(bfmo, facet);
    }

    @Override
    public MapMode getMapMode() {
        return MapMode.Basic;
    }

    @Override
    public Class getPresentationClass() {
        return facet.entryTargetDelegateClass;
    }

    @Override
    public Class getPresentationCeilingClass() {
        return facet.entryTargetDelegateClass;
    }

    public Class<? extends IMapEntryDelegate> getMapEntryDelegate(){
        return facet.entryTargetDelegateClass;
    }

    public static class Facet extends MapFacet {
        protected Class<? extends IMapEntryDelegate> entryTargetDelegateClass;

        public Facet(MapFacet facet, Class<? extends IMapEntryDelegate> entryTargetDelegateClass) {
            super(facet);
            this.entryTargetDelegateClass = entryTargetDelegateClass;
        }

        public Facet(Class mapType, Class mapImplType, Class keyType, Class valueTargetType, Class<? extends IMapEntryDelegate> entryTargetDelegateClass) {
            super(mapType, mapImplType, keyType, valueTargetType);
            this.entryTargetDelegateClass = entryTargetDelegateClass;
        }

        public Facet(Field field, boolean useAnnotation,Class explicitValType,
                     Class<? extends IMapEntryDelegate> explicitEntryTargetDelegateClass) {
            super(field, useAnnotation, explicitValType);
            if(useAnnotation){
                MapField mf = field.getDeclaredAnnotation(MapField.class);
                if(!MapMode.Basic.equals(mf.mode())){
                    MetadataException.throwFieldConfigurationException(field);
                }
                Class<? extends IMapEntryDelegate> marked = mf.entryDelegate();
                if(marked == null || marked.equals(IMapEntryDelegate.class)) {
                    Class keyClz = this.keyType;
                    Class valClz = this.getValueTargetType();
                    marked = MapEntryDelegates.getDefaultPrimitiveEntry(keyClz, valClz);
                }
                if(marked != null){
                    this.entryTargetDelegateClass = marked;
                }
            }
            if(explicitEntryTargetDelegateClass != null && !explicitEntryTargetDelegateClass.equals(IMapEntryDelegate.class)){
                this.entryTargetDelegateClass = explicitEntryTargetDelegateClass;
            }
        }

        public static Facet byAnnotation(Field field){
            return new Facet(field, true, null, null);
        }
    }

    public static class Seed implements IFieldMetaSeed {
        protected final Facet facet;

        public Seed(Facet facet) {
            this.facet = facet;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new BasicMapFieldMeta(bfmo, facet);
        }
    }


}
