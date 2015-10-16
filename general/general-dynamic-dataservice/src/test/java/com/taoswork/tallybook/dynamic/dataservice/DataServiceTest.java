package com.taoswork.tallybook.dynamic.dataservice;

import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.entity.EntityEntry;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.testframework.domain.zoo.ZooKeeper;
import com.taoswork.tallybook.testframework.domain.zoo.impl.ZooKeeperImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
public class DataServiceTest {

    private IDataService dataService = null;

    @Before
    public void setup(){
        dataService = new TallyMockupDataService();
    }

    @After
    public void teardown(){
        dataService = null;
    }

    @Test
    public void testEntityEntries(){
        Map<String, EntityEntry> entityEntries = dataService.getEntityEntries();

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

        DynamicEntityMetadataAccess dynamicEntityMetadataAccess = dataService.getService(DynamicEntityMetadataAccess.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityMetadataAccess);

        Collection<Class> entities = dynamicEntityMetadataAccess.getAllEntities(true, true);
        for(Class entity : entities){
            Assert.assertTrue(entityEntries.containsKey(entity.getName()));
        }

        Assert.assertEquals(entities.size(), entityEntries.size());
    }

}
