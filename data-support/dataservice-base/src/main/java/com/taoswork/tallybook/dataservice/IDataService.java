package com.taoswork.tallybook.dataservice;

import com.taoswork.tallybook.dataservice.core.entity.EntityCatalog;
import com.taoswork.tallybook.dataservice.security.ISecurityVerifier;

import java.util.Map;

/**
 * See Definition in :
 * JPADataServiceDefinition
 * ({@link JPADataServiceDefinition})
 *
 * Beans loaded to IDataService by DataServiceBase( ... annotatedConfigurationClasses)
 *
 * Configuration classes:
 * ADataServiceBeanConfiguration
 * ({@link ADataServiceBeanConfiguration})
 */
public interface IDataService {
    public static final String DATASERVICE_NAME_S_BEAN_NAME = "DataServiceBeanName";
    public static final String ERROR_MESSAGE_SOURCE_BEAN_NAME = "ErrorMessageSource";
    public static final String PROPERTY_CONFIGURER = "PROPERTY_CONFIGURER";

    <T> T getService(String serviceName);

    <T> T getService(Class<T> clz, String serviceName);

    <T> T getService(Class<T> serviceCls);

    IDataServiceDefinition getDataServiceDefinition();

    /**
     * Type name to EntityCatalog
     * @return
     */
    Map<String, EntityCatalog> getEntityCatalogs();

    /**
     * Convert typeName to resource
     * com.tallybook.data.Person -> person
     * com.tallybook.data.Tool -> tool
     *
     * @param typeName, the result of type.getName()
     * @return the resource name
     */
    String getEntityResourceName(String typeName);

    /**
     * Convert resource to typeName
     * person -> com.tallybook.data.Person
     * tool -> com.tallybook.data.Tool
     *
     * @param resourceName, the resource name
     * @return the type name
     */
    String getEntityTypeName(String resourceName);

    void setSecurityVerifier(ISecurityVerifier securityVerifier);

}
