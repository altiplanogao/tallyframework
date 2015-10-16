package com.taoswork.tallybook.testframework.domain.zoo;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
public interface ZooKeeper extends Persistable {
    Long getId();

    ZooKeeper setId(Long id);

    String getName();

    ZooKeeper setName(String name);

    String getUuid();

    ZooKeeper setUuid(String uuid);

    String getEmail();

    ZooKeeper setEmail(String email);

    String getMobile();

    ZooKeeper setMobile(String mobile);
}
