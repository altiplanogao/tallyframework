package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasePrimitiveFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

public final class StringFieldMeta
        extends BasePrimitiveFieldMeta {
    private final int length;

    public StringFieldMeta(BasicFieldMetaObject bfmo, int length) {
        super(bfmo);
        this.length = length;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.STRING;
    }

    public int getLength() {
        return length;
    }

    /**
     * Created by Gao Yuan on 2015/9/28.
     */
    public static class Seed implements IFieldMetaSeed {
        protected int length = -1;

        public int getLength() {
            return length;
        }

        public Seed setLength(int length) {
            this.length = length;
            return this;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new StringFieldMeta(bfmo, length);
        }
    }

    @Override
    public Object getValueFromString(String valStr) {
        return valStr;
    }
}
