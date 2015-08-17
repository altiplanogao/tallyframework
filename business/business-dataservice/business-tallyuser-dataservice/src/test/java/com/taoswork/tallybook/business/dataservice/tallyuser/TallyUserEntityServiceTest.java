package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonImpl;
import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserTestDataServiceBeanConfiguration;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonCertificationDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.tallyuser.PersonService;
import com.taoswork.tallybook.dynamic.datameta.description.service.MetaInfoService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public class TallyUserEntityServiceTest {
    TallyUserDataService dataService = null;
    int counter = 0;

    @Before
    public void setupDataSource() {
        counter++;
        dataService = new TallyUserDataService(
                TallyUserTestDataServiceBeanConfiguration.class, null);
    }

    @After
    public void tearDown() {
        dataService = null;
    }

    @Test
    public void testDao(){
        PersonDao personDao = dataService.getService(PersonDao.COMPONENT_NAME);
        Assert.assertNotNull(personDao);
        Person xx = personDao.readPersonById(-1L);
        Assert.assertTrue(xx.getName().equals("admin")); //Loaded from load_person.xml

        PersonCertificationDao personCertificationDao = dataService.getService(PersonCertificationDao.COMPONENT_NAME);
        Assert.assertNotNull(personCertificationDao);
        PersonCertification pc = personCertificationDao.readPersonCertificationById(-1L);
        Assert.assertEquals(pc.getUserCode(), xx.getUuid());
    }

    @Test
    public void testDataServices() {

        PersonService personService = dataService.getService(PersonService.SERVICE_NAME);
        Object object = dataService.getService(TallyUserDataServiceDefinition.TUSER_TRANSACTION_MANAGER_NAME);

        Assert.assertTrue(personService != null);

        Person adminP = personService.readPersonByAnyIdentity("admin");
        PersonCertification adminPC = personService.readPersonCertificationByUUID(adminP.getUuid());
        Assert.assertEquals(adminP.getUuid(), adminPC.getUserCode());

        int createAttempt = 10;
        int created = 0;
        try {
            for (int i = 0; i < createAttempt; ++i) {

                int expected = i + 1;
                Person admin = new PersonImpl();
                admin.setName("admin").setUuid(UUID.randomUUID().toString());
                personService.savePerson(admin);

                Long id = admin.getId();
                Person adminFromDb = personService.readPersonByID(id);

                Assert.assertTrue("Created and Read should be same: " + i, admin.getId() == adminFromDb.getId());
                Assert.assertTrue("Created Object [" + admin.getId() + "] should have Id: " + expected, admin.getId().equals(0L + expected));
                created++;
            }
        } finally {
            Assert.assertEquals(createAttempt, created);
        }
    }

    @Test
    public void testMetaDescriptionService(){
        MetaInfoService metaDescriptionService = dataService.getService(MetaInfoService.SERVICE_NAME);
        Assert.assertNotNull(metaDescriptionService);
    }

    @Test
    public void testDynamicEntityService() throws ServiceException {
        DynamicEntityService dynamicEntityService = dataService.getService(DynamicEntityService.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityService);

        {
            Person personImp = dynamicEntityService.find(PersonImpl.class, Long.valueOf(-1L));
            Person person = dynamicEntityService.find(Person.class, Long.valueOf(-1L));
            Assert.assertEquals(person.getUuid(), personImp.getUuid());
            Assert.assertEquals(person.getId(), personImp.getId());

            Assert.assertNotNull(person);
            Assert.assertTrue(person.getName().equals("admin")); //Loaded from load_person.xml
        }
        CriteriaQueryResult<Person> personsExisting = dynamicEntityService.query(Person.class, new CriteriaTransferObject());
        long existingCount = personsExisting.getTotalCount();
        Assert.assertTrue(existingCount >= 1);

        int createAttempt = 10;
        int created = 0;
        try {
            for (int i = 0; i < createAttempt; ++i) {

                int expected = i + 1;
                Person admin = new PersonImpl();
                admin.setName("admin").setUuid(UUID.randomUUID().toString());
                dynamicEntityService.save(admin);

                Long id = admin.getId();
                Person adminFromDb = dynamicEntityService.find(Person.class, Long.valueOf(id));

                Assert.assertTrue("Created and Read should be same: " + i, admin.getId() == adminFromDb.getId());
                Assert.assertTrue("Created Object [" + admin.getId() + "] should have Id: " + expected, admin.getId().equals(0L + expected));
                created++;
            }
        } finally {
            Assert.assertEquals(createAttempt, created);
        }

        CriteriaQueryResult<Person> persons = dynamicEntityService.query(Person.class, new CriteriaTransferObject());
        Assert.assertEquals(persons.getTotalCount().longValue(), existingCount + createAttempt);
    }
}
