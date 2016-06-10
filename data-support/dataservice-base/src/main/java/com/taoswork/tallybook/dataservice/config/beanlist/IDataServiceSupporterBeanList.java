package com.taoswork.tallybook.dataservice.config.beanlist;

import com.taoswork.tallybook.dataservice.core.description.FriendlyMetaInfoService;
import com.taoswork.tallybook.dataservice.security.impl.SecurityVerifierAgent;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface IDataServiceSupporterBeanList {

    RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer();

    MessageSource entityMessageSource();

    MessageSource errorMessageSource();

    FriendlyMetaInfoService friendlyMetaInfoService();

    SecurityVerifierAgent securityVerifierAgent();
}
