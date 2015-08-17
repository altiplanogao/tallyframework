package com.taoswork.tallybook.dynamic.dataservice.servicemockup;

import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition;
import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfig;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
public final class TallyMockupDataServiceDefinition implements IDataServiceDefinition {
    //DataServiceName
    public final static String DATA_SERVICE_NAME = "TallyMockupDataService";

    //TMOCKUP_DSD : Data Source Definition
    public final static String TMOCKUP_DSD = "tallymockup";

    public final static String TMOCKUP_DB_NAME = "tallymockup";
    //Prediction: must have JNDI enabled with name: jdbc/mockupdb
    public static final String TMOCKUP_JNDI_NAME = "jdbc/tallymockupJndiDb";

    public static final String TMOCKUP_DATASOURCE_NAME = "tallymockupDataSource";

    public static final String TMOCKUP_PERSISTENCE_XML = PERSISTENCE_XML_PREFIX + "persistence-test.xml";
    //should be same as in persistence.xml
    public final static String TMOCKUP_PU_NAME = //"tallymockupPU";
        TestDbPersistenceConfig.TEST_DB_PU_NAME;

    public static final String TMOCKUP_ENTITY_MANAGER_FACTORY_NAME = "tallymockupEntityManagerFactory";

    public static final String TMOCKUP_TRANSACTION_MANAGER_NAME = "tallymockupTransactionManager";

    public static final String TMOCKUP_ENTITY_MESSAGES = ENTITY_MESSAGES_FILE_PREFIX +
            "tallymockup/";

    public static final String TMOCKUP_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "tallymockup/";

    @Override
    public String getDataServiceName() {
        return DATA_SERVICE_NAME;
    }

    @Override
    public String getDbName() {
        return TMOCKUP_DB_NAME;
    }

    @Override
    public String getJndiDbName() {
        return TMOCKUP_JNDI_NAME;
    }

    @Override
    public String getDataSourceName(){
        return TMOCKUP_DATASOURCE_NAME;
    }

    @Override
    public String getPersistenceXml() {
        return TMOCKUP_PERSISTENCE_XML;
    }

    @Override
    public String getPersistenceUnit() {
        return TMOCKUP_PU_NAME;
    }

    @Override
    public String getEntityManagerName() {
        return TMOCKUP_ENTITY_MANAGER_FACTORY_NAME;
    }

    @Override
    public String getTransactionManagerName() {
        return TMOCKUP_TRANSACTION_MANAGER_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return TMOCKUP_ENTITY_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TMOCKUP_RUNTIME_PROPERTIES;
    }
}
