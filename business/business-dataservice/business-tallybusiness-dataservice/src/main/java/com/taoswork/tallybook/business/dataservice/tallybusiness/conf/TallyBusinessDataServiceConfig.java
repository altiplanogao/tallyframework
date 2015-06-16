package com.taoswork.tallybook.business.dataservice.tallybusiness.conf;

import com.taoswork.tallybook.business.dataservice.tallybusiness.TallyBusinessDataService;
import com.taoswork.tallybook.general.dataservice.support.annotations.Dao;
import com.taoswork.tallybook.general.dataservice.support.annotations.EntityService;
import com.taoswork.tallybook.general.dataservice.support.confighelper.DataServiceConfigBase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
@Configuration
@ComponentScan(
        basePackageClasses = TallyBusinessDataService.class,
        includeFilters = {@ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = {
                        Dao.class,
                        EntityService.class})},
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = {Configuration.class}
        )}
)
public class TallyBusinessDataServiceConfig extends DataServiceConfigBase {
}
