package com.taoswork.tallybook.dataservice.jpa.core.dao;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.jpa.config.db.TestDbConfig;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.EntityCreateHelper;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.JpaEntityService;
import com.taoswork.tallybook.dataservice.jpa.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.general.solution.time.MethodTimeCounter;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.impl.ZooKeeperImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityDaoTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityDaoTest.class);

    private IDataService dataService = null;

    @Before
    public void setup() {
        dataService = new TallyMockupDataService(TestDbConfig.class);
    }

    @After
    public void teardown() {
        dataService = null;
    }

    @Test
    public void testDynamicEntityDao() throws ServiceException {
        MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER);

//        JpaEntityService entityService = dataService.getService(IEntityService.COMPONENT_NAME);
        EntityDao entityDao = dataService.getService(EntityDao.COMPONENT_NAME);
        Assert.assertNotNull(entityDao);

        String nameFieldName = "name";

        int created = 0;
        String nameAAA = "aaa";
        int createAttemptA = 10;

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<ZooKeeperImpl> persons = entityDao.query(ZooKeeperImpl.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), 0);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
        }


        created += EntityCreateHelper.createPeopleEntityWith(dataService, nameAAA, created, createAttemptA);

        Assert.assertTrue(created == (createAttemptA));

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<ZooKeeperImpl> persons = entityDao.query(ZooKeeperImpl.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), createAttemptA);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
            Assert.assertEquals(persons.getStartIndex(), 0);

            for (int skipLeading = 2; skipLeading < createAttemptA; skipLeading++) {
                ctoFetchAll.setFirstResult(skipLeading);
                persons = entityDao.query(ZooKeeperImpl.class, ctoFetchAll);

                Assert.assertEquals(persons.fetchedCount(), createAttemptA - skipLeading);
                Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
                Assert.assertEquals(persons.getStartIndex(), skipLeading);
            }
        }
        methodTimeCounter.noticeOnExit();
    }
}
