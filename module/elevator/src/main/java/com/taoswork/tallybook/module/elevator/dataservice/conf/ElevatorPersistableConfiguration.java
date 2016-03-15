package com.taoswork.tallybook.module.elevator.dataservice.conf;

import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.annotations.EntityService;
import com.taoswork.tallybook.dataservice.mongo.config.MongoPersistableConfiguration;
import com.taoswork.tallybook.module.base.datadomain.CenterDefinition;
import com.taoswork.tallybook.module.elevator.dataservice.ElevatorModuleDataService;
import com.taoswork.tallybook.module.elevator.datadomain.ElevatorModuleDomain;
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
        basePackageClasses = ElevatorModuleDataService.class,
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
public class ElevatorPersistableConfiguration
        extends MongoPersistableConfiguration {
    @Override
    protected Class<?>[] createPersistableEntities() {
        List<Class> classes = new ArrayList<Class>();
        CollectionUtils.addAll(classes, ElevatorModuleDomain.persistableEntities());
        classes.add(CenterDefinition.class);
        return classes.toArray(new Class[]{});
    }
}
