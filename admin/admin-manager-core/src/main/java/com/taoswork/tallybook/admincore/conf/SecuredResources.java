package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminGroup;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminProtection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/10/23.
 */
public class SecuredResources {
    private static final List<AdminProtection> resources;

    static {
        List<AdminProtection> tempResources = new ArrayList<AdminProtection>();
        tempResources.add(new AdminProtection(AdminProtection.class, ProtectionMode.FitAll, true));
        tempResources.add(new AdminProtection(AdminEmployee.class, ProtectionMode.FitAll, true));
        tempResources.add(new AdminProtection(AdminGroup.class, ProtectionMode.FitAll, true));
        resources = Collections.unmodifiableList(tempResources);
    }

    public static List<AdminProtection> getResources() {
        return resources;
    }
}
