package com.taoswork.tallybook.business.dataservice.tallyadmin;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyadmin.impl.AdminEmployeeImpl;
import com.taoswork.tallybook.business.dataservice.tallyadmin.dao.AdminEmployeeDao;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.tallyadmin.AdminEmployeeService;
import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.TestDbSetting;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import org.junit.*;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public class TallyAdminDataServiceTest {
    static TallyAdminDataService dataService = null;

    @BeforeClass
    public static void setDataService() {
        dataService = new TallyAdminDataService(new TestDbSetting());
    }

    @AfterClass
    public static void tearDown() {
        dataService = null;
    }


    @Test
    public void testDataService() throws ServiceException {
        DynamicEntityService dynamicEntityService = dataService.getService(DynamicEntityService.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityService);
        CriteriaQueryResult<AdminEmployee> admins = dynamicEntityService.query(AdminEmployee.class, null);

        AdminEmployeeDao employeeDao = dataService.getService(AdminEmployeeDao.COMPONENT_NAME);
        Assert.assertNotNull(employeeDao);

        AdminEmployeeDao employeeDao1 = dataService.getService(AdminEmployeeDao.class);
        Assert.assertEquals(employeeDao, employeeDao1);

        AdminEmployeeService employeeService = dataService.getService(AdminEmployeeService.SERVICE_NAME);
        Assert.assertNotNull(employeeService);

        AdminEmployee employeeInDb = employeeService.readAdminEmployeeByPersonId(-1L);
        Assert.assertTrue(employeeInDb.getPersonId().equals(-1L));
        Assert.assertTrue(employeeInDb.getTitle().equals("master"));

        int createAttempt = 10;
        int created = 0;
        try {
            for (int i = 0; i < createAttempt; ++i) {
                int expected = i + 1;
                Long personId = i + 2L;
                AdminEmployee employee = new AdminEmployeeImpl();
                employee.setPersonId(personId);
                employee.setTitle("Title" + expected);
                employee.setName("Name" + expected);

                employeeService.saveAdminEmployee(employee);
                AdminEmployee employeeLoaded = employeeService.readAdminEmployeeByPersonId(personId);

                Assert.assertTrue(personId.equals(employeeLoaded.getPersonId()));
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
