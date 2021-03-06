package com.taoswork.tallybook.business.dataservice.tallyadmin;

import com.taoswork.tallybook.business.dataservice.tallyadmin.conf.AdminSpecifiedConfiguration;
import com.taoswork.tallybook.business.dataservice.tallyadmin.conf.TallyAdminDatasourceConfiguration;
import com.taoswork.tallybook.business.dataservice.tallyadmin.conf.TallyAdminPersistableConfiguration;
import com.taoswork.tallycheck.dataservice.annotations.DataService;
import com.taoswork.tallycheck.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallycheck.dataservice.mongo.core.MongoDataServiceBase;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
@DataService
//@Component(TallyAdminDataService.COMPONENT_NAME)
public class TallyAdminDataService
        extends MongoDataServiceBase<
                TallyAdminPersistableConfiguration,
                MongoDatasourceConfiguration> {

    public static final String COMPONENT_NAME = TallyAdminDataServiceDefinition.DATA_SERVICE_NAME;

    public TallyAdminDataService() {
        this(TallyAdminDatasourceConfiguration.class);
    }

    public TallyAdminDataService(Class<? extends MongoDatasourceConfiguration> dSrcConfClz) {
        super(new TallyAdminDataServiceDefinition(),
                TallyAdminPersistableConfiguration.class,
                dSrcConfClz, AdminSpecifiedConfiguration.class);
    }

    @Override
    protected void postConstruct() {
        super.postConstruct();
//        final String masterPersonId = AdminEmployee.ROOT_PERSON_ID;
//        IEntityService<Persistable> entityService = this.getService(IEntityService.COMPONENT_NAME);
//        try {
//            CriteriaTransferObject cto = new CriteriaTransferObject(AdminEmployee.FN_PERSON_ID, "" + masterPersonId);
//            AdminEmployee result = entityService.queryOne(AdminEmployee.class, cto);
//            if(result == null){
//                AdminEmployee newMaster = new AdminEmployee();
//                newMaster.setPersonId(masterPersonId);
//                newMaster.setName("Admin");
//                newMaster.setTitle("Master");
//                newMaster.setProtectionSpace(AdminSecurityDefinition.PROTECTION_SPACE);
//                newMaster.setNamespace(AdminSecurityDefinition.ADMIN_TENANT);
//                entityService.create(newMaster);
//            }
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }
//        IPermissionEngine permissionEngine = this.getService(AdminSpecifiedConfiguration.ADMIN_PERMISSION_ENGINE_NAME);
//        ISecurityVerifier securityVerifier = new SecurityVerifierByPermissionEngine(permissionEngine, ADMIN_DATA_PROTECTION_SCOPE, ADMIN_DATA_TENANT);
//        this.setSecurityVerifier(securityVerifier);
    }
}
