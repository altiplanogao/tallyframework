package com.taoswork.tallybook.business.dataservice.tallymodule;

import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyModuleDataServiceDefinition implements IDataServiceDefinition {
    //DataServiceName
    public final static String DATA_SERVICE_NAME = "TallyModuleDataService";

    //TMODULE_DSD : Data Source Definition
    public final static String TMODULE_DSD = "tallymodule";

    public final static String TMODULE_DB_NAME = "tallymodule";
    //Prediction: must have JNDI enabled with name: jdbc/userdb
    public static final String TMODULE_JNDI_NAME = "jdbc/tallymoduleJndiDb";

    public static final String TMODULE_DATASOURCE_NAME = "tallymoduleDataSource";

    public static final String TMODULE_PERSISTENCE_XML = PERSISTENCE_XML_PREFIX + "persistence-tallymodule.xml";
    //should be same as in persistence.xml
    public final static String TMODULE_PU_NAME = "tallymodulePU";

    public static final String TMODULE_ENTITY_MANAGER_FACTORY_NAME = "tallymoduleEntityManagerFactory";

    public static final String TMODULE_TRANSACTION_MANAGER_NAME = "tallymoduleTransactionManager";

    public static final String TMODULE_ENTITY_MESSAGES = ENTITY_MESSAGES_FILE_PREFIX +
            "tallymodule/";

    public static final String TMODULE_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "tallymodule/";

    @Override
    public String getDataServiceName() {
        return DATA_SERVICE_NAME;
    }

    @Override
    public String getDbName() {
        return TMODULE_DB_NAME;
    }

    @Override
    public String getJndiDbName() {
        return TMODULE_JNDI_NAME;
    }

    @Override
    public String getDataSourceName() {
        return TMODULE_DATASOURCE_NAME;
    }

    @Override
    public String getPersistenceXml() {
        return TMODULE_PERSISTENCE_XML;
    }

    @Override
    public String getPersistenceUnit() {
        return TMODULE_PU_NAME;
    }

    @Override
    public String getEntityManagerName() {
        return TMODULE_ENTITY_MANAGER_FACTORY_NAME;
    }

    @Override
    public String getTransactionManagerName() {
        return TMODULE_TRANSACTION_MANAGER_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return TMODULE_ENTITY_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TMODULE_RUNTIME_PROPERTIES;
    }
}
