package com.taoswork.tallybook.testframework.database;

import com.taoswork.tallybook.testframework.database.hsqldb.HsqlTestDbCreator;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public class TestDataSourceCreator {
    public static interface ITestDbCreator{
        DataSource createDb(String dbName);
        String hibernateSettingFile();
    }
    private static ITestDbCreator dbCreator = new HsqlTestDbCreator();

    public static DataSource createDataSource(String dbName){
        return dbCreator.createDb(dbName);
    }

    public static String getHibernateSettingFile(){
        return dbCreator.hibernateSettingFile();
    }
}
