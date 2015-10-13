package com.taoswork.tallybook.dynamic.dataservice.config.beanlist;

import com.taoswork.tallybook.general.solution.spring.BeanCreationMonitor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface IGeneralBeanList {
    BeanCreationMonitor beanCreationMonitor();

    String dataServiceName();

    AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator();

}
