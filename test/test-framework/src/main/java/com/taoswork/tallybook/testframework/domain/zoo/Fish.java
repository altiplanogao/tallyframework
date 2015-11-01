package com.taoswork.tallybook.testframework.domain.zoo;

import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;

/**
 * Created by Gao Yuan on 2015/10/2.
 */

@PersistEntity(permissionGuardian = Fish.class)
public interface Fish extends Animal {
}
