package com.taoswork.tallybook.general.datadomain.support.entity.valuegate;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

public abstract class EntityValueGateBase<T extends Persistable> implements IEntityValueGate {
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
