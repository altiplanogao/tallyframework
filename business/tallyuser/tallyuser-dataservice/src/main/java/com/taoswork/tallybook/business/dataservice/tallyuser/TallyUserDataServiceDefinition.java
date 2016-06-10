package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.dataservice.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public class TallyUserDataServiceDefinition implements IDataServiceDefinition {
    //DataServiceName
    public final static String DATA_SERVICE_NAME = "TallyUserDataService";

    //TUSER_DSD : Data Source Definition
    public final static String TUSER_DSD = "tallyuser";

    public static final String TUSER_ENTITY_MESSAGES = ENTITY_MESSAGES_FILE_PREFIX +
            "tallyuser/";

    public static final String TUSER_ERROR_MESSAGES = ERROR_MESSAGES_FILE_PREFIX +
            "tallyuser/";

    public static final String TUSER_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "tallyuser/";

    @Override
    public String getDataServiceName() {
        return DATA_SERVICE_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return TUSER_ENTITY_MESSAGES;
    }

    @Override
    public String getErrorMessageDirectory() {
        return TUSER_ERROR_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TUSER_RUNTIME_PROPERTIES;
    }

    @Override
    public Class[] getExtraConfig() {
        return new Class[]{};
    }
}
