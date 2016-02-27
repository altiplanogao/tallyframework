package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.IPrimitiveEntry;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.PrimitiveEntries;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class PrimitiveListFieldMeta extends ListFieldMeta<PrimitiveListFieldMeta.Facet> {

    public PrimitiveListFieldMeta(BasicFieldMetaObject bfmo, Facet facet) {
        super(bfmo, facet);
    }

    @Override
    public Class getPresentationClass() {
        return facet.entryTargetDelegateClass;
    }

    @Override
    public Class getPresentationCeilingClass() {
        return facet.entryTargetDelegateClass;
    }

    @Override
    public Class getEntryClass(){
        return facet.entryTargetClass;
    }

    @Override
    public Class getListImplementClass() {
        return facet.listImplType;
    }

    public CollectionMode getCollectionMode() {
        return CollectionMode.Primitive;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.COLLECTION;
    }

    public static class Facet extends ListFacet {

        //declared entry class is not important;
        protected Class<? extends IPrimitiveEntry> entryTargetDelegateClass;

        public Facet(
                Class collectionType, Class collectionImplType,
                Class entryTargetClass, Class<? extends IPrimitiveEntry> entryTargetDelegateClass) {
            super(collectionType, collectionImplType, entryTargetClass);
            this.entryTargetDelegateClass = entryTargetDelegateClass;
        }

        public Facet(ListFacet facet, Class<? extends IPrimitiveEntry> entryTargetDelegateClass) {
            super(facet);
            this.entryTargetDelegateClass = entryTargetDelegateClass;
        }

        public Facet(Field field, boolean useAnnotation,
                     Class explicitEntryTargetClass,
                     Class<? extends IPrimitiveEntry> explicitPrimitiveDelegate) {
            super(field, useAnnotation, explicitEntryTargetClass);
            if(useAnnotation){
                CollectionField cf = field.getDeclaredAnnotation(CollectionField.class);
                if(!CollectionMode.Primitive.equals(cf.mode())){
                    throw new MetadataException("CollectionMode un-match");
                }

                Class<? extends IPrimitiveEntry> marked = cf.primitiveDelegate();
                if (IPrimitiveEntry.class.equals(marked)) {
                    this.entryTargetDelegateClass = null;
                }else {
                    this.entryTargetDelegateClass = marked;
                }
            }
            if (this.entryTargetDelegateClass == null) {
                Class<? extends IPrimitiveEntry> marked = PrimitiveEntries.getDefaultPrimitiveEntry(this.getEntryTargetClass());
                if(marked == null){
                    MetadataException.throwFieldConfigurationException(field, " Delegate not defined");
                }
                this.entryTargetDelegateClass = marked;
            }
            if(explicitPrimitiveDelegate != null && !IPrimitiveEntry.class.equals(explicitPrimitiveDelegate)){
                this.entryTargetDelegateClass = explicitPrimitiveDelegate;
            }
        }
        public static Facet byAnnotation(Field field){
            if(field.isAnnotationPresent(CollectionField.class)){
                return new Facet(field, true, null, null);
            }
            return null;
        }
    }

    public static class Seed implements IFieldMetaSeed {
        protected final Facet facet;

        public Seed(Facet facet) {
            this.facet = facet;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new PrimitiveListFieldMeta(bfmo, facet);
        }
    }

}
