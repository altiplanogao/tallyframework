package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typed.BooleanMode;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BaseNonCollectionFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

public final class BooleanFieldMeta
        extends BaseNonCollectionFieldMeta
        implements IFieldMeta {
    private final BooleanMode mode;

    public BooleanFieldMeta(BasicFieldMetaObject bfmo, BooleanMode _mode) {
        super(bfmo);
        if (null != _mode) {
            this.mode = _mode;
        } else {
            this.mode = BooleanMode.YesNo;
        }
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.BOOLEAN;
    }

    public BooleanMode getMode() {
        return mode;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }

    public static class Seed implements IFieldMetaSeed {
        public final BooleanMode mode;

        public Seed() {
            this(BooleanMode.YesNo);
        }

        public Seed(BooleanMode mode) {
            this.mode = mode;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new BooleanFieldMeta(bfmo, mode);
        }
    }
}
