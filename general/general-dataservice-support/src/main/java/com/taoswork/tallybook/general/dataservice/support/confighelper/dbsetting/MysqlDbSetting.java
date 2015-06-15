package com.taoswork.tallybook.general.dataservice.support.confighelper.dbsetting;

import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/17.
 */
public class MysqlDbSetting implements IDbSetting {
    @Override
    public String hibernateDialect() {
        return MySQL5InnoDBDialect.class.getName();
    }

    @Override
    public DataSource publishDataSourceWithDefinition(IDataServiceDefinition dsDefine){
        return new JndiDataSourceLookup().getDataSource(
                dsDefine.getJndiDbName());
    }
}
