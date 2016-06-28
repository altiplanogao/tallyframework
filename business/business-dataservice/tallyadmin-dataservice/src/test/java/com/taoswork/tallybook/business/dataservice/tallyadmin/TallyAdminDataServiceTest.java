package com.taoswork.tallybook.business.dataservice.tallyadmin;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallycheck.dataservice.mongo.config.TestDatasourceConfiguration;
import com.taoswork.tallybook.business.dataservice.tallyadmin.dao.AdminEmployeeDao;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.tallyadmin.AdminEmployeeService;
import com.taoswork.tallycheck.dataservice.config.IDatasourceConfiguration;
import com.taoswork.tallycheck.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.service.IEntityService;
import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public class TallyAdminDataServiceTest {
    static TallyAdminDataService dataService = null;

    @BeforeClass
    public static void setDataService() {
        dataService = new TallyAdminDataService(TestDatasourceConfiguration.class);
    }

    @AfterClass
    public static void tearDown() {
        TestDatasourceConfiguration.DatasourceDefinition mdbDef = dataService.getService(IDatasourceConfiguration.DATA_SOURCE_PATH_DEFINITION);
        mdbDef.dropDatabase();
        dataService = null;
    }

    @Test
    public void testDataService() throws ServiceException {
        IEntityService entityService = dataService.getService(IEntityService.COMPONENT_NAME);
        Assert.assertNotNull(entityService);
        CriteriaQueryResult<AdminEmployee> admins = entityService.query(AdminEmployee.class, null);

        AdminEmployeeDao employeeDao = dataService.getService(AdminEmployeeDao.COMPONENT_NAME);
        Assert.assertNotNull(employeeDao);

        AdminEmployeeDao employeeDao1 = dataService.getService(AdminEmployeeDao.class);
        Assert.assertEquals(employeeDao, employeeDao1);

        AdminEmployeeService employeeService = dataService.getService(AdminEmployeeService.SERVICE_NAME);
        Assert.assertNotNull(employeeService);

        String rootPersonId =  AdminEmployee.ROOT_PERSON_ID;
        AdminEmployee employeeInDb = employeeService.readAdminEmployeeByPersonId(rootPersonId);
        Assert.assertTrue(employeeInDb.getPersonId().equals(rootPersonId));
        Assert.assertTrue(employeeInDb.getTitle().equals("Master"));

        int createAttempt = 10;
        int created = 0;
        try {
            for (int i = 0; i < createAttempt; ++i) {
                int expected = i + 1;
                ObjectId personId = new ObjectId();
                AdminEmployee employee = new AdminEmployee();
                employee.setPersonId(personId.toHexString());
                employee.setTitle("Title" + expected);
                employee.setName("Name" + expected);

                employeeService.saveAdminEmployee(employee);
                AdminEmployee employeeLoaded = employeeService.readAdminEmployeeByPersonId(personId.toHexString());

                Assert.assertTrue(personId.toHexString().equals(employeeLoaded.getPersonId()));
                Assert.assertTrue(employee.getTitle().equals(employeeLoaded.getTitle()));
                Assert.assertTrue(employee.getTitle().equals("Title" + expected));

                created++;
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        } finally {
            Assert.assertEquals(createAttempt, created);
        }
    }
}
