package com.taoswork.tallybook.general.dataservice.support.config;

import com.taoswork.tallybook.dynamic.datameta.description.service.MetaInfoService;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.general.dataservice.support.config.list.IStandAloneBeanList;
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

    @Override
    @Bean(name = MetadataService.SERVICE_NAME)
    public MetadataService metadataService() {
        return new MetadataServiceImpl();
    }

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
