package com.taoswork.tallybook.testframework.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

public class EnsureTestApplicationContextCreateable {

    @Test
    public void testCreateDb() {
        try {
            ApplicationContext applicationContext = TestApplicationContext.getApplicationContext();
            Assert.assertTrue(true);
            applicationContext = null;
        } catch (Exception exp) {
            Assert.fail();
        }
    }
}