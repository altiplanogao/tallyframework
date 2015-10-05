package com.taoswork.tallybook.general.datadomain.support.entity.valuegate;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public interface IEntityValueGate {
    /**
     * Called before create, update
     * @param entity, entity to be saved,
     * @param oldEntity, the old entity reference, may contain some hidden data, ( For example: Person.uuid)
     *                    [used in update mode] ['null' for creation mode]
     *
     */
    void deposit(Persistable entity, Persistable oldEntity);

    /**
     * Called before returning result to client.
     * Typically used to hide sensitive data
     * @param entity
     */
    void withdraw(Persistable entity);
}
