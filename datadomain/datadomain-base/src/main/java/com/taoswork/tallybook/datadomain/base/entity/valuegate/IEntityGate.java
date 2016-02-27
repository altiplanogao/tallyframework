package com.taoswork.tallybook.datadomain.base.entity.valuegate;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public interface IEntityGate {
    /**
     * Called before create, update
     *
     * @param entity,    entity to be saved,
     * @param oldEntity, the old entity reference, may contain some hidden data, ( For example: Person.uuid)
     *                   [used in update mode] ['null' for creation mode]
     */
    void store(Persistable entity, Persistable oldEntity);

    /**
     * Called before returning result to client.
     * Typically used to hide sensitive data
     *
     * @param entity
     */
    void fetch(Persistable entity);
}
