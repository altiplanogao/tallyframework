package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasePrimitiveFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

public final class EnumFieldMeta extends BasePrimitiveFieldMeta {
    private final Class enumerationType;

    public EnumFieldMeta(BasicFieldMetaObject bfmo, Class enumerationType) {
        super(bfmo);
        if (null != enumerationType) {
            this.enumerationType = enumerationType;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.ENUMERATION;
    }

    public Class getEnumerationType() {
        return enumerationType;
    }

    @Override
    public Object getValueFromString(String valStr) {
        return Enum.valueOf(enumerationType, valStr);
    }

    /**
     * Created by Gao Yuan on 2015/5/25.
     */
    public static class Seed implements IFieldMetaSeed {
        private final Class enumerationType;

        public Seed(Class enumerationType) {
            this.enumerationType = enumerationType;
        }

        public Class getEnumerationType() {
            return enumerationType;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new EnumFieldMeta(bfmo, enumerationType);
        }
    }
}
