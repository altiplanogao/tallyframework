package com.taosworks.tallybook.descriptor.mongo.metadata.fieldmetadata;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BaseNonCollectionFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class ObjectIdFieldMeta extends BaseNonCollectionFieldMeta {
    public ObjectIdFieldMeta(BasicFieldMetaObject bfmo) {
        super(bfmo);
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.ID;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }

    public static class Seed implements IFieldMetaSeed {
        public Seed() {
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new ObjectIdFieldMeta(bfmo);
        }
    }
}
