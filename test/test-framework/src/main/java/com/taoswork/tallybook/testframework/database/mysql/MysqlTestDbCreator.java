package com.taoswork.tallybook.testframework.database.mysql;

import com.taoswork.tallybook.testframework.database.TestDataSourceCreator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public class MysqlTestDbCreator implements TestDataSourceCreator.ITestDbCreator {
    @Override
    public String hibernateSettingFile() {
        return "/test/test-use-mysql.properties";
    }

    @Override
    public DataSource createDataSource(String dbName) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(com.mysql.jdbc.Driver.class.getName());

        dataSource.setUrl("jdbc:mysql://localhost:3306/" + dbName);
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Override
    public Class<? extends Dialect> getDialectClass() {
        return org.hibernate.dialect.MySQL5InnoDBDialect.class;
    }
}
