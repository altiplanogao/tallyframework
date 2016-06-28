package com.taoswork.tallybook.business.datadomain.tallyadmin;

import com.taoswork.tallycheck.authority.solution.domain.resource.DProtectionMode;
import com.taoswork.tallycheck.authority.solution.domain.resource.Protection;
import com.taoswork.tallycheck.datadomain.base.entity.PersistEntity;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2016/3/1.
 */
@Entity("adminprotect")
@PersistEntity(value = "adminprotect", asDefaultPermissionGuardian = true)
public class AdminProtection extends Protection {
    public static final String COMMON_NAMESPACE = "admin-namespace";

    public AdminProtection() {
    }

    public AdminProtection(Class resource) {
        this(resource, DProtectionMode.FitAll, true);
    }

    public AdminProtection(Class resource, DProtectionMode mode, boolean masterControlled) {
        this.setProtectionSpace(AdminProtectionSpace.COMMON_SPACE_NAME);
        this.setNamespace(COMMON_NAMESPACE);
        this.setName(resource.getSimpleName());
        this.setResource(resource);
        this.setProtectionMode(mode);
        this.setMasterControlled(masterControlled);
    }
}
