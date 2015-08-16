package com.taoswork.tallybook.dynamic.dataservice;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public interface IDataServiceDefinition {
    public static final String DATA_SERVICE_DEFINITION_BEAN_NAME = "IDataServiceDefinition";

    public static final String PERSISTENCE_XML_PREFIX = "classpath:/META-INF/persistence/";
    public static final String RUNTIME_PROPERTIES_FILE_PREFIX = "/runtime-properties/";
    public static final String ENTITY_MESSAGES_FILE_PREFIX = "/entity-messages/";
    public static final String ENTITY_MESSAGES_FILE_DELIMTER = ";";

    String getDataServiceName();

    String getDbName();

    String getJndiDbName();

    String getDataSourceName();

    String getPersistenceXml();

    String getPersistenceUnit();

    String getEntityManagerName();

    String getTransactionManagerName();

    String getEntityMessageDirectory();

    String getPropertiesResourceDirectory();

    /* CODE TEMPLATE AS FOLLOWING */

    /* END OF TEMPLATE*/
}
