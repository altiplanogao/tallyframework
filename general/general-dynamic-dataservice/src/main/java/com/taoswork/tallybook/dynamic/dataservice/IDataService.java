package com.taoswork.tallybook.dynamic.dataservice;

import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.dynamic.dataservice.entity.EntityEntry;

import java.util.Map;

/**
 * See Definition in :
 * IDataServiceDefinition
 * ({@link com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition})
 *
 * Beans loaded to IDataService by DataServiceBase( ... annotatedConfigurationClasses)
 *
 * Configuration classes:
 * ADataServiceBeanConfiguration
 * ({@link com.taoswork.tallybook.dynamic.dataservice.config.ADataServiceBeanConfiguration})
 */
public interface IDataService {
    public static final String DATASERVICE_NAME_S_BEAN_NAME = "DataServiceBeanName";
    public static final String ERROR_MESSAGE_SOURCE_BEAN_NAME = "ErrorMessageSource";

    <T> T getService(String serviceName);

    <T> T getService(Class<T> clz, String serviceName);

    <T> T getService(Class<T> serviceCls);

    IDataServiceDefinition getDataServiceDefinition();

    /**
     * Type name to EntityEntry
     * @return
     */
    Map<String, EntityEntry> getEntityEntries();

    /**
     * Convert typeName to resourceName
     * com.tallybook.data.Person -> person
     * com.tallybook.data.Tool -> tool
     *
     * @param typeName, the result of type.getName()
     * @return the resource name
     */
    String getEntityResourceName(String typeName);

    /**
     * Convert resourceName to typeName
     * person -> com.tallybook.data.Person
     * tool -> com.tallybook.data.Tool
     *
     * @param resourceName, the resource name
     * @return the type name
     */
    String getEntityTypeName(String resourceName);

    void setSecurityVerifier(ISecurityVerifier securityVerifier);
}
