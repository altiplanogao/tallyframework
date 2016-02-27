package com.taoswork.tallybook.testmaterial.jpa.database.hsqldb;

import com.taoswork.tallybook.testmaterial.jpa.database.JpaTestDataSourceCreator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.dialect.Dialect;
import org.hsqldb.jdbc.JDBCDriver;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public class HsqlTestDbCreator implements JpaTestDataSourceCreator.ITestDbCreator {
    @Override
    public String hibernateSettingFile() {
        return "/test/test-use-hsql.properties";
    }

    @Override
    public DataSource createDataSource(String dbName) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(JDBCDriver.class.getName());
        //dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");

        dataSource.setUrl("jdbc:hsqldb:mem:" + dbName);
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Override
    public Class<? extends Dialect> getDialectClass() {
        return org.hibernate.dialect.HSQLDialect.class;
    }
}
