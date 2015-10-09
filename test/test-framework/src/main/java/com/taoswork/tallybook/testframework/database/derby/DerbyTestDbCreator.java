package com.taoswork.tallybook.testframework.database.derby;

import com.taoswork.tallybook.testframework.database.TestDataSourceCreator;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public class DerbyTestDbCreator implements TestDataSourceCreator.ITestDbCreator {
    @Override
    public String hibernateSettingFile() {
        return "/test/test-use-derby.properties";
    }

    @Override
    public DataSource createDb(String dbName) {
        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(.class.getName());
//        //dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
//
//        dataSource.setUrl("jdbc:hsqldb:mem:" + dbName);
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
        return dataSource;
    }
}
