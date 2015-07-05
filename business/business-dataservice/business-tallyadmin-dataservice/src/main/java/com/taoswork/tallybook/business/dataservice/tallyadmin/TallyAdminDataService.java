package com.taoswork.tallybook.business.dataservice.tallyadmin;

import com.taoswork.tallybook.business.dataservice.tallyadmin.conf.TallyAdminPersistenceConfig;
import com.taoswork.tallybook.business.dataservice.tallyadmin.conf.TallyAdminDataServiceConfig;
import com.taoswork.tallybook.business.dataservice.tallyadmin.conf.TallyAdminPersistenceConfigBase;
import com.taoswork.tallybook.general.authority.domain.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.authority.permission.PermissionEntry;
import com.taoswork.tallybook.general.authority.domain.authority.permission.Role;
import com.taoswork.tallybook.general.authority.domain.authority.resource.ResourceCriteria;
import com.taoswork.tallybook.general.authority.domain.authority.resource.ResourceType;
import com.taoswork.tallybook.general.dataservice.support.annotations.DataService;
import com.taoswork.tallybook.general.dataservice.support.config.DataServiceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.config.PersistenceConfigBase;
import com.taoswork.tallybook.general.dataservice.support.impl.DataServiceBase;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
@DataService
//@Component(TallyAdminDataService.COMPONENT_NAME)
public class TallyAdminDataService extends DataServiceBase {
    public static final String COMPONENT_NAME = "TallyAdminDataService";

    public TallyAdminDataService() {
        this(TallyAdminDataServiceConfig.class,
                TallyAdminPersistenceConfig.class, null);
    }

    public TallyAdminDataService(
            Class<? extends TallyAdminDataServiceConfig> dataServiceConf,
            Class<? extends TallyAdminPersistenceConfigBase> persistenceConf,
            List<Class> annotatedClasses) {
        super(dataServiceConf, persistenceConf, annotatedClasses);
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
