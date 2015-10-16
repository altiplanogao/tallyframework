package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.testframework.domain.business.impl.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetadataServiceTest_Cache {
    private MetadataService metadataService;
    @Before
    public void setup(){
        metadataService = new MetadataServiceImpl();
    }

    @After
    public void teardown(){
        metadataService = null;
    }

    @Test
    public void testClassMetadataCache_Company(){
        ClassMetadata classMetadata = metadataService.generateMetadata(CompanyImpl.class);
        Assert.assertTrue(metadataService.isMetadataCached(CompanyImpl.class));
        Assert.assertFalse(metadataService.isMetadataCached(DepartmentImpl.class));
        Assert.assertFalse(metadataService.isMetadataCached(EmployeeImpl.class));
        Assert.assertFalse(metadataService.isMetadataCached(ParkingSpaceImpl.class));
        Assert.assertFalse(metadataService.isMetadataCached(ProjectImpl.class));

        classMetadata = metadataService.generateMetadata(DepartmentImpl.class);
        Assert.assertTrue(metadataService.isMetadataCached(CompanyImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(DepartmentImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(EmployeeImpl.class));
        Assert.assertFalse(metadataService.isMetadataCached(ParkingSpaceImpl.class));
        Assert.assertFalse(metadataService.isMetadataCached(ProjectImpl.class));
    }

    @Test
    public void testClassMetadataCache_Department(){
        ClassMetadata classMetadata = metadataService.generateMetadata(DepartmentImpl.class);
        Assert.assertFalse(metadataService.isMetadataCached(CompanyImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(DepartmentImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(EmployeeImpl.class));
        Assert.assertFalse(metadataService.isMetadataCached(ParkingSpaceImpl.class));
        Assert.assertFalse(metadataService.isMetadataCached(ProjectImpl.class));

        classMetadata = metadataService.generateMetadata(EmployeeImpl.class);
        Assert.assertFalse(metadataService.isMetadataCached(CompanyImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(DepartmentImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(EmployeeImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(ParkingSpaceImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(ProjectImpl.class));
    }

    @Test
    public void testClassMetadataCache_Employee(){
        ClassMetadata classMetadata = metadataService.generateMetadata(EmployeeImpl.class);
        Assert.assertFalse(metadataService.isMetadataCached(CompanyImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(DepartmentImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(EmployeeImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(ParkingSpaceImpl.class));
        Assert.assertTrue(metadataService.isMetadataCached(ProjectImpl.class));
    }

}
