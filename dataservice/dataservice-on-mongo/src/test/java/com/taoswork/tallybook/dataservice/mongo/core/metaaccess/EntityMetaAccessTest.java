package com.taoswork.tallybook.dataservice.mongo.core.metaaccess;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.mongo.servicemockup.TallyMockupMongoDataService;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.testmaterial.mongo.domain.business.ICompany;
import com.taoswork.tallybook.testmaterial.mongo.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testmaterial.mongo.domain.nature.Citizen;
import com.taoswork.tallybook.testmaterial.mongo.domain.zoo.ZooKeeper;
import com.taoswork.tallybook.testmaterial.mongo.domain.zoo.impl.ZooKeeperImpl;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityMetaAccessTest {
    private static IDataService dataService = null;

    @BeforeClass
    public static void setup() {
        dataService = new TallyMockupMongoDataService();
    }

    @AfterClass
    public static void teardown() {
        dataService = null;
    }

    @Test
    public void testEntityMetaAccess_EntityNames() {
        EntityMetaAccess entityMetaAccess = dataService.getService(EntityMetaAccess.COMPONENT_NAME);
        Assert.assertNotNull(entityMetaAccess);

        {
            Collection<Class> entities = entityMetaAccess.getAllEntities(false, true);
            Assert.assertNotNull(entities);
            //           Assert.assertFalse(entities.contains(ZooKeeperImpl.class));
            Assert.assertFalse(entities.contains(Citizen.class));
            Assert.assertFalse(entities.contains(ZooKeeperImpl.class));
            Assert.assertTrue(entities.contains(ZooKeeper.class));
        }
        {
            Collection<Class> entities = entityMetaAccess.getAllEntities(true, false);
            Assert.assertNotNull(entities);
            Assert.assertTrue(entities.contains(Citizen.class));
            Assert.assertTrue(entities.contains(ZooKeeperImpl.class));
            Assert.assertFalse(entities.contains(ZooKeeper.class));
        }
        {
            Collection<Class> entities = entityMetaAccess.getAllEntities(true, true);
            Assert.assertNotNull(entities);
            Assert.assertTrue(entities.contains(Citizen.class));
            Assert.assertTrue(entities.contains(ZooKeeperImpl.class));
            Assert.assertTrue(entities.contains(ZooKeeper.class));
        }
        {
            Collection<Class> entities = entityMetaAccess.getAllEntities(false, false);
            Assert.assertNotNull(entities);
            Assert.assertEquals(entities.size(), 0);
            Assert.assertFalse(entities.contains(Citizen.class));
            Assert.assertFalse(entities.contains(ZooKeeperImpl.class));
            Assert.assertFalse(entities.contains(ZooKeeper.class));
        }

        EntityClassTree entityClassTree = entityMetaAccess.getEntityClassTree(ZooKeeper.class);
        Assert.assertEquals(ZooKeeper.class.getName(), entityClassTree.getData().clz.getName());

        IClassMeta entityClassTreeMeta = entityMetaAccess.getClassTreeMeta(ZooKeeper.class);
        Assert.assertEquals(ZooKeeper.class.getName(), entityClassTreeMeta.getName());
    }

    @Test
    public void testEntityMetaAccess_EntityTypeGuardians() {
        EntityMetaAccess entityMetaAccess = dataService.getService(EntityMetaAccess.COMPONENT_NAME);
        Assert.assertNotNull(entityMetaAccess);
        {
            Class guardian1 = entityMetaAccess.getPermissionGuardian(ZooKeeper.class);
            Class guardian2 = entityMetaAccess.getPermissionGuardian(ZooKeeperImpl.class);

            Assert.assertEquals(guardian1, ZooKeeper.class);
            Assert.assertEquals(guardian2, ZooKeeper.class);
        }
        {
            Class guardian1 = entityMetaAccess.getPermissionGuardian(ICompany.class);
            Class guardian2 = entityMetaAccess.getPermissionGuardian(CompanyImpl.class);

            Assert.assertEquals(guardian1, ICompany.class);
            Assert.assertEquals(guardian2, ICompany.class);
        }
    }
}
