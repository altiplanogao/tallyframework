package com.taoswork.tallybook.dataservice.jpa.config.beanlist;

import com.taoswork.tallybook.dataservice.jpa.core.entityservice.PersistenceService;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.JpaEntityService;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
public interface IEntityBeanList {

    JpaEntityService dynamicEntityService();

    AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator();

    PersistenceService dynamicEntityPersistenceService();
}
