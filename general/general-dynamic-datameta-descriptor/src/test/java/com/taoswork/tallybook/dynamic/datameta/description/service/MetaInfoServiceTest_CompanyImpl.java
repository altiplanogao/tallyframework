package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import org.junit.After;
import org.junit.Before;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MetaInfoServiceTest_CompanyImpl {
    private MetadataService metadataService;
    private MetaInfoService metaInfoService;

    @Before
    public void setup() {
        metadataService = new MetadataServiceImpl();
        metaInfoService = new MetaInfoServiceImpl();
    }

    @After
    public void teardown() {
        metadataService = null;
        metaInfoService = null;
    }


}
