package com.taoswork.tallybook.dataservice.jpa.config.db.setting;

import com.taoswork.tallybook.dataservice.jpa.JpaDatasourceDefinition;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.hibernate.dialect.DerbyTenSevenDialect;
import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/17.
 */
public class DerbyDbSetting implements JpaDbSetting {
    @Override
    public Class<? extends Dialect> hibernateDialect() {
        return DerbyTenSevenDialect.class;
    }

    @Override
    public DataSource publishDataSourceWithDefinition(JpaDatasourceDefinition dsDefine) {
        String dbName = dsDefine.getDbName();
        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriver(new EmbeddedDriver());
        dataSource.setDriverClassName(EmbeddedDriver.class.getName());

        dataSource.setUrl("jdbc:derby:memory:" + dbName + ";create=true");
        return dataSource;
    }
}
