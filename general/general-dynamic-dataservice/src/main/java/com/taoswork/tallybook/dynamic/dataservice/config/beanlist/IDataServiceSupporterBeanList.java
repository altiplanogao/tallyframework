package com.taoswork.tallybook.dynamic.dataservice.config.beanlist;

import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition;
import com.taoswork.tallybook.dynamic.dataservice.core.description.FriendlyMetaInfoService;
import com.taoswork.tallybook.dynamic.dataservice.core.security.impl.SecurityVerifierAgent;
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

    SecurityVerifierAgent securityVerifierAgent();
}
