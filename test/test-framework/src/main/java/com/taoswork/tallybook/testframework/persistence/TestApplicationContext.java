package com.taoswork.tallybook.testframework.persistence;

import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class TestApplicationContext {
    public static ApplicationContext getApplicationContext(Class<?>... additionalAnnotationClasses){
        List<Class<?>> annotationClasses = new ArrayList<Class<?>>();
        for(Class<?> clz : additionalAnnotationClasses){
            annotationClasses.add(clz);
        }
        annotationClasses.add(TestDbPersistenceConfig.class);
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(annotationClasses.toArray(new Class<?>[]{}));
        return annotationConfigApplicationContext;
    }
}
