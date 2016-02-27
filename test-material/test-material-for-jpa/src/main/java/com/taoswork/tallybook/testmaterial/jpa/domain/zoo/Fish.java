package com.taoswork.tallybook.testmaterial.jpa.domain.zoo;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;

/**
 * Created by Gao Yuan on 2015/10/2.
 */

@PersistEntity(permissionGuardian = Fish.class)
public interface Fish extends Animal {
}
