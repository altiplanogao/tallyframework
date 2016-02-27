package com.taoswork.tallybook.descriptor.metadata.service;

import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.service.MetaService;
import com.taoswork.tallybook.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetaServiceTest_Cache {
    private MetaService metaService;

    @Before
    public void setup() {
        metaService = new JpaMetaServiceImpl();
    }

    @After
    public void teardown() {
        metaService = null;
    }

    @Test
    public void testClassMetadataCache_Company() {
        IClassMeta classMeta = metaService.generateMeta(CompanyImpl.class, null);
        Assert.assertTrue(metaService.isMetadataCached(CompanyImpl.class));
        Assert.assertFalse(metaService.isMetadataCached(DepartmentImpl.class));
        Assert.assertFalse(metaService.isMetadataCached(EmployeeImpl.class));
        Assert.assertFalse(metaService.isMetadataCached(ParkingSpaceImpl.class));
        Assert.assertFalse(metaService.isMetadataCached(ProjectImpl.class));

        classMeta = metaService.generateMeta(DepartmentImpl.class, null);
        Assert.assertTrue(metaService.isMetadataCached(CompanyImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(DepartmentImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(EmployeeImpl.class));
        Assert.assertFalse(metaService.isMetadataCached(ParkingSpaceImpl.class));
        Assert.assertFalse(metaService.isMetadataCached(ProjectImpl.class));
    }

    @Test
    public void testClassMetadataCache_Department() {
        IClassMeta classMeta = metaService.generateMeta(DepartmentImpl.class, null);
        Assert.assertFalse(metaService.isMetadataCached(CompanyImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(DepartmentImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(EmployeeImpl.class));
        Assert.assertFalse(metaService.isMetadataCached(ParkingSpaceImpl.class));
        Assert.assertFalse(metaService.isMetadataCached(ProjectImpl.class));

        classMeta = metaService.generateMeta(EmployeeImpl.class, null);
        Assert.assertFalse(metaService.isMetadataCached(CompanyImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(DepartmentImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(EmployeeImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(ParkingSpaceImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(ProjectImpl.class));
    }

    @Test
    public void testClassMetadataCache_Employee() {
        IClassMeta classMeta = metaService.generateMeta(EmployeeImpl.class, null);
        Assert.assertFalse(metaService.isMetadataCached(CompanyImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(DepartmentImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(EmployeeImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(ParkingSpaceImpl.class));
        Assert.assertTrue(metaService.isMetadataCached(ProjectImpl.class));
    }

}
