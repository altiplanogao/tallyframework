package com.taoswork.tallybook.datadomain.base.entity.valuegate;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;

public abstract class BaseEntityGate<T extends Persistable> implements IEntityGate {
    @Override
    public final void store(Persistable entity, Persistable reference) {
        this.doStore((T) entity, (T) reference);
    }

    @Override
    public final void fetch(Persistable entity) {
        this.doFetch((T) entity);
    }

    protected abstract void doStore(T entity, T oldEntity);

    protected abstract void doFetch(T entity);

}
