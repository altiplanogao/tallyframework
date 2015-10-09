package com.taoswork.tallybook.admincore;

import com.taoswork.tallybook.admincore.conf.AdminCoreTestConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TallyBookAdminCoreTest {
    @Test
    public void testCore(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AdminCoreTestConfig.class);
        applicationContext.refresh();
    }
}
