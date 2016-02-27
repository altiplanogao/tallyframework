package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.admincore.TallyBookAdminCoreRoot;
import com.taoswork.tallybook.application.core.conf.ApplicationCommonConfig;
import com.taoswork.tallybook.authority.solution.domain.resource.ResourceProtectionMode;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminSecuredResource;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.impl.AdminSecuredResourceImpl;
import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.AdminEmployeeDetailsService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.impl.AdminEmployeeDetailsServiceImpl;
import com.taoswork.tallybook.business.dataservice.tallybusiness.TallyBusinessDataService;
import com.taoswork.tallybook.business.dataservice.tallymanagement.TallyManagementDataService;
import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataService;
import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.annotations.Dao;
import com.taoswork.tallybook.dataservice.annotations.EntityService;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.jpa.config.db.setting.JpaDbSetting;
import com.taoswork.tallybook.dataservice.manage.DataServiceManager;
import com.taoswork.tallybook.dataservice.manage.impl.DataServiceManagerImpl;
import com.taoswork.tallybook.dataservice.service.IEntityService;
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
        protected JpaDbSetting getDbSetting(){
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
                DataServiceManager dataServiceManager = new DataServiceManagerImpl(){
                        @Override
                        public void doInitialize() {
                                dataServiceManagerDoInitialize(this);
                        }
                };

                dataServiceManager.buildingAppendDataService(
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

        private void dataServiceManagerDoInitialize(DataServiceManager dataServiceManager){
                IDataService adminDataService = dataServiceManager.getDataServiceByServiceName(TallyAdminDataService.COMPONENT_NAME);
                IEntityService entityService = adminDataService.getService(IEntityService.COMPONENT_NAME);
                try {
                        for (SecuredResource res : SecuredResources.getResources()) {
                                AdminSecuredResource asr = new AdminSecuredResourceImpl();
                                asr.setName(res.getName());
                                asr.setResourceEntity(res.getEntity());
                                asr.setCategory(res.getCategory());
                                asr.setMasterControlled(res.isMasterControlled());
                                switch (res.getProtectionMode()) {
                                        case FitAll:
                                                asr.setProtectionMode(ResourceProtectionMode.PassAll);
                                                break;
                                        case FitAny:
                                                asr.setProtectionMode(ResourceProtectionMode.PassAny);
                                                break;
                                }
                                CriteriaTransferObject cto = new CriteriaTransferObject();
                                PropertyFilterCriteria propC = new PropertyFilterCriteria("name", res.getName());
                                cto.addFilterCriteria(propC);
                                CriteriaQueryResult cqr = entityService.query(AdminSecuredResource.class, cto);
                                if(cqr.fetchedCount() == 0){
                                        entityService.create(AdminSecuredResource.class, asr);
                                }
                        }
                } catch (ServiceException e) {
                        e.printStackTrace();
                }
        }
}
