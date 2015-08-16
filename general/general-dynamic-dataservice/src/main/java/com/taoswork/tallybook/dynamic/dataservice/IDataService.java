package com.taoswork.tallybook.dynamic.dataservice;

import com.taoswork.tallybook.dynamic.dataservice.entity.EntityEntry;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/11.
 */
public interface IDataService {
    public static final String DATASERVICE_NAME_S_BEAN_NAME = "DataServiceBeanName";

    <T> T getService(String serviceName);

    <T> T getService(Class<T> clz, String serviceName);

    <T> T getService(Class<T> serviceCls);

    IDataServiceDefinition getDataServiceDefinition();

    Map<String, EntityEntry> getEntityEntries();

    String getEntityResourceName(String interfaceName);

    String getEntityInterfaceName(String resourceName);
}
