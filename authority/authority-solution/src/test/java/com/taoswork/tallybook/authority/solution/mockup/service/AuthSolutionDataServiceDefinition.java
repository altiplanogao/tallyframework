package com.taoswork.tallybook.authority.solution.mockup.service;

import com.taoswork.tallybook.dataservice.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
public class AuthSolutionDataServiceDefinition implements IDataServiceDefinition {
    //DataServiceName
    public final static String DATA_SERVICE_NAME = "AuthSolutionMockup";

    public static final String TMOCKUP_ENTITY_MESSAGES = ENTITY_MESSAGES_FILE_PREFIX +
            "permission/";

    public static final String TMOCKUP_ERROR_MESSAGES = ERROR_MESSAGES_FILE_PREFIX +
            "permission/";

    public static final String TMOCKUP_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "permission/";

    @Override
    public String getDataServiceName() {
        return DATA_SERVICE_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return TMOCKUP_ENTITY_MESSAGES;
    }

    @Override
    public String getErrorMessageDirectory() {
        return TMOCKUP_ERROR_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TMOCKUP_RUNTIME_PROPERTIES;
    }

    @Override
    public Class[] getExtraConfig() {
        return new Class[]{};
    }

}
