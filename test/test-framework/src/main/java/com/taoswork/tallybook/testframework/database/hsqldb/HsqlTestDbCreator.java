package com.taoswork.tallybook.testframework.database.hsqldb;

import com.taoswork.tallybook.testframework.database.TestDataSourceCreator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hsqldb.jdbc.JDBCDriver;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public class HsqlTestDbCreator implements TestDataSourceCreator.ITestDbCreator {
    @Override
    public String hibernateSettingFile() {
        return "/test/test-use-hsql.properties";
    }

    @Override
    public DataSource createDb(String dbName) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(JDBCDriver.class.getName());
        //dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");

        dataSource.setUrl("jdbc:hsqldb:mem:" + dbName);
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
}
