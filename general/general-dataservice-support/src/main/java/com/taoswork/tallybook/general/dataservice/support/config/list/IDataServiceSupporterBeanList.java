package com.taoswork.tallybook.general.dataservice.support.config.list;

import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface IDataServiceSupporterBeanList {

    IDataServiceDefinition dataServiceDefinitionBean();

    RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer();
}
