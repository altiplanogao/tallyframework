package com.taoswork.tallybook.business.dataservice.dynamic.entity.service;

import com.taoswork.tallybook.business.dataservice.dynamic.entity.conf.DynamicConfig;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.testframework.domain.TPerson;
import com.taoswork.tallybook.testframework.domain.impl.TPersonImpl;
import com.taoswork.tallybook.testframework.persistence.TestApplicationContext;
import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfig;
import com.taoswork.tallybook.testframework.persistence.em.EntityManagerHolder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class DynamicEntityServiceTest {
    private ApplicationContext applicationContext;

    @Before
    public void setup(){
        applicationContext = TestApplicationContext.getApplicationContext(DynamicConfig.class);
    }

    @After
    public void teardown(){
        applicationContext = null;
    }

    @Test
    public void testService(){
        EntityManagerHolder entityManagerHolder = (EntityManagerHolder)applicationContext.getBean(TestDbPersistenceConfig.ENTITY_MANAGER_HOLDER);
        Assert.assertNotNull(entityManagerHolder);

        EntityManager entityManager = entityManagerHolder.getEntityManager();
        Assert.assertNotNull(entityManager);

        DynamicEntityMetadataAccess dynamicEntityMetadataAccess = (DynamicEntityMetadataAccess)applicationContext.getBean(DynamicEntityMetadataAccess.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityMetadataAccess);
        testEntityMetadataAccess(dynamicEntityMetadataAccess);

        DynamicEntityService dynamicEntityService = (DynamicEntityService)applicationContext.getBean(DynamicEntityService.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityService);
        testDynamicEntityService(dynamicEntityService);
    }

    private void testEntityMetadataAccess(DynamicEntityMetadataAccess dynamicEntityMetadataAccess) {
        Map<String, EntityClassTree> entityClassTrees = dynamicEntityMetadataAccess.getAllEntityClassTree();
        Assert.assertNotNull(entityClassTrees);
        Assert.assertEquals(entityClassTrees.size(), 1);
        EntityClassTree entityClassTree = entityClassTrees.getOrDefault(TPerson.class.getName(), null);
        Assert.assertEquals(TPerson.class.getName(), entityClassTree.getData().clz.getName());
    }


    private void testDynamicEntityService(DynamicEntityService dynamicEntityService) {
        int createAttempt = 10;
        int created = 0;
        try {
            for (int i = 0; i < createAttempt; ++i) {

                int expected = i + 1;
                TPerson admin = new TPersonImpl();
                admin.setName("admin").setUuid(UUID.randomUUID().toString());
                dynamicEntityService.save(admin);

                Long id = admin.getId();
                TPerson adminFromDb = dynamicEntityService.find(TPerson.class, Long.valueOf(id));
                TPerson admin2FromDb = dynamicEntityService.find(TPersonImpl.class, Long.valueOf(id));

                Assert.assertTrue("Created and Read should be same: " + i, admin.getId() == adminFromDb.getId());
                Assert.assertTrue("Created Object [" + admin.getId() + "] should have Id: " + expected, admin.getId().equals(0L + expected));

                Assert.assertEquals(adminFromDb.getUuid(), admin2FromDb.getUuid());
                Assert.assertEquals(adminFromDb.getId(), admin2FromDb.getId());

                Assert.assertNotNull(adminFromDb);
                Assert.assertTrue(adminFromDb.getName().equals("admin")); //Loaded from load_person.xml

                created++;
            }
        } finally {
            Assert.assertEquals(createAttempt, created);
        }
        List<TPerson> persons = dynamicEntityService.query(TPerson.class, new CriteriaTransferObject());
        Assert.assertEquals(persons.size(), createAttempt);
    }
}
