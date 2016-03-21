package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.admincore.TallyBookAdminCoreRoot;
import com.taoswork.tallybook.application.core.conf.ApplicationCommonConfig;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminProtection;
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
import com.taoswork.tallybook.dataservice.jpa.config.db.IDbConfig;
import com.taoswork.tallybook.dataservice.jpa.config.db.ProductDbConfig;
import com.taoswork.tallybook.dataservice.server.manage.DataServiceManager;
import com.taoswork.tallybook.dataservice.server.manage.impl.DataServiceManagerImpl;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.general.extension.annotations.FrameworkService;
import org.springframework.beans.factory.BeanCreationException;
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
    private static void dataServiceManagerDoInitialize(DataServiceManager dataServiceManager) throws ServiceException {
        IDataService adminDataService = dataServiceManager.getDataServiceByServiceName(TallyAdminDataService.COMPONENT_NAME);
        IEntityService entityService = adminDataService.getService(IEntityService.COMPONENT_NAME);

        for (AdminProtection res : SecuredResources.getResources()) {
            CriteriaTransferObject cto = new CriteriaTransferObject();
            PropertyFilterCriteria propC = new PropertyFilterCriteria("name", res.getName());
            cto.addFilterCriteria(propC);
            CriteriaQueryResult cqr = entityService.query(AdminProtection.class, cto);
            if (cqr.fetchedCount() == 0) {
                entityService.create(res);
            }
        }
    }

    protected Class<? extends IDbConfig> getDbSetting() {
        return ProductDbConfig.class;
    }

    @Bean(name = TallyUserDataService.COMPONENT_NAME)
    public TallyUserDataService tallyUserDataService() {
        return new TallyUserDataService(getDbSetting());
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
        DataServiceManager dataServiceManager = new DataServiceManagerImpl() {
            @Override
            public void doInitialize() {
                try {
                    dataServiceManagerDoInitialize(this);
                } catch (ServiceException se) {
                    throw new BeanCreationException("ServiceException", se);
                }
            }
        };

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
