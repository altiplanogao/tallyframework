package com.taoswork.tallybook.dataservice.core.entityprotect.field;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public class FieldTypeType {
    public final FieldType type;
    public final String clz;

    public FieldTypeType(FieldType type) {
        this.type = type;
        this.clz = null;
    }

    public FieldTypeType(Class clz) {
        this(null, clz);
    }

    public FieldTypeType(String clz) {
        this.type = null;
        this.clz = clz;
    }

    public FieldTypeType(FieldType type, Class clz) {
        this.type = type;
        if (clz == null) {
            this.clz = null;
        } else {
            this.clz = clz.getName();
        }
    }

    public FieldTypeType(FieldType type, String clz) {
        this.type = type;
        this.clz = clz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof FieldTypeType)) return false;

        FieldTypeType that = (FieldTypeType) o;

        return new EqualsBuilder()
                .append(type, that.type)
                .append(clz, that.clz)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(type)
                .append(clz)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "" + clz + ":" + type;
    }

}
