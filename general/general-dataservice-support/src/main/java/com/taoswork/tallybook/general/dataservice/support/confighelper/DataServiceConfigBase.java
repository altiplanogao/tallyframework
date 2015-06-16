package com.taoswork.tallybook.general.dataservice.support.confighelper;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.impl.EntityDescriptionServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.impl.EntityMetadataServiceImpl;
import com.taoswork.tallybook.general.dataservice.support.IDataService;
import com.taoswork.tallybook.general.solution.cache.ehcache.HasCacheScope;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import com.taoswork.tallybook.general.solution.spring.BeanCreationMonitor;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

/**
 * Created by Gao Yuan on 2015/5/19.
 */
public abstract class DataServiceConfigBase {

    private final String name;

    public DataServiceConfigBase(){
        this("");
    }

    public DataServiceConfigBase(String name){
        if(StringUtils.isEmpty(name)){
            Class thisClz = this.getClass();
            Class clz = ClassUtility.getSuperClassWithout(thisClz, "spring");
            this.name = clz.getSimpleName().replace("Config", "");
        }else {
            this.name = name;
        }
    }

    @Bean(name = IDataService.DATASERVICE_NAME_S_BEAN_NAME)
    public String dataServiceName(){
        return name;
    }

    @Bean
    public BeanCreationMonitor beanCreationMonitor() {
        return new BeanCreationMonitor(dataServiceName());
//        Class thisClz = this.getClass();
//        Class clz = ClassUtility.getSuperClassWithout(thisClz, "spring");
//        return new BeanCreationMonitor(clz.getSimpleName().replace("Config", ""));
    }

    @Bean(name = EntityMetadataService.SERVICE_NAME)
    public EntityMetadataService metadataService() {
        return new EntityMetadataServiceImpl();
    }

    @Bean(name = EntityDescriptionService.SERVICE_NAME)
    public EntityDescriptionService entityDescriptionService() {
        EntityDescriptionService entityDescriptionService = new EntityDescriptionServiceImpl();
        if(entityDescriptionService instanceof HasCacheScope){
            ((HasCacheScope) entityDescriptionService).setCacheScope(
                    dataServiceName() + "." + EntityDescriptionService.class.getSimpleName());
        }
        return entityDescriptionService;
    }
}
