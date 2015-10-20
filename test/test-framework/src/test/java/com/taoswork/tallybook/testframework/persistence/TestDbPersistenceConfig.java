package com.taoswork.tallybook.testframework.persistence;

import com.taoswork.tallybook.general.solution.spring.BeanCreationMonitor;
import com.taoswork.tallybook.testframework.database.TestDataSourceCreator;
import com.taoswork.tallybook.testframework.database.derby.DerbyTestDbCreator;
import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfigBase;
import org.hibernate.dialect.Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Gao Yuan on 2015/5/29.
 */

@Configuration
public class TestDbPersistenceConfig extends TestDbPersistenceConfigBase {
    public static final String TEST_DB_PU_NAME = "tallymockupPU";

    @Override
    public String getPersistenceXml() {
        return "persistence-test.xml";
    }

    @Override
    public String getDataSourceName() {
        return "test_mockup";
    }

    @Override
    public String getPuName() {
        return TEST_DB_PU_NAME;
    }
}
