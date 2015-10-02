package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.testframework.domain.TPerson;
import com.taoswork.tallybook.testframework.domain.impl.TPersonImpl;
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
            Assert.assertFalse(entities.contains(TPersonImpl.class));
            Assert.assertTrue(entities.contains(TPerson.class));
        }
        {
            Collection<Class> entities = dynamicEntityMetadataAccess.getAllEntities(true, false);
            Assert.assertNotNull(entities);
            Assert.assertTrue(entities.contains(TPersonImpl.class));
            Assert.assertFalse(entities.contains(TPerson.class));
        }
        {
            Collection<Class> entities = dynamicEntityMetadataAccess.getAllEntities(true, true);
            Assert.assertNotNull(entities);
            Assert.assertTrue(entities.contains(TPersonImpl.class));
            Assert.assertTrue(entities.contains(TPerson.class));
        }
        {
            Collection<Class> entities = dynamicEntityMetadataAccess.getAllEntities(false, false);
            Assert.assertNotNull(entities);
            Assert.assertEquals(entities.size(), 0);
            Assert.assertFalse(entities.contains(TPersonImpl.class));
            Assert.assertFalse(entities.contains(TPerson.class));
        }

        EntityClassTree entityClassTree = dynamicEntityMetadataAccess.getEntityClassTree(TPerson.class);
        Assert.assertEquals(TPerson.class.getName(), entityClassTree.getData().clz.getName());

        ClassTreeMetadata entityClassTreeMetadata = dynamicEntityMetadataAccess.getClassTreeMetadata(TPerson.class);
        Assert.assertEquals(TPerson.class.getName(), entityClassTreeMetadata.getName());
    }

    @Test
    public void testEntityMetadataAccess_EntityTypeGuardians() {
        DynamicEntityMetadataAccess dynamicEntityMetadataAccess = dataService.getService(DynamicEntityMetadataAccess.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityMetadataAccess);

        Class guardian1 = dynamicEntityMetadataAccess.getPermissionGuardian(TPerson.class);
        Class guardian2 = dynamicEntityMetadataAccess.getPermissionGuardian(TPersonImpl.class);

        Assert.assertEquals(guardian1, TPerson.class);
        Assert.assertEquals(guardian2, TPerson.class);

    }
}
