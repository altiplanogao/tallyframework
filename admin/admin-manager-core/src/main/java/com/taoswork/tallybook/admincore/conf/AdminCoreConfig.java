package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.admincore.TallyBookAdminCoreRoot;
import com.taoswork.tallybook.application.core.conf.ApplicationCommonConfig;
import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.AdminEmployeeDetailsService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.impl.AdminEmployeeDetailsServiceImpl;
import com.taoswork.tallybook.business.dataservice.tallybusiness.TallyBusinessDataService;
import com.taoswork.tallybook.business.dataservice.tallymanagement.TallyManagementDataService;
import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataService;
import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.IDbSetting;
import com.taoswork.tallybook.dynamic.dataservice.manage.DataServiceManager;
import com.taoswork.tallybook.dynamic.dataservice.manage.impl.DataServiceManagerImpl;
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
        protected IDbSetting getDbSetting(){
                return null;
        }

        @Bean(name = TallyUserDataService.COMPONENT_NAME)
        public TallyUserDataService tallyUserDataService(){
                return new TallyUserDataService(getDbSetting());
        }

        @Bean(name = TallyAdminDataService.COMPONENT_NAME)
        public TallyAdminDataService tallyAdminDataService(){
                return new TallyAdminDataService(getDbSetting());
        }

        @Bean(name = TallyBusinessDataService.COMPONENT_NAME)
        public TallyBusinessDataService tallyBusinessDataService(){
                return new TallyBusinessDataService(getDbSetting());
        }

        @Bean(name = TallyManagementDataService.COMPONENT_NAME)
        public TallyManagementDataService tallyManagementDataService(){
                return new TallyManagementDataService(getDbSetting());
        }

        @Bean(name = AdminEmployeeDetailsService.COMPONENT_NAME)
        public UserDetailsService adminEmployeeDetailsService(){
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
