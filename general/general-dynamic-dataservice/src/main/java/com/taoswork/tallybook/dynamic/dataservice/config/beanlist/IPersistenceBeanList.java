package com.taoswork.tallybook.dynamic.dataservice.config.beanlist;

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

}
