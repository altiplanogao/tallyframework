package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.admincore.TallyBookAdminCoreRoot;
import com.taoswork.tallybook.application.core.conf.ApplicationCommonConfig;
import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.AdminEmployeeDetailsService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.impl.AdminEmployeeDetailsServiceImpl;
import com.taoswork.tallybook.business.dataservice.tallybusiness.TallyBusinessDataService;
import com.taoswork.tallybook.business.dataservice.tallymanagement.TallyManagementDataService;
import com.taoswork.tallycheck.dataservice.tallyuser.TallyUserDataService;
import com.taoswork.tallycheck.dataservice.annotations.Dao;
import com.taoswork.tallycheck.dataservice.annotations.EntityService;
import com.taoswork.tallycheck.dataservice.jpa.config.db.IDbConfig;
import com.taoswork.tallycheck.dataservice.jpa.config.db.ProductDbConfig;
import com.taoswork.tallycheck.dataservice.server.manage.DataServiceManager;
import com.taoswork.tallycheck.dataservice.server.manage.impl.DataServiceManagerImpl;
import com.taoswork.tallycheck.general.extension.annotations.FrameworkService;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Gao Yuan on 2015/4/24.
 */
@Configuration
@Import({ApplicationCommonConfig.class})
@ComponentScan(
        basePackageClasses = TallyBookAdminCoreRoot.class,
        //   useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter({
                        Dao.class,
                        EntityService.class,
                        FrameworkService.class
                })},
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = {Configuration.class}
        )}
)
public class AdminCoreConfig {
    protected Class<? extends IDbConfig> getDbSetting() {
        return ProductDbConfig.class;
    }

    @Bean(name = TallyUserDataService.COMPONENT_NAME)
    public TallyUserDataService tallyUserDataService() {
        return new TallyUserDataService();
    }

    @Bean(name = TallyAdminDataService.COMPONENT_NAME)
    public TallyAdminDataService tallyAdminDataService() {
        return new TallyAdminDataService();
    }

    @Bean(name = TallyBusinessDataService.COMPONENT_NAME)
    public TallyBusinessDataService tallyBusinessDataService() {
        return new TallyBusinessDataService();
    }

    @Bean(name = TallyManagementDataService.COMPONENT_NAME)
    public TallyManagementDataService tallyManagementDataService() {
        return new TallyManagementDataService();
    }

    @Bean(name = AdminEmployeeDetailsService.COMPONENT_NAME)
    public UserDetailsService adminEmployeeDetailsService() {
        return new AdminEmployeeDetailsServiceImpl();
    }

    @Bean(name = DataServiceManager.COMPONENT_NAME)
    public DataServiceManager dataServiceManager() {
        DataServiceManager dataServiceManager = new DataServiceManagerImpl();

        dataServiceManager
                .buildingAppendDataService(
                        TallyUserDataService.COMPONENT_NAME,
                        tallyUserDataService())
                .buildingAppendDataService(
                        TallyAdminDataService.COMPONENT_NAME,
                        tallyAdminDataService())
                .buildingAppendDataService(
                        TallyBusinessDataService.COMPONENT_NAME,
                        tallyBusinessDataService())
                .buildingAppendDataService(
                        TallyManagementDataService.COMPONENT_NAME,
                        tallyManagementDataService())

                .buildingAnnounceFinishing();

        return dataServiceManager;
    }
}
