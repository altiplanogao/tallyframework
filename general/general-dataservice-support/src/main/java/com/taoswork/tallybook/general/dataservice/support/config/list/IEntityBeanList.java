package com.taoswork.tallybook.general.dataservice.support.config.list;

import com.taoswork.tallybook.dynamic.dataservice.server.service.DynamicServerEntityService;
import com.taoswork.tallybook.dynamic.dataservice.service.DynamicEntityService;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
public interface IEntityBeanList {
    DynamicEntityService dynamicEntityService();

    DynamicServerEntityService dynamicServerEntityService();
}
