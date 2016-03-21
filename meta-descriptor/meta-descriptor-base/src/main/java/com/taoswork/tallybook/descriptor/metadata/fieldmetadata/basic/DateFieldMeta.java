package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typed.DateCellMode;
import com.taoswork.tallybook.datadomain.base.presentation.typed.DateMode;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasePrimitiveFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/10/30.
 */
public final class DateFieldMeta
        extends BasePrimitiveFieldMeta {
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
    public Object getValueFromString(String valStr) {
        if (StringUtils.isEmpty(valStr)) {
            return null;
        } else {
            Long ms = Long.parseLong(valStr);
            Object val = ms;
            if (useJavaDate) {
                val = new Date(ms);
            }
            return val;
        }
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
