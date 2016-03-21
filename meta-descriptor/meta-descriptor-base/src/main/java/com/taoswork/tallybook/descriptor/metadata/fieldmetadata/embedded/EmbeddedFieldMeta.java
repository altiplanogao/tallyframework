package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.embedded;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BaseNonCollectionFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFacet;

public final class EmbeddedFieldMeta extends BaseNonCollectionFieldMeta {
    private final IClassMeta classMeta;

    public EmbeddedFieldMeta(BasicFieldMetaObject bfmo, IClassMeta classMeta) {
        super(bfmo);
        this.classMeta = classMeta;
    }

    public IClassMeta getClassMetadata() {
        return classMeta;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.EMBEDDABLE;
    }

//    @Override
//    public boolean isPrimitiveField() {
//        return false;
//    }

    public static class Facet implements IFacet {
        private final Class embeddableClz;
        private IClassMeta embeddedMetadata;

        public Facet(Class embeddableClz) {
            this.embeddableClz = embeddableClz;
        }

        public Class getEmbeddableClz() {
            return embeddableClz;
        }

        public IClassMeta getEmbeddedMetadata() {
            return embeddedMetadata;
        }

        public void setEmbeddedMetadata(IClassMeta embeddedMetadata) {
            this.embeddedMetadata = embeddedMetadata;
        }
    }

    public static class Seed implements IFieldMetaSeed {
        public final Facet facet;

        public Seed(Facet facet) {
            this.facet = facet;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            IClassMeta cm = facet.embeddedMetadata;
            if(cm == null){
                throw new MetadataException("facet.embeddedMetadata should be set by EmbeddedFieldHandler");
            }

            return new EmbeddedFieldMeta(bfmo, cm);
        }
    }

}
