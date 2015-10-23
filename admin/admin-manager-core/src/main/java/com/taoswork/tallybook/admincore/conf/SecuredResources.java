package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermission;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminSecuredResource;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminSecuredResourceFilter;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/10/23.
 */
public class SecuredResources {
    private static final List<SecuredResource> resources;

    static {
        List<SecuredResource> tempResources = new ArrayList<SecuredResource>();
        tempResources.add(new SecuredResource(AdminSecuredResource.class, "", ProtectionMode.FitAll, true));
        tempResources.add(new SecuredResource(AdminSecuredResourceFilter.class, "", ProtectionMode.FitAll, true));
        tempResources.add(new SecuredResource(AdminEmployee.class, "", ProtectionMode.FitAll, true));
        tempResources.add(new SecuredResource(AdminPermission.class, "", ProtectionMode.FitAll, true));
        resources = Collections.unmodifiableList(tempResources);
    }

    public static List<SecuredResource> getResources() {
        return resources;
    }
}
