package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataService;
import org.junit.After;
import org.junit.Before;

/**
 * Created by Gao Yuan on 2015/11/10.
 */
public class DynamicEntityServicePerformanceTest {
    private IDataService dataService = null;

    @Before
    public void setup() {
        dataService = new TallyMockupDataService();
    }

    @After
    public void teardown() {
        dataService = null;
    }

}
