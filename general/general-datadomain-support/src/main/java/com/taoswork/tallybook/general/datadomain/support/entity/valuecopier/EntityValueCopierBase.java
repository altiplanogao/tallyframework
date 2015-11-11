package com.taoswork.tallybook.general.datadomain.support.entity.valuecopier;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public abstract class EntityValueCopierBase<T extends Persistable> implements IEntityValueCopier {
    @Override
    public final void copy(Object src, Object target) {
        doCopy((T)src, (T)target);
    }

    protected abstract void doCopy(T src, T target);
}
