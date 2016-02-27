package com.taoswork.tallybook.business.dataservice.tallyadmin.conf;

import com.taoswork.tallybook.dataservice.mongo.MongoDatasourceDefinitionBase;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public class TallyAdminDatasourceDefinition
        extends MongoDatasourceDefinitionBase {

    @Override
    public String getDbName() {
        return "tallyadmin";
    }

}
