package com.taoswork.tallybook.dynamic.dataservice.config.beanlist;

import com.taoswork.tallybook.dynamic.datameta.description.service.MetaInfoService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface IStandAloneBeanList {
    MetadataService metadataService();

    MetaInfoService metaInfoService();
}
