package com.taoswork.tallybook.testmaterial.jpa.database;

import com.taoswork.tallybook.testmaterial.jpa.database.derby.DerbyTestDbCreator;
import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public class JpaTestDataSourceCreator {
    public static interface ITestDbCreator {
        DataSource createDataSource(String dbName);

        String hibernateSettingFile();

        Class<? extends Dialect> getDialectClass();
    }

    private static ITestDbCreator dbCreator = new DerbyTestDbCreator();

    public static DataSource createDataSource(String dbName) {
        return dbCreator.createDataSource(dbName);
    }

    public static String getHibernateSettingFile() {
        return dbCreator.hibernateSettingFile();
    }
}
