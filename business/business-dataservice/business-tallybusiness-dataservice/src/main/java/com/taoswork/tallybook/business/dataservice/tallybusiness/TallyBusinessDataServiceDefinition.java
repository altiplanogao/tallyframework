package com.taoswork.tallybook.business.dataservice.tallybusiness;

import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyBusinessDataServiceDefinition  implements IDataServiceDefinition {
    //DataServiceName
    public final static String DATA_SERVICE_NAME = "TallyBusinessDataService";

    //TBUSINESS_DSD : Data Source Definition
    public final static String TBUSINESS_DSD = "tallybusiness";

    public final static String TBUSINESS_DB_NAME = "tallybusiness";
    //Prediction: must have JNDI enabled with name: jdbc/userdb
    public static final String TBUSINESS_JNDI_NAME = "jdbc/tallybusinessJndiDb";

    public static final String TBUSINESS_DATASOURCE_NAME = "tallybusinessDataSource";

    public static final String TBUSINESS_PERSISTENCE_XML = PERSISTENCE_XML_PREFIX + "persistence-tallybusiness.xml";
    //should be same as in persistence.xml
    public final static String TBUSINESS_PU_NAME = "tallybusinessPU";

    public static final String TBUSINESS_ENTITY_MANAGER_FACTORY_NAME = "tallybusinessEntityManagerFactory";

    public static final String TBUSINESS_TRANSACTION_MANAGER_NAME = "tallybusinessTransactionManager";

    public static final String TBUSINESS_ENTITY_MESSAGES = ENTITY_MESSAGES_FILE_PREFIX +
            "tallybusiness/";

    public static final String TBUSINESS_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "tallybusiness/";

    @Override
    public String getDataServiceName() {
        return DATA_SERVICE_NAME;
    }

    @Override
    public String getDbName() {
        return TBUSINESS_DB_NAME;
    }

    @Override
    public String getJndiDbName() {
        return TBUSINESS_JNDI_NAME;
    }

    @Override
    public String getDataSourceName() {
        return TBUSINESS_DATASOURCE_NAME;
    }

    @Override
    public String getPersistenceXml() {
        return TBUSINESS_PERSISTENCE_XML;
    }

    @Override
    public String getPersistenceUnit() {
        return TBUSINESS_PU_NAME;
    }

    @Override
    public String getEntityManagerName() {
        return TBUSINESS_ENTITY_MANAGER_FACTORY_NAME;
    }

    @Override
    public String getTransactionManagerName() {
        return TBUSINESS_TRANSACTION_MANAGER_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return TBUSINESS_ENTITY_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TBUSINESS_RUNTIME_PROPERTIES;
    }
}
