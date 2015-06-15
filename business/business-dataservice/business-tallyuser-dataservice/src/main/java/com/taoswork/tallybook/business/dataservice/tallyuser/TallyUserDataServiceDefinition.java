package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
public final class TallyUserDataServiceDefinition implements IDataServiceDefinition {
    //TUSER_DSD : Data Source Definition
    public final static String TUSER_DSD = "tallyuser";

    public final static String TUSER_DB_NAME = "tallyuser";
    //Prediction: must have JNDI enabled with name: jdbc/userdb
    public static final String TUSER_JNDI_NAME = "jdbc/tallyuserJndiDb";

    public static final String TUSER_DATASOURCE_NAME = "tallyuserDataSource";

    public static final String TUSER_PERSISTENCE_XML = PERSISTENCE_XML_PREFIX + "persistence-tallyuser.xml";
    //should be same as in persistence.xml
    public final static String TUSER_PU_NAME = "tallyuserPU";

    public static final String TUSER_ENTITY_MANAGER_FACTORY_NAME = "tallyuserEntityManagerFactory";

    public static final String TUSER_TRANSACTION_MANAGER_NAME = "tallyuserTransactionManager";

    public static final String TUSER_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "tallyuser-ds/";

    @Override
    public String getDbName() {
        return TUSER_DB_NAME;
    }

    @Override
    public String getJndiDbName() {
        return TUSER_JNDI_NAME;
    }

    @Override
    public String getDataSourceName(){
        return TUSER_DATASOURCE_NAME;
    }

    @Override
    public String getPersistenceXml() {
        return TUSER_PERSISTENCE_XML;
    }

    @Override
    public String getPersistenceUnit() {
        return TUSER_PU_NAME;
    }

    @Override
    public String getEntityManagerName() {
        return TUSER_ENTITY_MANAGER_FACTORY_NAME;
    }

    @Override
    public String getTransactionManagerName() {
        return TUSER_TRANSACTION_MANAGER_NAME;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TUSER_RUNTIME_PROPERTIES;
    }
}
