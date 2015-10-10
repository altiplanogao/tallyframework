package com.taoswork.tallybook.admincore;

import com.taoswork.tallybook.admincore.conf.AdminCoreTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TallyBookAdminCoreTest {
    @Test
    public void testCore() {
        try {
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
            applicationContext.register(AdminCoreTestConfig.class);
            applicationContext.refresh();
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
