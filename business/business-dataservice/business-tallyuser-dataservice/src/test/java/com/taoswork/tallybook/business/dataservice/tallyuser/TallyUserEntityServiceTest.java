package com.taoswork.tallybook.business.dataservice.tallyuser;

import com.taoswork.tallybook.business.datadomain.tallyuser.Gender;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonImpl;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonCertificationDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.dao.PersonDao;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.tallyuser.PersonService;
import com.taoswork.tallybook.datadomain.onmongo.PersistableDocument;
import com.taoswork.tallybook.dataservice.config.IDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.core.entityprotect.validate.EntityValueValidationException;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.mongo.config.TestDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.descriptor.service.MetaInfoService;
import org.bson.types.ObjectId;
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
        dataService = new TallyUserDataService(TestDatasourceConfiguration.class);
    }

    @After
    public void tearDown() {
        TestDatasourceConfiguration.DatasourceDefinition mdbDef = dataService.getService(IDatasourceConfiguration.DATA_SOURCE_PATH_DEFINITION);
        mdbDef.dropDatabase();
        dataService = null;
    }
//
//    @Test
//    public void testCreate() throws ServiceException {
//        IEntityService<PersistableDocument> es = dataService.getService(IEntityService.COMPONENT_NAME);
//        for(int i = 0 ; i < 67 ; i ++) {
//            Person p = new PersonImpl();
//            p.setName("admin" + i).setGender(Gender.male).setEmail("admin_" + i + "@aa.com").setMobile("12345678901" + i);
//            es.create(p);
//        }
//    }

    private final static String AdminId = "56f8c90cc98d9124e8cc1752";

    @Test
    public void testDao() {
        PersonDao personDao = dataService.getService(PersonDao.COMPONENT_NAME);
        Assert.assertNotNull(personDao);
        Person xx = personDao.readPersonById(AdminId);
        Assert.assertTrue(xx.getName().equals("admin")); //Loaded from load_person.xml

        PersonCertificationDao personCertificationDao = dataService.getService(PersonCertificationDao.COMPONENT_NAME);
        Assert.assertNotNull(personCertificationDao);
        PersonCertification pc = personCertificationDao.readPersonCertificationById(AdminId);
        Assert.assertEquals(pc.getUserCode(), xx.getUuid());
    }

    @Test
    public void testDataServices() {

        PersonService personService = dataService.getService(PersonService.SERVICE_NAME);
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

                ObjectId id = admin.getId();
                Person adminFromDb = personService.readPersonByID(id.toHexString());

                Assert.assertTrue("Created and Read should be same: " + i, admin.getId().equals(adminFromDb.getId()));
//                Assert.assertTrue("Created Object [" + admin.getId() + "] should have Id: " + expected, admin.getId().equals(0L + expected));
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
        IEntityService<PersistableDocument> entityService = dataService.getService(IEntityService.COMPONENT_NAME);
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
            Person personImp = entityService.straightRead(PersonImpl.class, AdminId);
            Person person = entityService.straightRead(Person.class, AdminId);
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

                ObjectId id = admin.getId();
                Person adminFromDb = entityService.straightRead(Person.class, id);

                Assert.assertTrue("Created and Read should be same: " + i, admin.getId().equals(adminFromDb.getId()));
//                Assert.assertTrue("Created Object [" + admin.getId() + "] should have Id: " + expected, admin.getId().equals(0L + expected));
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