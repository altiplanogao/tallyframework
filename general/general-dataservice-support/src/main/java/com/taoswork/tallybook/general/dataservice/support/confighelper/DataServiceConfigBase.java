package com.taoswork.tallybook.general.dataservice.support.confighelper;

import com.taoswork.tallybook.dynamic.datameta.description.service.MetaDescriptionService;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaDescriptionServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
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

    @Bean(name = MetadataService.SERVICE_NAME)
    public MetadataService metadataService() {
        return new MetadataServiceImpl();
    }

    @Bean(name = MetaDescriptionService.SERVICE_NAME)
    public MetaDescriptionService metaDescriptionService() {
        MetaDescriptionService metaDescriptionService = new MetaDescriptionServiceImpl();
        if(metaDescriptionService instanceof HasCacheScope){
            ((HasCacheScope) metaDescriptionService).setCacheScope(
                    dataServiceName() + "." + MetaDescriptionService.class.getSimpleName());
        }
        return metaDescriptionService;
    }
}
