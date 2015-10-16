package com.taoswork.tallybook.dynamic.dataservice.config.dbsetting;

import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/17.
 */
public class MysqlDbSetting implements IDbSetting {
    @Override
    public Class<?extends Dialect> hibernateDialect() {
        return MySQL5InnoDBDialect.class;
    }

    @Override
    public DataSource publishDataSourceWithDefinition(IDataServiceDefinition dsDefine){
        return new JndiDataSourceLookup().getDataSource(
                dsDefine.getJndiDbName());
    }
}
