package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.admincore.TallyBookAdminCoreRoot;
import com.taoswork.tallybook.application.core.conf.ApplicationCommonConfig;
import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.AdminEmployeeDetailsService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.impl.AdminEmployeeDetailsServiceImpl;
import com.taoswork.tallybook.business.dataservice.tallybusiness.TallyBusinessDataService;
import com.taoswork.tallybook.business.dataservice.tallymanagement.TallyManagementDataService;
import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataService;
import com.taoswork.tallybook.general.dataservice.management.manager.DataServiceManager;
import com.taoswork.tallybook.general.dataservice.management.manager.impl.DataServiceManagerImpl;
import com.taoswork.tallybook.general.dataservice.support.annotations.Dao;
import com.taoswork.tallybook.general.dataservice.support.annotations.EntityService;
import com.taoswork.tallybook.general.extension.annotations.FrameworkService;
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
                @ComponentScan.Filter( {
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
        @Bean(name = TallyUserDataService.COMPONENT_NAME)
        TallyUserDataService tallyUserDataService(){
                return new TallyUserDataService();
        }

        @Bean(name = TallyAdminDataService.COMPONENT_NAME)
        TallyAdminDataService tallyAdminDataService(){
                return new TallyAdminDataService();
        }

        @Bean(name = TallyBusinessDataService.COMPONENT_NAME)
        TallyBusinessDataService tallyBusinessDataService(){
                return new TallyBusinessDataService();
        }

        @Bean(name = TallyManagementDataService.COMPONENT_NAME)
        TallyManagementDataService tallyManagementDataService(){
                return new TallyManagementDataService();
        }

        @Bean(name = AdminEmployeeDetailsService.COMPONENT_NAME)
        UserDetailsService adminEmployeeDetailsService(){
                return new AdminEmployeeDetailsServiceImpl();
        }

        @Bean(name = DataServiceManager.COMPONENT_NAME)
        public DataServiceManager dataServiceManager(){
                return new DataServiceManagerImpl()
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
        }
}
