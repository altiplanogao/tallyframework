package com.taoswork.tallybook.business.dataservice.tallymanagement;

import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyManagementDataServiceDefinition  implements IDataServiceDefinition {
    //DataServiceName
    public final static String DATA_SERVICE_NAME = "TallyManagementDataService";

    //TMANAGEMENT_DSD : Data Source Definition
    public final static String TMANAGEMENT_DSD = "tallymanagement";

    public final static String TMANAGEMENT_DB_NAME = "tallymanagement";
    //Prediction: must have JNDI enabled with name: jdbc/userdb
    public static final String TMANAGEMENT_JNDI_NAME = "jdbc/tallymanagementJndiDb";

    public static final String TMANAGEMENT_DATASOURCE_NAME = "tallymanagementDataSource";

    public static final String TMANAGEMENT_PERSISTENCE_XML = PERSISTENCE_XML_PREFIX + "persistence-tallymanagement.xml";
    //should be same as in persistence.xml
    public final static String TMANAGEMENT_PU_NAME = "tallymanagementPU";

    public static final String TMANAGEMENT_ENTITY_MANAGER_FACTORY_NAME = "tallymanagementEntityManagerFactory";

    public static final String TMANAGEMENT_TRANSACTION_MANAGER_NAME = "tallymanagementTransactionManager";

    public static final String TMANAGEMENT_ENTITY_MESSAGES = ENTITY_MESSAGES_FILE_PREFIX +
            "tallymanagement/";

    public static final String TMANAGEMENT_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "tallymanagement/";

    @Override
    public String getDataServiceName() {
        return DATA_SERVICE_NAME;
    }

    @Override
    public String getDbName() {
        return TMANAGEMENT_DB_NAME;
    }

    @Override
    public String getJndiDbName() {
        return TMANAGEMENT_JNDI_NAME;
    }

    @Override
    public String getDataSourceName() {
        return TMANAGEMENT_DATASOURCE_NAME;
    }

    @Override
    public String getPersistenceXml() {
        return TMANAGEMENT_PERSISTENCE_XML;
    }

    @Override
    public String getPersistenceUnit() {
        return TMANAGEMENT_PU_NAME;
    }

    @Override
    public String getEntityManagerName() {
        return TMANAGEMENT_ENTITY_MANAGER_FACTORY_NAME;
    }

    @Override
    public String getTransactionManagerName() {
        return TMANAGEMENT_TRANSACTION_MANAGER_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return TMANAGEMENT_ENTITY_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TMANAGEMENT_RUNTIME_PROPERTIES;
    }
}
