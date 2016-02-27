package com.taoswork.tallybook.business.dataservice.tallyadmin.conf;

import com.taoswork.tallybook.authority.solution.AuthoritySolutionDomain;
import com.taoswork.tallybook.business.datadomain.tallyadmin.TallyAdminDataDomain;
import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataService;
import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.annotations.EntityService;
import com.taoswork.tallybook.dataservice.mongo.config.MongoPersistableConfiguration;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
@Configuration
@ComponentScan(
        basePackageClasses = TallyAdminDataService.class,
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
public class TallyAdminPersistableConfiguration
        extends MongoPersistableConfiguration {
    @Override
    protected Class<?>[] createPersistableEntities() {
        List<Class> classes = new ArrayList<Class>();
        CollectionUtils.addAll(classes, AuthoritySolutionDomain.domainEntities());
        CollectionUtils.addAll(classes, TallyAdminDataDomain.persistableEntities());
        return classes.toArray(new Class[]{});
    }
}
