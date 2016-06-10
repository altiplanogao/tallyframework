package com.taoswork.tallybook.business.dataservice.tallybusiness;

import com.taoswork.tallybook.dataservice.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TallyBusinessDataServiceDefinition implements IDataServiceDefinition {
    //DataServiceName
    public final static String DATA_SERVICE_NAME = "TallyBusinessDataService";

    //TBUSINESS_DSD : Data Source Definition
    public final static String TBUSINESS_DSD = "tallybusiness";


    public static final String TBUSINESS_ENTITY_MESSAGES = ENTITY_MESSAGES_FILE_PREFIX +
            "tallybusiness/";

    public static final String TBUSINESS_ERROR_MESSAGES = ERROR_MESSAGES_FILE_PREFIX +
            "tallybusiness/";

    public static final String TBUSINESS_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "tallybusiness/";

    @Override
    public String getDataServiceName() {
        return DATA_SERVICE_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return TBUSINESS_ENTITY_MESSAGES;
    }

    @Override
    public String getErrorMessageDirectory() {
        return TBUSINESS_ERROR_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TBUSINESS_RUNTIME_PROPERTIES;
    }

    @Override
    public Class[] getExtraConfig() {
        return new Class[0];
    }
}
