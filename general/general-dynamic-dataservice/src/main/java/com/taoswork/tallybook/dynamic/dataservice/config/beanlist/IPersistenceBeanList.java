package com.taoswork.tallybook.dynamic.dataservice.config.beanlist;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.OpenSessionAop;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManager;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManagerFactory;
import com.taoswork.tallybook.dynamic.dataservice.core.persistence.PersistenceManagerInvoker;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface IPersistenceBeanList {
    DataSource serviceDataSource();

    AbstractEntityManagerFactoryBean entityManagerFactory();

    JpaTransactionManager jpaTransactionManager();

    PersistenceManagerFactory persistenceManagerFactory();

    PersistenceManager persistenceManager();

    PersistenceManagerInvoker persistenceManagerInvoker();

    OpenSessionAop openSessionAop();
}
