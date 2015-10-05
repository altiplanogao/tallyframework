package com.taoswork.tallybook.general.datadomain.support.entity.valuegate;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

public abstract class EntityValueGateBase<T extends Persistable> implements IEntityValueGate {
    @Override
    public final void deposit(Persistable entity, Persistable reference) {
        this.doDeposit((T)entity, (T)reference);
    }

    @Override
    public final void withdraw(Persistable entity) {
        this.doWithdraw((T) entity);
    }

    protected abstract void doDeposit(T entity, T oldEntity);

    protected abstract void doWithdraw(T entity);

}
