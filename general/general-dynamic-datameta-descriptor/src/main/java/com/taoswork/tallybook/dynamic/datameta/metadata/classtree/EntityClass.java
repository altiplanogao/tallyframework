package com.taoswork.tallybook.dynamic.datameta.metadata.classtree;

import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class EntityClass implements Serializable {
    public final Class<?> clz;
    protected final boolean instanceable;

    public boolean isInstanceable(){return instanceable;}

    public EntityClass(Class<?> clz) {
        this.clz = clz;
        instanceable = NativeClassHelper.isInstanceable(clz);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EntityClass)) return false;

        EntityClass that = (EntityClass) o;

        return new EqualsBuilder()
                .append(instanceable, that.instanceable)
                .append(clz, that.clz)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(clz)
                .append(instanceable)
                .toHashCode();
    }

    @Override
    public String toString() {
        return clz.getSimpleName();
    }
}