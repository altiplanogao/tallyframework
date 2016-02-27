package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.testmaterial.jpa.persistence.TestApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

/**
 * Created by Gao Yuan on 2015/10/19.
 */
public class TallyUserDataDomainTest {
    @Test
    public void testCreateDb() {
        try {
            ApplicationContext applicationContext =
                    TestApplicationContext.getApplicationContext(TallyUserDbPersistenceConfig.class);
            Assert.assertTrue(true);
            applicationContext = null;
        } catch (Exception exp) {
            Assert.fail();
        }

    }
}
