package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class EntityListFieldMeta extends ListFieldMeta<EntityListFieldMeta.Facet> {

    public EntityListFieldMeta(BasicFieldMetaObject bfmo, Facet facet) {
        super(bfmo, facet);
    }

    @Override
    public Class getPresentationClass() {
        return facet.entryTargetClass;
    }

    @Override
    public Class getPresentationCeilingClass() {
        return facet.entryTargetClass;
    }

    @Override
    public Class getEntryClass() {
        return facet.entryTargetClass;
    }

    @Override
    public Class getListImplementClass() {
        return facet.listImplType;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.COLLECTION;
    }

    public static class Facet extends ListFacet {
        private String mappedBy;

        public Facet(Class collectionType, Class collectionImplType, Class entryTargetClass) {
            super(collectionType, collectionImplType, entryTargetClass);
        }

        public Facet(ListFacet other) {
            super(other);
        }

        public Facet(Field field, boolean useAnnotation, Class explicitEntryTargetClass) {
            super(field, useAnnotation, explicitEntryTargetClass);
            if(useAnnotation){
                CollectionField cf = field.getDeclaredAnnotation(CollectionField.class);
                if (!CollectionMode.Entity.equals(cf.mode()) ) {
                    throw new MetadataException("CollectionMode un-match");
                }
            }
        }

        public static Facet byAnnotation(Field field){
            if(field.isAnnotationPresent(CollectionField.class)){
                return new Facet(field, true, null);
            }
            return null;
        }

        public String getMappedBy() {
            return mappedBy;
        }

        public void setMappedBy(String mappedBy) {
            this.mappedBy = mappedBy;
        }
    }

    public static class Seed implements IFieldMetaSeed {
        protected final Facet facet;

        public Seed(Facet facet) {
            this.facet = facet;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new EntityListFieldMeta(bfmo, facet);
        }
    }

}
