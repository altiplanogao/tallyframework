package com.taoswork.tallybook.business.dataservice.tallybusiness;

import com.taoswork.tallybook.dynamic.dataservice.config.dbsetting.HsqlDbSetting;
import org.junit.Assert;
import org.junit.Test;

public class TallyBusinessDataServiceTest {
    @Test
    public void testCreation(){
        try {
            TallyBusinessDataService tbDataService = new TallyBusinessDataService(new HsqlDbSetting());
        }catch (Exception e){
            Assert.fail();
        }
    }
}
