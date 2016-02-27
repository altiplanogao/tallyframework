package com.taoswork.tallybook.testmaterial.jpa;

import com.taoswork.tallybook.testmaterial.jpa.persistence.TestApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class EnsureTestApplicationContextCreateable {

    @Test
    public void testCreateDb() {
        try {
            ApplicationContext applicationContext =
                TestApplicationContext.getApplicationContext(TestDbPersistenceConfig.class);
            Assert.assertTrue(true);
            applicationContext = null;
        } catch (Exception exp) {
            Assert.fail();
        }
    }
}
