package com.taoswork.tallybook.datadomain.base.entity.valuecopier;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public abstract class BaseEntityCopier<T extends Persistable> implements IEntityCopier {
    @Override
    public final void copy(Object src, Object target) {
        doCopy((T) src, (T) target);
    }

    protected abstract void doCopy(T src, T target);
}
