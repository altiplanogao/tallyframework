package com.taoswork.tallybook.dataservice.config;

import com.taoswork.tallybook.dataservice.config.beanlist.IStandAloneBeanList;
import com.taoswork.tallybook.descriptor.service.MetaInfoService;
import com.taoswork.tallybook.descriptor.service.impl.MetaInfoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
@Configuration
public class StandAloneBeanConfiguration implements IStandAloneBeanList {

    // **************************************************** //
    //      IStandAloneBeanList                             //
    // **************************************************** //
//
//    @Override
//    @Bean(name = MetaService.SERVICE_NAME)
//    public MetaService metadataService() {
//        return new JpaMetadataServiceImpl();
//    }

    @Override
    @Bean(name = MetaInfoService.SERVICE_NAME)
    public MetaInfoService metaInfoService() {
        MetaInfoService metaInfoService = new MetaInfoServiceImpl();
        return metaInfoService;
    }

    // **************************************************** //
    //                                                      //
    // **************************************************** //

}
