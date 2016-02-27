package com.taoswork.tallybook.dataservice.jpa;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.core.entity.EntityCatalog;
import com.taoswork.tallybook.dataservice.jpa.config.db.TestDbConfig;
import com.taoswork.tallybook.dataservice.jpa.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.ZooKeeper;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.impl.ZooKeeperImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
public class JpaDataServiceTest {

    private IDataService dataService = null;

    @Before
    public void setup(){
        dataService = new TallyMockupDataService(TestDbConfig.class);
    }

    @After
    public void teardown(){
        dataService = null;
    }

    @Test
    public void testEntityEntries(){
        Map<String, EntityCatalog> entityEntries = dataService.getEntityCatalogs();

        {
            Class entityType = ZooKeeper.class;
            String personResName = dataService.getEntityResourceName(entityType.getName());
            String personTypeName = dataService.getEntityTypeName(personResName);

            Assert.assertEquals(personResName, entityType.getSimpleName().toLowerCase());
            Assert.assertEquals(personTypeName, entityType.getName());
            Assert.assertTrue(entityEntries.containsKey(personTypeName));
        }
        {
            Class entityType = ZooKeeperImpl.class;
            String personResName = dataService.getEntityResourceName(entityType.getName());
            String personTypeName = dataService.getEntityTypeName(personResName);

            Assert.assertEquals(personResName, entityType.getSimpleName().toLowerCase());
            Assert.assertEquals(personTypeName, entityType.getName());
            Assert.assertTrue(entityEntries.containsKey(personTypeName));
        }

        EntityMetaAccess entityMetaAccess = dataService.getService(EntityMetaAccess.COMPONENT_NAME);
        Assert.assertNotNull(entityMetaAccess);

        Collection<Class> entities = entityMetaAccess.getAllEntities(true, true);
        for(Class entity : entities){
            Assert.assertTrue(entityEntries.containsKey(entity.getName()));
        }

        Assert.assertEquals(entities.size(), entityEntries.size());
    }

}
