package com.taoswork.tallybook.business.datadomain.tallyadmin;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
public class TallyAdminDataDomain {
    public static Class<?>[] persistableEntities() {
        return new Class<?>[]{
                AdminProtectionSpace.class,
                AdminProtection.class,
                AdminEmployee.class,
                AdminGroup.class,
        };
    }

}
