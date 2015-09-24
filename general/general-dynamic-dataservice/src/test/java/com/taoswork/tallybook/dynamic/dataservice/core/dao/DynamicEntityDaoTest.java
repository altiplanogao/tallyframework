package com.taoswork.tallybook.dynamic.dataservice.core.dao;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityCreateHelper;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.*;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.testframework.domain.TPerson;
import com.taoswork.tallybook.testframework.domain.impl.TPersonImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class DynamicEntityDaoTest {

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
    public void testDynamicEntityDao() throws ServiceException{
        DynamicEntityService dynamicEntityService = dataService.getService(DynamicEntityService.COMPONENT_NAME);
        DynamicEntityDao dynamicEntityDao = dataService.getService(DynamicEntityDao.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityDao);

        String nameFieldName = "name";

        int created = 0;
        String nameAAA = "aaa";
        int createAttemptA = 10;

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<TPersonImpl> persons = dynamicEntityDao.query(TPersonImpl.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), 0);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
        }


        created += EntityCreateHelper.createPeopleEntityWith(dynamicEntityService, nameAAA, created, createAttemptA);

        Assert.assertTrue(created == (createAttemptA));

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<TPersonImpl> persons = dynamicEntityDao.query(TPersonImpl.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), createAttemptA);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
            Assert.assertEquals(persons.getStartIndex(), 0);

            for(int skipLeading = 2 ; skipLeading < createAttemptA ; skipLeading ++) {
                ctoFetchAll.setFirstResult(skipLeading);
                persons = dynamicEntityDao.query(TPersonImpl.class, ctoFetchAll);

                Assert.assertEquals(persons.fetchedCount(), createAttemptA - skipLeading);
                Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
                Assert.assertEquals(persons.getStartIndex(), skipLeading);
            }
        }
    }
}
