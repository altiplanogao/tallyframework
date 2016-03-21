package com.taoswork.tallybook.dataservice.mongo;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.config.IDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.core.entity.EntityCatalog;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.mongo.servicemockup.TallyMockupMongoDataService;
import com.taoswork.tallybook.dataservice.mongo.servicemockup.datasource.TallyMockupMongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.testmaterial.mongo.domain.nature.Citizen;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
public class MongoDataServiceTest {
    private IDataService dataService = null;

    @Before
    public void setup() {
        dataService = new TallyMockupMongoDataService();
    }

    @After
    public void teardown() {
        TallyMockupMongoDatasourceConfiguration.DatasourceDefinition mdbDef = dataService.getService(IDatasourceConfiguration.DATA_SOURCE_PATH_DEFINITION);
        mdbDef.dropDatabase();
        dataService = null;
    }

    @Test
    public void testEntityEntries() {
        IEntityService entityService = dataService.getService(IEntityService.COMPONENT_NAME);
        Assert.assertNotNull(entityService);

        {
            Map<String, EntityCatalog> entityEntries = dataService.getEntityCatalogs();
            Class entityType = Citizen.class;
            String citizenResName = dataService.getEntityResourceName(entityType.getName());
            String citizenTypeName = dataService.getEntityTypeName(citizenResName);

            Assert.assertEquals(citizenResName, entityType.getSimpleName().toLowerCase());
            Assert.assertEquals(citizenTypeName, entityType.getName());
            Assert.assertTrue(entityEntries.containsKey(citizenTypeName));
        }

        EntityMetaAccess entityMetaAccess = dataService.getService(EntityMetaAccess.COMPONENT_NAME);
        Assert.assertNotNull(entityMetaAccess);

        try {
            {
                Citizen c = new Citizen();
                c.setFirstName("First");
                c.setLastName("Last");

                PersistableResult<Citizen> result = entityService.create(c);

            }

        } catch (ServiceException e) {
            Assert.fail(e.getMessage());
        } finally {
            //dataService.
        }

        //entityService.create(ctzClz, c);

     //   entityService.create(ctzClz, c);

    }
}
