package com.taoswork.tallybook.testframework.database.derby;

import com.taoswork.tallybook.testframework.database.TestDataSourceCreator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public class DerbyTestDbCreator implements TestDataSourceCreator.ITestDbCreator {
    @Override
    public String hibernateSettingFile() {
        return "/test/test-use-derby.properties";
    }

    @Override
    public DataSource createDataSource(String dbName) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());

        dataSource.setUrl("jdbc:derby:memory:" + dbName + ";create=true");
        return dataSource;
    }

    @Override
    public Class<? extends Dialect> getDialectClass() {
        return org.hibernate.dialect.DerbyTenSevenDialect.class;
    }
}
