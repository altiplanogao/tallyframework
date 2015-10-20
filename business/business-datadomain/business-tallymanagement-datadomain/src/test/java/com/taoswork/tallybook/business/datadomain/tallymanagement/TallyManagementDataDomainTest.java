package com.taoswork.tallybook.business.datadomain.tallymanagement;

import com.taoswork.tallybook.testframework.persistence.TestApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

/**
 * Created by Gao Yuan on 2015/10/19.
 */
public class TallyManagementDataDomainTest {
    @Test
    public void testCreateDb(){
        try {
            ApplicationContext applicationContext =
                TestApplicationContext.getApplicationContext(TallyManagementDbPersistenceConfig.class);
            Assert.assertTrue(true);
            applicationContext = null;
        } catch (Exception exp) {
            Assert.fail();
        }

    }
}
