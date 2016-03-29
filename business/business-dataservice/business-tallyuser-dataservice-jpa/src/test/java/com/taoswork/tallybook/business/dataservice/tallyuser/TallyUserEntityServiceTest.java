package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonImpl;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonCertificationDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.conf.TallyUserJpaDatasourceDefinition;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.tallyuser.PersonService;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.core.entityprotect.validate.EntityValueValidationException;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.jpa.config.db.TestDbConfig;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.JpaEntityService;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.descriptor.service.MetaInfoService;
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
        dataService = new TallyUserDataService(TestDbConfig.class);
    }

    @After
    public void tearDown() {
        dataService = null;
    }

    @Test
    public void testDao() {
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
        Object object = dataService.getService(TallyUserJpaDatasourceDefinition.TUSER_TRANSACTION_MANAGER_NAME);

        Assert.assertTrue(personService != null);

        Person adminP = personService.readPersonByAnyIdentity("admin");
        PersonCertification adminPC = personService.readPersonCertificationByUUID(adminP.getUuid());
        Assert.assertEquals(adminP.getUuid(), adminPC.getUserCode());

        int createAttempt = 10;
        int created = 0;
        try {
            //idInitialValue, base on PersonImpl.id's annotation: @TableGenerator's initialValue + 1
            int idInitialValue = 1;
            for (int i = 0; i < createAttempt; ++i) {
                int expected = i + idInitialValue;
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
    public void testMetaDescriptionService() {
        MetaInfoService metaDescriptionService = dataService.getService(MetaInfoService.SERVICE_NAME);
        Assert.assertNotNull(metaDescriptionService);
    }

    @Test
    public void testDynamicEntityService() {
        JpaEntityService entityService = dataService.getService(IEntityService.COMPONENT_NAME);
        Assert.assertNotNull(entityService);
        try {
            Person admin = new PersonImpl();
            admin.setName("admin").setUuid(UUID.randomUUID().toString());
            entityService.create(admin);
            Assert.fail();
        } catch (ServiceException e) {
            if (!(e instanceof EntityValueValidationException)) {
                Assert.fail();
            }
        }

        long existingCount = 0;
        try {
            Person personImp = entityService.straightRead(PersonImpl.class, Long.valueOf(-1L));
            Person person = entityService.straightRead(Person.class, Long.valueOf(-1L));
            Assert.assertEquals(person.getUuid(), personImp.getUuid());
            Assert.assertEquals(person.getId(), personImp.getId());

            Assert.assertNotNull(person);
            Assert.assertTrue(person.getName().equals("admin")); //Loaded from load_person.xml

            CriteriaQueryResult<Person> personsExisting = entityService.query(Person.class, new CriteriaTransferObject());
            existingCount = personsExisting.getTotalCount();
            Assert.assertTrue(existingCount >= 1);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage());
        } finally {
        }

        int createAttempt = 10;
        int created = 0;
        try {
            //idInitialValue, base on PersonImpl.id's annotation: @TableGenerator's initialValue + 1
            int idInitialValue = 1;
            for (int i = 0; i < createAttempt; ++i) {
                int expected = i + idInitialValue;
                Person admin = new PersonImpl();
                admin.setName("admin").setUuid(UUID.randomUUID().toString());
                admin.setMobile("1234567890" + (1000 + i));
                entityService.create(admin);

                Long id = admin.getId();
                Person adminFromDb = entityService.straightRead(Person.class, Long.valueOf(id));

                Assert.assertTrue("Created and Read should be same: " + i, admin.getId() == adminFromDb.getId());
                Assert.assertTrue("Created Object [" + admin.getId() + "] should have Id: " + expected, admin.getId().equals(0L + expected));
                created++;
            }
        } catch (ServiceException e) {
            Assert.fail(e.getMessage());
        } finally {
            Assert.assertEquals(createAttempt, created);
        }

        try {
            CriteriaQueryResult<Person> persons = entityService.query(Person.class, new CriteriaTransferObject());
            Assert.assertEquals(persons.getTotalCount().longValue(), existingCount + createAttempt);
        } catch (ServiceException e) {
            Assert.fail(e.getMessage());
        }
    }
}