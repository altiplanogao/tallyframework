package com.taoswork.tallybook.general.dataservice.management.manager;

import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public interface DataServiceManager {
    public static final String COMPONENT_NAME = "DataServiceManager";

    DataServiceManager buildingAppendDataService(String dataServiceBeanName, IDataService dataService);

    DataServiceManager buildingAnnounceFinishing();

    ManagedEntityEntry getInterfaceEntityEntry(String entityClz);

    String getEntityInterfaceName(String friendlyName);

    String getEntityResourceName(String entityClz);

    IDataService getDataService(String entityClz);

    DynamicEntityService getDynamicEntityService(String entityClz);

//    FrontEndEntityService getFrontEndDynamicEntityService(String entityClz);
}
