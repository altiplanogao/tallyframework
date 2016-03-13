package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.authority.solution.domain.user.GroupAuthority;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2016/2/29.
 */
@Entity("role")
@PersistEntity(value = "role",
        asDefaultPermissionGuardian = true)
public class Role extends GroupAuthority {
}
