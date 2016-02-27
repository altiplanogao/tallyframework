package com.taoswork.tallybook.descriptor.description.service;

import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typedcollection.BasicCollectionFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typedcollection.EntityCollectionFieldInfo;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.PhoneNumberByType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetaInfoServiceTest_Fields_Map extends MetaInfoServiceTest_Fields_Base {

    @Test
    public void testPrimitiveMap() {
        IFieldInfo fieldInfo = employeeInfo.getField("phoneNumbers");
        Assert.assertNotNull(fieldInfo);
        BasicCollectionFieldInfo mapFieldInfo = (BasicCollectionFieldInfo) fieldInfo;
        Assert.assertEquals(PhoneNumberByType.class.getName(), mapFieldInfo.getInstanceType());
    }

//    @Test
//    public void testEmbeddedMap() {
//        MapFieldInfo employeesByNameXInfo = (MapFieldInfo) departmentInfo.getField("employeesByNameX");
//        assertValidMapFieldInfo(employeesByNameXInfo, departmentInfo, EmployeeNameX.class, EmployeeImpl.class);
//    }


    @Test
    public void testEntityMap() {
        {
            EntityCollectionFieldInfo employeesByNameFm = (EntityCollectionFieldInfo) departmentInfo.getField("employeesByName");
            Assert.assertNotNull(employeesByNameFm);

//            assertValidMapFieldInfo(employeesByNameFm, departmentInfo, EmployeeName.class, EmployeeImpl.class);
        }
        {
            EntityCollectionFieldInfo employeesFm = (EntityCollectionFieldInfo) departmentInfo.getField("employeesByUnTypedId");
            Assert.assertNotNull(employeesFm);
//            assertValidMapFieldInfo(employeesFm, departmentInfo, null, EmployeeImpl.class);
        }
        {
            EntityCollectionFieldInfo employeesFm = (EntityCollectionFieldInfo) departmentInfo.getField("employeesByUnTypedName");
            Assert.assertNotNull(employeesFm);
//            assertValidMapFieldInfo(employeesFm, departmentInfo, null, EmployeeImpl.class);
        }
    }
}