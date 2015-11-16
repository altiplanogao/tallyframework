package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedmap.MapFieldInfo;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.StringEntryDelegate;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeName;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeNameX;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testframework.domain.common.PhoneTypeEntryDelegate;
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
        MapFieldInfo mapFieldInfo = (MapFieldInfo)fieldInfo;
        assertValidMapFieldInfo(mapFieldInfo, employeeInfo, PhoneTypeEntryDelegate.class, StringEntryDelegate.class);
    }

    @Test
    public void testEmbeddedMap() {
        MapFieldInfo employeesByNameXInfo = (MapFieldInfo) departmentInfo.getField("employeesByNameX");
        assertValidMapFieldInfo(employeesByNameXInfo, departmentInfo, EmployeeNameX.class, EmployeeImpl.class);
    }


    @Test
    public void testEntityMap() {
        {
            MapFieldInfo employeesByNameFm = (MapFieldInfo) departmentInfo.getField("employeesByName");
            assertValidMapFieldInfo(employeesByNameFm, departmentInfo, EmployeeName.class, EmployeeImpl.class);
        }
        {
            MapFieldInfo employeesFm = (MapFieldInfo) departmentInfo.getField("employeesByUnTypedId");
            assertValidMapFieldInfo(employeesFm, departmentInfo, null, EmployeeImpl.class);
        }
        {
            MapFieldInfo employeesFm = (MapFieldInfo) departmentInfo.getField("employeesByUnTypedName");
            assertValidMapFieldInfo(employeesFm, departmentInfo, null, EmployeeImpl.class);
        }
    }
}