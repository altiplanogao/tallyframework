package com.taoswork.tallybook.general.dataservice.support.config;

import com.taoswork.tallybook.dynamic.datameta.description.service.MetaInfoService;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
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

    public DataServiceConfigBase() {
        this("");
    }

    public DataServiceConfigBase(String name) {
        if (StringUtils.isEmpty(name)) {
            Class thisClz = this.getClass();
            Class clz = ClassUtility.getSuperClassWithout(thisClz, "spring");
            this.name = clz.getSimpleName().replace("Config", "");
        } else {
            this.name = name;
        }
    }

    @Bean(name = IDataService.DATASERVICE_NAME_S_BEAN_NAME)
    public String dataServiceName() {
        return name;
    }

    @Bean
    public BeanCreationMonitor beanCreationMonitor() {
        return new BeanCreationMonitor(dataServiceName());
    }

    @Bean(name = MetadataService.SERVICE_NAME)
    public MetadataService metadataService() {
        return new MetadataServiceImpl();
    }

    @Bean(name = MetaInfoService.SERVICE_NAME)
    public MetaInfoService metaInfoService() {
        MetaInfoService metaInfoService = new MetaInfoServiceImpl();
        if (metaInfoService instanceof HasCacheScope) {
            ((HasCacheScope) metaInfoService).setCacheScope(
                    dataServiceName() + "." + MetaInfoService.class.getSimpleName());
        }
        return metaInfoService;
    }
}
