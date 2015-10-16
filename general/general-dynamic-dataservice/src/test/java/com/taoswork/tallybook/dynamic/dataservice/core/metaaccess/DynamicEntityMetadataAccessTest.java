package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.testframework.domain.zoo.ZooKeeper;
import com.taoswork.tallybook.testframework.domain.zoo.impl.ZooKeeperImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class DynamicEntityMetadataAccessTest {
    private static IDataService dataService = null;

    @BeforeClass
    public static void setup(){
        dataService = new TallyMockupDataService();
    }

    @AfterClass
    public static void teardown(){
        dataService = null;
    }

    @Test
    public void testEntityMetadataAccess_EntityNames() {
        DynamicEntityMetadataAccess dynamicEntityMetadataAccess = dataService.getService(DynamicEntityMetadataAccess.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityMetadataAccess);

        {
            Collection<Class> entities = dynamicEntityMetadataAccess.getAllEntities(false, true);
            Assert.assertNotNull(entities);
            Assert.assertFalse(entities.contains(ZooKeeperImpl.class));
            Assert.assertTrue(entities.contains(ZooKeeper.class));
        }
        {
            Collection<Class> entities = dynamicEntityMetadataAccess.getAllEntities(true, false);
            Assert.assertNotNull(entities);
            Assert.assertTrue(entities.contains(ZooKeeperImpl.class));
            Assert.assertFalse(entities.contains(ZooKeeper.class));
        }
        {
            Collection<Class> entities = dynamicEntityMetadataAccess.getAllEntities(true, true);
            Assert.assertNotNull(entities);
            Assert.assertTrue(entities.contains(ZooKeeperImpl.class));
            Assert.assertTrue(entities.contains(ZooKeeper.class));
        }
        {
            Collection<Class> entities = dynamicEntityMetadataAccess.getAllEntities(false, false);
            Assert.assertNotNull(entities);
            Assert.assertEquals(entities.size(), 0);
            Assert.assertFalse(entities.contains(ZooKeeperImpl.class));
            Assert.assertFalse(entities.contains(ZooKeeper.class));
        }

        EntityClassTree entityClassTree = dynamicEntityMetadataAccess.getEntityClassTree(ZooKeeper.class);
        Assert.assertEquals(ZooKeeper.class.getName(), entityClassTree.getData().clz.getName());

        ClassTreeMetadata entityClassTreeMetadata = dynamicEntityMetadataAccess.getClassTreeMetadata(ZooKeeper.class);
        Assert.assertEquals(ZooKeeper.class.getName(), entityClassTreeMetadata.getName());
    }

    @Test
    public void testEntityMetadataAccess_EntityTypeGuardians() {
        DynamicEntityMetadataAccess dynamicEntityMetadataAccess = dataService.getService(DynamicEntityMetadataAccess.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityMetadataAccess);

        Class guardian1 = dynamicEntityMetadataAccess.getPermissionGuardian(ZooKeeper.class);
        Class guardian2 = dynamicEntityMetadataAccess.getPermissionGuardian(ZooKeeperImpl.class);

        Assert.assertEquals(guardian1, ZooKeeper.class);
        Assert.assertEquals(guardian2, ZooKeeper.class);

    }
}
