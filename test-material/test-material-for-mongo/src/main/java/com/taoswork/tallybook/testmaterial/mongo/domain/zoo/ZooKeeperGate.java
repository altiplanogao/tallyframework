package com.taoswork.tallybook.testmaterial.mongo.domain.zoo;

import com.taoswork.tallybook.datadomain.base.entity.valuegate.BaseEntityGate;
import com.taoswork.tallybook.testmaterial.mongo.domain.zoo.impl.ZooKeeperImpl;

import java.util.UUID;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class ZooKeeperGate extends BaseEntityGate<ZooKeeperImpl> {
    @Override
    protected void doStore(ZooKeeperImpl entity, ZooKeeperImpl oldEntity) {
        if (oldEntity == null) {
            entity.setUuid(UUID.randomUUID().toString());
        } else {
            entity.setUuid(oldEntity.getUuid());
        }
    }

    @Override
    protected void doFetch(ZooKeeperImpl entity) {

    }
}
