package com.taoswork.tallybook.business.datadomain.tallyadmin;

import com.taoswork.tallybook.authority.solution.domain.user.GroupAuthority;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2016/2/29.
 */
@Entity("admingroup")
@PersistEntity(value = "admingroup",
        asDefaultPermissionGuardian = true)
public class AdminGroup extends GroupAuthority {
}
