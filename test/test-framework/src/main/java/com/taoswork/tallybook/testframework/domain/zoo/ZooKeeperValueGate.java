package com.taoswork.tallybook.testframework.domain.zoo;

import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.EntityValueGateBase;

import java.util.UUID;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class ZooKeeperValueGate extends EntityValueGateBase<ZooKeeper> {
    @Override
    protected void doStore(ZooKeeper entity, ZooKeeper oldEntity) {
        if(oldEntity == null){
            entity.setUuid(UUID.randomUUID().toString());
        }else {
            entity.setUuid(oldEntity.getUuid());
        }
    }

    @Override
    protected void doFetch(ZooKeeper entity) {

    }
}
