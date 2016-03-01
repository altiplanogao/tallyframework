package com.taoswork.tallybook.business.dataservice.tallymodule;

import com.taoswork.tallybook.dataservice.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyModuleDataServiceDefinition implements IDataServiceDefinition {
    //DataServiceName
    public final static String DATA_SERVICE_NAME = "TallyModuleDataService";

    //TMODULE_DSD : Data Source Definition
    public final static String TMODULE_DSD = "tallymodule";

    public static final String TMODULE_ENTITY_MESSAGES = ENTITY_MESSAGES_FILE_PREFIX +
            "tallymodule/";

    public static final String TMODULE_ERROR_MESSAGES = ERROR_MESSAGES_FILE_PREFIX +
            "tallymodule/";

    public static final String TMODULE_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "tallymodule/";

    @Override
    public String getDataServiceName() {
        return DATA_SERVICE_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return TMODULE_ENTITY_MESSAGES;
    }

    @Override
    public String getErrorMessageDirectory() {
        return TMODULE_ERROR_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TMODULE_RUNTIME_PROPERTIES;
    }

    @Override
    public Class[] getExtraConfig() {
        return new Class[0];
    }
}
