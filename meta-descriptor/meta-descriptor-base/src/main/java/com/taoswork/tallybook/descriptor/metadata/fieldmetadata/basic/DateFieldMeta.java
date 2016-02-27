package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typed.DateCellMode;
import com.taoswork.tallybook.datadomain.base.presentation.typed.DateMode;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BaseNonCollectionFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

/**
 * Created by Gao Yuan on 2015/10/30.
 */
public final class DateFieldMeta
        extends BaseNonCollectionFieldMeta
        implements IFieldMeta {
    private final DateMode mode;
    private final DateCellMode cellMode;
    private final boolean useJavaDate;

    public DateFieldMeta(BasicFieldMetaObject bfmo,
                         DateMode mode,
                         DateCellMode cellMode,
                         boolean useJavaDate) {
        super(bfmo);
        this.mode = mode;
        this.cellMode = cellMode;
        this.useJavaDate = useJavaDate;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.DATE;
    }

    public DateMode getMode() {
        return mode;
    }

    public DateCellMode getCellMode() {
        return cellMode;
    }

    public boolean isUseJavaDate() {
        return useJavaDate;
    }

    @Override
    public boolean isPrimitiveField() {
        return true;
    }

    /**
     * Created by Gao Yuan on 2015/10/30.
     */
    public static class Seed implements IFieldMetaSeed {
        public final DateMode mode;
        public final DateCellMode cellMode;

        public final boolean useJavaDate;

        public Seed(DateMode mode, DateCellMode cellMode, boolean useJavaDate) {
            this.mode = mode;
            this.cellMode = cellMode;
            this.useJavaDate = useJavaDate;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new DateFieldMeta(bfmo, mode, cellMode, useJavaDate);
        }
    }
}
