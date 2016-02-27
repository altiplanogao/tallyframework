package com.taoswork.tallybook.testmaterial.jpa.domain.zoo;

import com.taoswork.tallybook.datadomain.base.entity.valuegate.BaseEntityGate;

import java.util.UUID;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class ZooKeeperGate extends BaseEntityGate<ZooKeeper> {
    @Override
    protected void doStore(ZooKeeper entity, ZooKeeper oldEntity) {
        if (oldEntity == null) {
            entity.setUuid(UUID.randomUUID().toString());
        } else {
            entity.setUuid(oldEntity.getUuid());
        }
    }

    @Override
    protected void doFetch(ZooKeeper entity) {

    }
}
