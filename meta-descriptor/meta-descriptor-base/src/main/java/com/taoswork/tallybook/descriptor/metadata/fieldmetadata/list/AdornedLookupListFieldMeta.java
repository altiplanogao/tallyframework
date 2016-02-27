package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class AdornedLookupListFieldMeta extends ListFieldMeta<AdornedLookupListFieldMeta.Facet> {

    public AdornedLookupListFieldMeta(BasicFieldMetaObject bfmo, Facet facet) {
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

    public Class getJoinEntity(){
        return facet.getJoinEntity();
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.COLLECTION;
    }

    @Override
    public int gatherReferencingTypes(Collection<Class> collection) {
        collection.add(getJoinEntity());
        return 1 + super.gatherReferencingTypes(collection);
    }

    public static class Facet extends ListFacet {

        private Class joinEntity;

        public Facet(Class collectionType, Class collectionImplType, Class entryTargetClass) {
            super(collectionType, collectionImplType, entryTargetClass);
        }

        public Facet(ListFacet other) {
            super(other);
        }

        public Facet(Field field, boolean useAnnotation, Class explicitEntryTargetClass, Class explicitJoinEntity) {
            super(field, useAnnotation, explicitEntryTargetClass);
            if(useAnnotation){
                CollectionField cf = field.getDeclaredAnnotation(CollectionField.class);
                if (!CollectionMode.AdornedLookup.equals(cf.mode()) ) {
                    throw new MetadataException("CollectionMode un-match");
                }
                Class joinEntity = cf.joinEntity();
                if(joinEntity.equals(void.class)){
                    MetadataException.throwFieldConfigurationException(field, "joinEntity not defined.");
                }
                this.joinEntity = joinEntity;
            }
            if(explicitJoinEntity != null){
                this.joinEntity = explicitJoinEntity;
            }
        }

        public static Facet byAnnotation(Field field){
            if(field.isAnnotationPresent(CollectionField.class)){
                return new Facet(field, true, null, null);
            }
            return null;
        }

        public Class getJoinEntity() {
            return joinEntity;
        }

        public void setJoinEntity(Class joinEntity) {
            this.joinEntity = joinEntity;
        }
    }

    public static class Seed implements IFieldMetaSeed {
        protected final Facet facet;

        public Seed(Facet facet) {
            this.facet = facet;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new AdornedLookupListFieldMeta(bfmo, facet);
        }
    }

}
