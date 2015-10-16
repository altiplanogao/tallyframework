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

        try {
            File tempFile = File.createTempFile("derby", ".dby.db");
            tempFile.deleteOnExit();
            //????
            //????
            //????
            //????
            //????
            //????

        } catch (IOException e) {
            return null;
        }
//        dataSource.setDriverClassName(.class.getName());
//        //dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
//
//        dataSource.setUrl("jdbc:hsqldb:mem:" + dbName);
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
        return dataSource;
    }

    @Override
    public Class<? extends Dialect> getDialectClass() {
        return org.hibernate.dialect.DerbyDialect.class;
    }
}
