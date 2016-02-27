package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BaseNonCollectionFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

public final class PaleFieldMeta
        extends BaseNonCollectionFieldMeta
        implements IFieldMeta {
    public PaleFieldMeta(BasicFieldMetaObject bfmo) {
        super(bfmo);
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.UNKNOWN;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }

    /**
     * Created by Gao Yuan on 2016/2/19.
     */
    public static class Seed implements IFieldMetaSeed {
        public Seed() {
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new PaleFieldMeta(bfmo);
        }
    }
}
