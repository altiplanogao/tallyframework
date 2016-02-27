package com.taoswork.tallybook.descriptor.metadata.classtree;

import com.taoswork.tallybook.descriptor.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.general.solution.threading.annotations.ThreadSafe;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
@ThreadSafe
public class EntityClass implements Serializable {
    public final Class<?> clz;
    protected final boolean instantiable;

    public EntityClass(Class<?> clz) {
        this.clz = clz;
        instantiable = NativeClassHelper.isInstantiable(clz);
    }

    public boolean isInstantiable() {
        return instantiable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EntityClass)) return false;

        EntityClass that = (EntityClass) o;

        return new EqualsBuilder()
                .append(instantiable, that.instantiable)
                .append(clz, that.clz)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(clz)
                .append(instantiable)
                .toHashCode();
    }

    @Override
    public String toString() {
        return clz.getSimpleName() + " instantiable:" + instantiable;
    }
}