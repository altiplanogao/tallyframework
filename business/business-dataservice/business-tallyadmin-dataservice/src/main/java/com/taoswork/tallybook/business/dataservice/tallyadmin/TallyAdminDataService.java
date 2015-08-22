package com.taoswork.tallybook.business.dataservice.tallyadmin;

import com.taoswork.tallybook.business.dataservice.tallyadmin.conf.TallyAdminDataServiceBeanConfiguration;
import com.taoswork.tallybook.dynamic.dataservice.impl.DataServiceBase;
import com.taoswork.tallybook.general.authority.core.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.core.authority.permission.PermissionEntry;
import com.taoswork.tallybook.general.authority.core.authority.permission.Role;
import com.taoswork.tallybook.general.authority.core.authority.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.authority.core.authority.resource.SecuredResource;
import com.taoswork.tallybook.general.dataservice.support.annotations.DataService;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
@DataService
//@Component(TallyAdminDataService.COMPONENT_NAME)
public class TallyAdminDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = TallyAdminDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyAdminDataService() {
        this(TallyAdminDataServiceBeanConfiguration.class, null);
    }

    public TallyAdminDataService(
            Class<? extends TallyAdminDataServiceBeanConfiguration> dataServiceConf,
            List<Class> annotatedClasses) {
        super(new TallyAdminDataServiceDefinition(), dataServiceConf, annotatedClasses);
    }

    @Override
    protected void postConstruct() {
        super.postConstruct();
        super.setEntityResourceNameOverride(SecuredResourceFilter.class, "admin-resource-criteria");
        super.setEntityResourceNameOverride(SecuredResource.class, "admin-resource-type");

        super.setEntityResourceNameOverride(PermissionEntry.class, "admin-permission-entry");
        super.setEntityResourceNameOverride(Permission.class, "admin-permission");
        super.setEntityResourceNameOverride(Role.class, "admin-role");

    }
}
