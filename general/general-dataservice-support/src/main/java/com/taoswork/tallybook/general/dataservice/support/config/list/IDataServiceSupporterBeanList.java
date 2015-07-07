package com.taoswork.tallybook.general.dataservice.support.config.list;

import com.taoswork.tallybook.dynamic.dataservice.description.FriendlyMetaInfoService;
import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface IDataServiceSupporterBeanList {

    IDataServiceDefinition dataServiceDefinitionBean();

    RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer();

    MessageSource entityFriendlyMessageSource();

    FriendlyMetaInfoService friendlyMetaInfoService();
}
