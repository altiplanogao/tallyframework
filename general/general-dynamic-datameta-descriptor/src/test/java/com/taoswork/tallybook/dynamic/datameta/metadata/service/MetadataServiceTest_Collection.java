package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/11/13.
 */
public class MetadataServiceTest_Collection {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataServiceTest.class);
    private MetadataService metadataService;

    @Before
    public void setup() {
        metadataService = new MetadataServiceImpl();
    }

    @After
    public void teardown() {
        metadataService = null;
    }

    @Test
    public void testCollectionTypeSimple(){
        IClassMetadata employeeCm = metadataService.generateMetadata(EmployeeImpl.class, null, true);
        IFieldMetadata nickNameListFm = employeeCm.getFieldMetadata("nickNameList");
    }

    @Test
    public void testCollectionTypeEmbeddable(){
        IClassMetadata employeeCm = metadataService.generateMetadata(EmployeeImpl.class, null, true);
        IFieldMetadata addressesFm = employeeCm.getFieldMetadata("addresses");
    }

    @Test
    public void testCollectionTypeEntity(){

    }

    @Test
    public void testCollectionTypeLookup(){
        IClassMetadata employeeCm = metadataService.generateMetadata(EmployeeImpl.class, null, true);
        IFieldMetadata nickNameListFm = employeeCm.getFieldMetadata("projects");

    }

    @Test
    public void testCollectionTypeAdornedLookup(){

    }
}
