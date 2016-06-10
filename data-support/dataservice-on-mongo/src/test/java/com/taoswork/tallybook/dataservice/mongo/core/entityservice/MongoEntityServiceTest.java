package com.taoswork.tallybook.dataservice.mongo.core.entityservice;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.config.IDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.*;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.mongo.servicemockup.TallyMockupMongoDataService;
import com.taoswork.tallybook.dataservice.mongo.servicemockup.datasource.TallyMockupMongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.general.solution.time.MethodTimeCounter;
import com.taoswork.tallybook.testmaterial.mongo.domain.zoo.ZooKeeper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MongoEntityServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoEntityServiceTest.class);

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
    public void testDynamicEntityService() throws ServiceException {
        MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER);
        IEntityService entityService = dataService.getService(IEntityService.COMPONENT_NAME);
        Assert.assertNotNull(entityService);

        String nameFieldName = "name";

        int created = 0;
        String nameAAA = "aaa";
        int createAttemptA = 10;

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<ZooKeeper> persons = entityService.query(ZooKeeper.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), 0);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
        }


        created += EntityCreateHelper.createPeopleEntityWith(dataService, nameAAA, created, createAttemptA);

        Assert.assertTrue(created == (createAttemptA));

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<ZooKeeper> persons = entityService.query(ZooKeeper.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), createAttemptA);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
            Assert.assertEquals(persons.getStartIndex(), 0);

            for (int skipLeading = 2; skipLeading < createAttemptA; skipLeading++) {
                ctoFetchAll.setFirstResult(skipLeading);
                persons = entityService.query(ZooKeeper.class, ctoFetchAll);

                Assert.assertEquals(persons.fetchedCount(), createAttemptA - skipLeading);
                Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
                Assert.assertEquals(persons.getStartIndex(), skipLeading);
            }
        }
        methodTimeCounter.noticeOnExit();
    }

    @Test
    public void testDynamicEntityService_1() throws ServiceException {
        MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER);
        IEntityService entityService = dataService.getService(IEntityService.COMPONENT_NAME);
        Assert.assertNotNull(entityService);

        String nameFieldName = "name";

        int created = 0;
        String nameAAA = "aaa";
        String nameBBB = "bbb";
        int createAttemptA = 100;
        int createAttemptB = 200;

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<ZooKeeper> persons = entityService.query(ZooKeeper.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), 0);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
        }


        created += EntityCreateHelper.createPeopleEntityWith(dataService, nameAAA, created, createAttemptA);
        created += EntityCreateHelper.createPeopleEntityWith(dataService, nameBBB, created, createAttemptB);

        Assert.assertTrue(created == (createAttemptA + createAttemptB));

//        EntityCreateHelper.createPeopleEntityWith(jpaEntityService, "admin", 0, createAttempt);
        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<ZooKeeper> persons = entityService.query(ZooKeeper.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), CriteriaTransferObject.SINGLE_QUERY_DEFAULT_PAGE_SIZE);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
        }

        {
            //Test count A
            CriteriaTransferObject ctoFetchA = new CriteriaTransferObject();
            ctoFetchA.addFilterCriteria(new PropertyFilterCriteria(nameFieldName).addFilterValue(nameAAA));
            CriteriaQueryResult<ZooKeeper> persons = entityService.query(ZooKeeper.class, ctoFetchA);

            Assert.assertEquals(persons.fetchedCount(), CriteriaTransferObject.SINGLE_QUERY_DEFAULT_PAGE_SIZE);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(createAttemptA));
        }

        {
            //Test count B
            CriteriaTransferObject ctoFetchB = new CriteriaTransferObject();
            ctoFetchB.addFilterCriteria(new PropertyFilterCriteria(nameFieldName).addFilterValue(nameBBB));
            CriteriaQueryResult<ZooKeeper> persons = entityService.query(ZooKeeper.class, ctoFetchB);

            Assert.assertEquals(persons.fetchedCount(), CriteriaTransferObject.SINGLE_QUERY_DEFAULT_PAGE_SIZE);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(createAttemptB));
        }

        {
            //query asc,
            int requestCount = 25;
            // start = 0
            {
                CriteriaTransferObject ctoFetchFirst25AAA = new CriteriaTransferObject()
                        .setPageSize(requestCount)
                        .addSortCriteria(new PropertySortCriteria(nameFieldName, SortDirection.ASCENDING));
                {
                    CriteriaQueryResult<ZooKeeper> personsAAA = entityService.query(ZooKeeper.class, ctoFetchFirst25AAA);

                    Assert.assertEquals(personsAAA.fetchedCount(), requestCount);
                    Assert.assertEquals(personsAAA.getTotalCount(), Long.valueOf(created));
                    for (ZooKeeper person : personsAAA.getEntityCollection()) {
                        Assert.assertTrue(person.getName().startsWith(nameAAA));
                    }
                }
            }
            {
                // start = createAttemptA (100)
                int startIndex = createAttemptA;
                //After index 100, result should be BBB
                CriteriaTransferObject ctoFetchFirst25AfterAAA = new CriteriaTransferObject()
                        .setPageSize(requestCount)
                        .setFirstResult(startIndex)
                        .addSortCriteria(new PropertySortCriteria(nameFieldName, SortDirection.ASCENDING));
                {
                    CriteriaQueryResult<ZooKeeper> personsAAA = entityService.query(ZooKeeper.class, ctoFetchFirst25AfterAAA);

                    Assert.assertEquals(personsAAA.fetchedCount(), requestCount);
                    Assert.assertEquals(personsAAA.getTotalCount(), Long.valueOf(created));
                    for (ZooKeeper person : personsAAA.getEntityCollection()) {
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
                CriteriaTransferObject ctoFetchFirst25BBB = new CriteriaTransferObject().setPageSize(requestCount)
                        .addSortCriteria(new PropertySortCriteria(nameFieldName, SortDirection.DESCENDING));
                {
                    CriteriaQueryResult<ZooKeeper> personsBBB = entityService.query(ZooKeeper.class, ctoFetchFirst25BBB);

                    Assert.assertEquals(personsBBB.fetchedCount(), requestCount);
                    Assert.assertEquals(personsBBB.getTotalCount(), Long.valueOf(created));
                    for (ZooKeeper person : personsBBB.getEntityCollection()) {
                        Assert.assertTrue(person.getName().startsWith(nameBBB));
                    }
                }
            }
            {
                // start = createAttemptB
                int startIndex = createAttemptB;
                CriteriaTransferObject ctoFetchFirst25BBB = new CriteriaTransferObject()
                        .setPageSize(requestCount)
                        .setFirstResult(startIndex)
                        .addSortCriteria(new PropertySortCriteria(nameFieldName, SortDirection.DESCENDING));
                {
                    CriteriaQueryResult<ZooKeeper> personsBBB = entityService.query(ZooKeeper.class, ctoFetchFirst25BBB);

                    Assert.assertEquals(personsBBB.fetchedCount(), requestCount);
                    Assert.assertEquals(personsBBB.getTotalCount(), Long.valueOf(created));
                    for (ZooKeeper person : personsBBB.getEntityCollection()) {
                        Assert.assertTrue(person.getName().startsWith(nameAAA));
                    }
                }

            }
        }

        //delete all BBB
        {
            int startIndex = 0;
            int eachQuerySize = 12;
            int fullPageCount = 0;
            int returned = 0;
            List<ZooKeeper> cache = new ArrayList<ZooKeeper>();
            do {
                CriteriaTransferObject ctoFetchB = new CriteriaTransferObject().setFirstResult(startIndex).setPageSize(eachQuerySize);
                ctoFetchB.addFilterCriteria(new PropertyFilterCriteria(nameFieldName).addFilterValue(nameBBB));
                CriteriaQueryResult<ZooKeeper> persons = entityService.query(ZooKeeper.class, ctoFetchB);

                if (persons.fetchedCount() == eachQuerySize) {
                    fullPageCount++;
                }
                returned += persons.fetchedCount();
                startIndex = returned;

                cache.addAll(persons.getEntityCollection());

                Assert.assertTrue(returned <= createAttemptB);
                if (returned >= createAttemptB) {
                    break;
                }
            } while (true);

            Assert.assertTrue(fullPageCount == (createAttemptB / eachQuerySize));
            Assert.assertTrue(returned == createAttemptB);

            for (ZooKeeper p : cache) {
                entityService.delete(p);
            }

            {
                CriteriaTransferObject ctoFetchB = new CriteriaTransferObject();
                ctoFetchB.addFilterCriteria(new PropertyFilterCriteria(nameFieldName).addFilterValue(nameBBB));
                CriteriaQueryResult<ZooKeeper> persons = entityService.query(ZooKeeper.class, ctoFetchB);

                Assert.assertEquals(persons.getTotalCount().intValue(), 0);
            }

            {
                CriteriaTransferObject ctoFetchA = new CriteriaTransferObject();
                ctoFetchA.addFilterCriteria(new PropertyFilterCriteria(nameFieldName).addFilterValue(nameAAA));
                CriteriaQueryResult<ZooKeeper> persons = entityService.query(ZooKeeper.class, ctoFetchA);

                Assert.assertEquals(persons.getTotalCount().intValue(), createAttemptA);
            }
        }
        methodTimeCounter.noticeOnExit();
    }
}
