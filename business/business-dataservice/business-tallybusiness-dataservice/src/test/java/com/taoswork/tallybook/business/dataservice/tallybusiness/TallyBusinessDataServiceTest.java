package com.taoswork.tallybook.business.dataservice.tallybusiness;

import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.TestDbSetting;
import org.junit.Assert;
import org.junit.Test;

public class TallyBusinessDataServiceTest {
    @Test
    public void testCreation() {
        try {
            TallyBusinessDataService tbDataService = new TallyBusinessDataService(new TestDbSetting());
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
