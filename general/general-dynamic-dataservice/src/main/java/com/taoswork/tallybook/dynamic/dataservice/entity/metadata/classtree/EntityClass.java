package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.utils.NativeClassHelper;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class EntityClass {
    public final Class<?> clz;
    protected final boolean polymorphism;

    public boolean isPolymorphism() {
        return polymorphism;
    }

    public EntityClass(Class<?> clz) {
        this.clz = clz;
        polymorphism = !NativeClassHelper.isExcludeClassFromPolymorphism(clz);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityClass that = (EntityClass) o;

        if (polymorphism != that.polymorphism) return false;
        return !(clz != null ? !clz.equals(that.clz) : that.clz != null);

    }

    @Override
    public int hashCode() {
        int result = clz != null ? clz.hashCode() : 0;
        result = 31 * result + (polymorphism ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return clz.getSimpleName();
    }
}