package com.taoswork.tallybook.business.dataservice.tallybusiness;

import com.taoswork.tallybook.business.dataservice.tallybusiness.conf.TestDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import org.junit.Assert;
import org.junit.Test;

public class TallyBusinessDataServiceTest {
    @Test
    public void testCreation() {
        try {
            TallyBusinessDataService tbDataService = new TallyBusinessDataService(TestDatasourceConfiguration.class);
            IEntityService entityService = tbDataService.getService(IEntityService.COMPONENT_NAME);
            EntityMetaAccess entityMetaAccess = tbDataService.getService(EntityMetaAccess.COMPONENT_NAME);
            Assert.assertNotNull(entityService);
            Assert.assertNotNull(entityMetaAccess);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
