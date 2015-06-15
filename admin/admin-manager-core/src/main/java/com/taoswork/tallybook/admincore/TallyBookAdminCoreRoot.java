package com.taoswork.tallybook.admincore;

/**
 * Created by Gao Yuan on 2015/4/24.
 */
public class TallyBookAdminCoreRoot {

    /*
    public static void initEntityTypes(){
    EntityInterfaceManager.instance()
            .registEntity(AdminPermission.class, AdminPermissionImpl.class)
            .registEntity(AdminRole.class, AdminRoleImpl.class)
            .registEntity(AdminPermissionQualifiedEntity.class, AdminPermissionQualifiedEntityImpl.class)
            .registEntity(AdminUser.class, AdminUserImpl.class)
            .registEntity(UserCertification.class, UserCertificationImpl.class);

    }
    */

    public static String[] getMessageBasenames(){
        return new String[]{
          "classpath:/messages/MenuMessages"
        };
    }
}
