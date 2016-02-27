package com.taoswork.tallybook.descriptor.description.service;

import com.taoswork.tallybook.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallybook.descriptor.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.descriptor.service.MetaInfoService;
import com.taoswork.tallybook.descriptor.service.MetaService;
import org.junit.After;
import org.junit.Before;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MetaInfoServiceTest_CompanyImpl {
    private MetaService metaService;
    private MetaInfoService metaInfoService;

    @Before
    public void setup() {
        metaService = new JpaMetaServiceImpl();
        metaInfoService = new MetaInfoServiceImpl();
    }

    @After
    public void teardown() {
        metaService = null;
        metaInfoService = null;
    }


}
