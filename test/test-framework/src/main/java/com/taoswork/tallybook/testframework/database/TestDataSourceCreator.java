package com.taoswork.tallybook.testframework.database;

import com.taoswork.tallybook.testframework.database.hsqldb.HsqlTestDbCreator;
import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public class TestDataSourceCreator {
    public static interface ITestDbCreator{
        DataSource createDataSource(String dbName);

        String hibernateSettingFile();

        Class<? extends Dialect> getDialectClass();
    }

    private static ITestDbCreator dbCreator = new HsqlTestDbCreator();

    public static DataSource createDataSource(String dbName){
        return dbCreator.createDataSource(dbName);
    }

    public static String getHibernateSettingFile(){
        return dbCreator.hibernateSettingFile();
    }
}
