package com.taoswork.tallybook.business.dataservice.dynamic.entity.service;

import com.taoswork.tallybook.business.dataservice.dynamic.entity.conf.DynamicConfig;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.*;
import com.taoswork.tallybook.testframework.domain.TPerson;
import com.taoswork.tallybook.testframework.domain.impl.TPersonImpl;
import com.taoswork.tallybook.testframework.persistence.TestApplicationContext;
import com.taoswork.tallybook.testframework.persistence.conf.TestDbPersistenceConfig;
import com.taoswork.tallybook.testframework.persistence.em.EntityManagerHolder;
import org.junit.*;
import org.springframework.context.ApplicationContext;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class DynamicEntityServiceTest {
    static class EntityCreateHelper{
        static int createPeopleEntityWith(DynamicEntityService dynamicEntityService,
                                           String namePrefix, int postfixStartingIndex, int createAttempt){
            int created = 0;
            try {
                for (int i = 0; i < createAttempt; ++i) {
                    String name = namePrefix + (int)(postfixStartingIndex + i);

//                    int expected = i + 1;
                    TPerson admin = new TPersonImpl();
                    admin.setName(name).setUuid(UUID.randomUUID().toString());
                    dynamicEntityService.save(admin);

                    Long id = admin.getId();
                    TPerson adminFromDb = dynamicEntityService.find(TPerson.class, Long.valueOf(id));
                    TPerson admin2FromDb = dynamicEntityService.find(TPersonImpl.class, Long.valueOf(id));

                    Assert.assertEquals("Created and Read should be same: " + admin.getId() + " : " + adminFromDb.getId(),
                            admin.getId(), adminFromDb.getId());
//                    Assert.assertTrue("Created Object [" + admin.getId() + "] should have Id: " + expected, admin.getId().equals(0L + expected));

                    Assert.assertEquals(admin.getUuid(), adminFromDb.getUuid());
                    Assert.assertEquals(adminFromDb.getUuid(), admin2FromDb.getUuid());

                    Assert.assertEquals(admin.getId(), adminFromDb.getId());
                    Assert.assertEquals(adminFromDb.getId(), admin2FromDb.getId());

                    Assert.assertNotNull(adminFromDb);
                    Assert.assertTrue(adminFromDb.getName().equals(name));

                    created++;
                }
            } finally {
                Assert.assertEquals(createAttempt, created);
            }
            return created;
        }
    }
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
    public void testGeneralService() {
        EntityManagerHolder entityManagerHolder = (EntityManagerHolder) applicationContext.getBean(TestDbPersistenceConfig.ENTITY_MANAGER_HOLDER);
        Assert.assertNotNull(entityManagerHolder);

        EntityManager entityManager = entityManagerHolder.getEntityManager();
        Assert.assertNotNull(entityManager);

    }

    @Test
    public void testEntityMetadataAccess() {
        DynamicEntityMetadataAccess dynamicEntityMetadataAccess = (DynamicEntityMetadataAccess)applicationContext.getBean(DynamicEntityMetadataAccess.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityMetadataAccess);

        Map<String, EntityClassTree> entityClassTrees = dynamicEntityMetadataAccess.getAllEntityClassTree();
        Assert.assertNotNull(entityClassTrees);
        Assert.assertEquals(entityClassTrees.size(), 1);
        EntityClassTree entityClassTree = entityClassTrees.getOrDefault(TPerson.class.getName(), null);
        Assert.assertEquals(TPerson.class.getName(), entityClassTree.getData().clz.getName());
    }

    @Test
    public void testDynamicEntityService1() {
        DynamicEntityService dynamicEntityService = (DynamicEntityService) applicationContext.getBean(DynamicEntityService.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityService);

        String nameFieldName = "name";

        int created = 0;
        String nameAAA = "aaa";
        int createAttemptA = 10;

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<TPerson> persons = dynamicEntityService.query(TPerson.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), 0);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
        }


        created += EntityCreateHelper.createPeopleEntityWith(dynamicEntityService, nameAAA, created, createAttemptA);

        Assert.assertTrue(created == (createAttemptA));

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<TPerson> persons = dynamicEntityService.query(TPerson.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), createAttemptA);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
            Assert.assertEquals(persons.getStartIndex(), 0);

            for(int skipLeading = 2 ; skipLeading < createAttemptA ; skipLeading ++) {
                ctoFetchAll.setFirstResult(skipLeading);
                persons = dynamicEntityService.query(TPerson.class, ctoFetchAll);

                Assert.assertEquals(persons.fetchedCount(), createAttemptA - skipLeading);
                Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
                Assert.assertEquals(persons.getStartIndex(), skipLeading);
            }
        }
    }
    @Test
    public void testDynamicEntityService() {
        DynamicEntityService dynamicEntityService = (DynamicEntityService)applicationContext.getBean(DynamicEntityService.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityService);

        String nameFieldName = "name";

        int created = 0;
        String nameAAA = "aaa";
        String nameBBB = "bbb";
        int createAttemptA = 100;
        int createAttemptB = 200;

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<TPerson> persons = dynamicEntityService.query(TPerson.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), 0);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
        }


        created += EntityCreateHelper.createPeopleEntityWith(dynamicEntityService, nameAAA, created, createAttemptA);
        created += EntityCreateHelper.createPeopleEntityWith(dynamicEntityService, nameBBB, created, createAttemptB);

        Assert.assertTrue(created == (createAttemptA + createAttemptB));

//        EntityCreateHelper.createPeopleEntityWith(dynamicEntityService, "admin", 0, createAttempt);
        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<TPerson> persons = dynamicEntityService.query(TPerson.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), CriteriaTransferObject.SINGLE_QUERY_DEFAULT_RESULT_COUNT);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
        }

        {
            //Test count A
            CriteriaTransferObject ctoFetchA = new CriteriaTransferObject();
            ctoFetchA.addFilterCriteria(new PropertyFilterCriteria(nameFieldName).addFilterValue(nameAAA));
            CriteriaQueryResult<TPerson> persons = dynamicEntityService.query(TPerson.class, ctoFetchA);

            Assert.assertEquals(persons.fetchedCount(), CriteriaTransferObject.SINGLE_QUERY_DEFAULT_RESULT_COUNT);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(createAttemptA));
        }

        {
            //Test count B
            CriteriaTransferObject ctoFetchB = new CriteriaTransferObject();
            ctoFetchB.addFilterCriteria(new PropertyFilterCriteria(nameFieldName).addFilterValue(nameBBB));
            CriteriaQueryResult<TPerson> persons = dynamicEntityService.query(TPerson.class, ctoFetchB);

            Assert.assertEquals(persons.fetchedCount(), CriteriaTransferObject.SINGLE_QUERY_DEFAULT_RESULT_COUNT);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(createAttemptB));
        }

        {
            //query asc,
            int requestCount = 25;
            // start = 0
            {
                CriteriaTransferObject ctoFetchFirst25AAA = new CriteriaTransferObject()
                        .setMaxResultCount(requestCount)
                        .addSortCriteria(new PropertySortCriteria(nameFieldName, SortDirection.ASCENDING));
                {
                    CriteriaQueryResult<TPerson> personsAAA = dynamicEntityService.query(TPerson.class, ctoFetchFirst25AAA);

                    Assert.assertEquals(personsAAA.fetchedCount(), requestCount);
                    Assert.assertEquals(personsAAA.getTotalCount(), Long.valueOf(created));
                    for (TPerson person : personsAAA.getEntityCollection()) {
                        Assert.assertTrue(person.getName().startsWith(nameAAA));
                    }
                }
            }
            {
                // start = createAttemptA (100)
                int startIndex = createAttemptA;
                //After index 100, result should be BBB
                CriteriaTransferObject ctoFetchFirst25AfterAAA = new CriteriaTransferObject()
                        .setMaxResultCount(requestCount)
                        .setFirstResult(startIndex)
                        .addSortCriteria(new PropertySortCriteria(nameFieldName, SortDirection.ASCENDING));
                {
                    CriteriaQueryResult<TPerson> personsAAA = dynamicEntityService.query(TPerson.class, ctoFetchFirst25AfterAAA);

                    Assert.assertEquals(personsAAA.fetchedCount(), requestCount);
                    Assert.assertEquals(personsAAA.getTotalCount(), Long.valueOf(created));
                    for (TPerson person : personsAAA.getEntityCollection()) {
                        Assert.assertTrue(person.getName().startsWith(nameBBB));
                    }
                }
            }

        }
        {
            //query desc,
            int requestCount = 25;
            {
                // start = 0
                CriteriaTransferObject ctoFetchFirst25BBB = new CriteriaTransferObject().setMaxResultCount(requestCount)
                        .addSortCriteria(new PropertySortCriteria(nameFieldName, SortDirection.DESCENDING));
                {
                    CriteriaQueryResult<TPerson> personsBBB = dynamicEntityService.query(TPerson.class, ctoFetchFirst25BBB);

                    Assert.assertEquals(personsBBB.fetchedCount(), requestCount);
                    Assert.assertEquals(personsBBB.getTotalCount(), Long.valueOf(created));
                    for (TPerson person : personsBBB.getEntityCollection()) {
                        Assert.assertTrue(person.getName().startsWith(nameBBB));
                    }
                }
            }
            {
                // start = createAttemptB
                int startIndex = createAttemptB;
                CriteriaTransferObject ctoFetchFirst25BBB = new CriteriaTransferObject()
                        .setMaxResultCount(requestCount)
                        .setFirstResult(startIndex)
                        .addSortCriteria(new PropertySortCriteria(nameFieldName, SortDirection.DESCENDING));
                {
                    CriteriaQueryResult<TPerson> personsBBB = dynamicEntityService.query(TPerson.class, ctoFetchFirst25BBB);

                    Assert.assertEquals(personsBBB.fetchedCount(), requestCount);
                    Assert.assertEquals(personsBBB.getTotalCount(), Long.valueOf(created));
                    for (TPerson person : personsBBB.getEntityCollection()) {
                        Assert.assertTrue(person.getName().startsWith(nameAAA));
                    }
                }

            }
        }

        //remove all BBB
        {
            int startIndex = 0;
            int eachQuerySize = 12;
            int fullPageCount = 0;
            int returned = 0;
            List<TPerson> cache = new ArrayList<TPerson>();
            do{
                CriteriaTransferObject ctoFetchB = new CriteriaTransferObject().setFirstResult(startIndex).setMaxResultCount(eachQuerySize);
                ctoFetchB.addFilterCriteria(new PropertyFilterCriteria(nameFieldName).addFilterValue(nameBBB));
                CriteriaQueryResult<TPerson> persons = dynamicEntityService.query(TPerson.class, ctoFetchB);

                if(persons.fetchedCount() == eachQuerySize){
                    fullPageCount ++;
                }
                returned+= persons.fetchedCount();
                startIndex = returned;

                cache.addAll(persons.getEntityCollection());

                Assert.assertTrue(returned <= createAttemptB);
                if(returned >= createAttemptB){
                    break;
                }
            }while (true);

            Assert.assertTrue(fullPageCount == (createAttemptB / eachQuerySize));
            Assert.assertTrue(returned == createAttemptB);

            for(TPerson p : cache){
                dynamicEntityService.delete(p);
            }

            {
                CriteriaTransferObject ctoFetchB = new CriteriaTransferObject();
                ctoFetchB.addFilterCriteria(new PropertyFilterCriteria(nameFieldName).addFilterValue(nameBBB));
                CriteriaQueryResult<TPerson> persons = dynamicEntityService.query(TPerson.class, ctoFetchB);

                Assert.assertEquals(persons.getTotalCount().intValue(), 0);
            }

            {
                CriteriaTransferObject ctoFetchA = new CriteriaTransferObject();
                ctoFetchA.addFilterCriteria(new PropertyFilterCriteria(nameFieldName).addFilterValue(nameAAA));
                CriteriaQueryResult<TPerson> persons = dynamicEntityService.query(TPerson.class, ctoFetchA);

                Assert.assertEquals(persons.getTotalCount().intValue(), createAttemptA);
            }
        }


    }
}
