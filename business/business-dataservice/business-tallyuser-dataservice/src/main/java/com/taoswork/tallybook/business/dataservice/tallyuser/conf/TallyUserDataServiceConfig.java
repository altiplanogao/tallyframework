package com.taoswork.tallybook.business.dataservice.tallyuser.conf;

import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataService;
import com.taoswork.tallybook.general.dataservice.support.annotations.Dao;
import com.taoswork.tallybook.general.dataservice.support.annotations.EntityService;
import com.taoswork.tallybook.general.dataservice.support.config.DataServiceConfigBase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@Configuration
@ComponentScan(
        basePackageClasses = TallyUserDataService.class,
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
public class TallyUserDataServiceConfig extends DataServiceConfigBase {
        public TallyUserDataServiceConfig() {
                super("TallyUserDataService");
        }
}
