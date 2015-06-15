package com.taoswork.tallybook.general.dataservice.support;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public interface IDataServiceDefinition {
    public static final String DATA_SERVICE_DEFINITION_BEAN_NAME = "IDataServiceDefinition";
    public static final String PERSISTENCE_XML_PREFIX = "classpath:/META-INF/persistence/";
    public static final String RUNTIME_PROPERTIES_FILE_PREFIX = "/runtime-properties/";

    String getDbName();

    String getJndiDbName();

    String getDataSourceName();

    String getPersistenceXml();

    String getPersistenceUnit();

    String getEntityManagerName();

    String getTransactionManagerName();

    String getPropertiesResourceDirectory();

    /* CODE TEMPLATE AS FOLLOWING */

    /* END OF TEMPLATE*/
}
