package com.taoswork.tallybook.testframework.domain;

import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@PersistFriendly(permissionGuardian = TFish.class)
public interface TFish extends TAnimal {
}
