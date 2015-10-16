package com.taoswork.tallybook.dynamic.dataservice.config.dbsetting;

import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.HSQLDialect;
import org.hsqldb.jdbc.JDBCDriver;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/17.
 */
public class HsqlDbSetting implements IDbSetting {
    @Override
    public Class<? extends Dialect> hibernateDialect() {
        return HSQLDialect.class;
    }

    @Override
    public DataSource publishDataSourceWithDefinition(IDataServiceDefinition dsDefine) {
        //Example Url:   jdbc:hsqldb:hsql://localhost/broadleaf
        String dbName = dsDefine.getDbName();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(JDBCDriver.class.getName());

        //dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");

//        dataSource.setUrl("jdbc:hsqldb:mem:" + dbName);
//        dataSource.setUrl("jdbc:hsqldb:hsql://localhost/" + dbName);
        dataSource.setUrl("jdbc:hsqldb:mem:" + dbName);

        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
