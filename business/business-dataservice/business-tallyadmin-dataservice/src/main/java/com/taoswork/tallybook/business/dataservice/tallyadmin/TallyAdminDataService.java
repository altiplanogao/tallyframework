package com.taoswork.tallybook.business.dataservice.tallyadmin;

import com.taoswork.tallybook.business.dataservice.tallyadmin.conf.TallyAdminPersistenceConfig;
import com.taoswork.tallybook.business.dataservice.tallyadmin.conf.TallyAdminDataServiceConfig;
import com.taoswork.tallybook.general.authority.domain.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.authority.permission.PermissionEntry;
import com.taoswork.tallybook.general.authority.domain.authority.permission.Role;
import com.taoswork.tallybook.general.authority.domain.authority.resource.ResourceCriteria;
import com.taoswork.tallybook.general.authority.domain.authority.resource.ResourceType;
import com.taoswork.tallybook.general.dataservice.support.annotations.DataService;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
@DataService
//@Component(TallyAdminDataService.COMPONENT_NAME)
public class TallyAdminDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = "TallyAdminDataService";

    TallyAdminDataService(Class<?>... annotatedClasses) {
        super(annotatedClasses);
    }

    public TallyAdminDataService() {
        super( new Class<?>[]{
                TallyAdminPersistenceConfig.class,
                TallyAdminDataServiceConfig.class});
    }

    @Override
    protected void postConstruct() {
        super.postConstruct();
        super.setEntityResourceNameOverride(ResourceCriteria.class, "admin-resource-criteria");
        super.setEntityResourceNameOverride(ResourceType.class, "admin-resource-type");

        super.setEntityResourceNameOverride(PermissionEntry.class, "admin-permission-entry");
        super.setEntityResourceNameOverride(Permission.class, "admin-permission");
        super.setEntityResourceNameOverride(Role.class, "admin-role");

    }
}
