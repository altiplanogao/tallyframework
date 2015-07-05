package com.taoswork.tallybook.general.dataservice.support.config.dbsetting;

import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/17.
 */
public interface IDbSetting {
    public final static IDbSetting DEFAULT_DB_SETTING = null;

    String hibernateDialect();

    DataSource publishDataSourceWithDefinition(IDataServiceDefinition dsDefine);
}
