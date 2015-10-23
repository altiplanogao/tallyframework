package com.taoswork.tallybook.dynamic.dataservice.manage;

import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public interface DataServiceManager {
    public static final String COMPONENT_NAME = "DataServiceManager";

    void doInitialize();

    DataServiceManager buildingAppendDataService(String dataServiceBeanName, IDataService dataService);

    DataServiceManager buildingAnnounceFinishing();

    ManagedEntityEntry getInterfaceEntityEntry(String entityClz);

    String getEntityInterfaceName(String friendlyName);

    String getEntityResourceName(String entityClz);

    IDataService getDataServiceByServiceName(String serviceName);

    IDataService getDataService(String entityClz);

    DynamicEntityService getDynamicEntityService(String entityClz);

//    FrontEndEntityService getFrontEndDynamicEntityService(String entityClz);
}
