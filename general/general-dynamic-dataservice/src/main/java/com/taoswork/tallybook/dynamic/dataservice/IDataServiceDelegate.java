package com.taoswork.tallybook.dynamic.dataservice;

import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.IDbSetting;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public interface IDataServiceDelegate {
    IDataServiceDefinition getDataServiceDefinition();

    IDbSetting getDbSetting();

    IDataService theDataService();
}
