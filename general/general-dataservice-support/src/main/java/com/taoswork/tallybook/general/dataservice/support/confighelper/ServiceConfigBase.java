package com.taoswork.tallybook.general.dataservice.support.confighelper;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.impl.EntityDescriptionServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.impl.EntityMetadataServiceImpl;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import com.taoswork.tallybook.general.solution.spring.BeanCreationMonitor;
import org.springframework.context.annotation.Bean;

/**
 * Created by Gao Yuan on 2015/5/19.
 */
public abstract class ServiceConfigBase {

    @Bean
    public BeanCreationMonitor beanCreationMonitor() {
        Class thisClz = this.getClass();
        Class clz = ClassUtility.getSuperClassWithout(thisClz, "spring");
        return new BeanCreationMonitor(clz.getSimpleName().replace("Config", ""));
    }

    @Bean(name = EntityMetadataService.SERVICE_NAME)
    public EntityMetadataService metadataService() {
        return new EntityMetadataServiceImpl();
    }

    @Bean(name = EntityDescriptionService.SERVICE_NAME)
    public EntityDescriptionService entityDescriptionService() {
        EntityDescriptionService entityDescriptionService = new EntityDescriptionServiceImpl();
        return entityDescriptionService;
    }
}
