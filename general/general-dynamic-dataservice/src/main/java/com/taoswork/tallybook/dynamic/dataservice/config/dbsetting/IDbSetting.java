package com.taoswork.tallybook.dynamic.dataservice.config.dbsetting;

import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition;
import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/17.
 */
public interface IDbSetting {
    public final static IDbSetting DEFAULT_DB_SETTING = null;

    Class<? extends Dialect> hibernateDialect();

    DataSource publishDataSourceWithDefinition(IDataServiceDefinition dsDefine);
}
