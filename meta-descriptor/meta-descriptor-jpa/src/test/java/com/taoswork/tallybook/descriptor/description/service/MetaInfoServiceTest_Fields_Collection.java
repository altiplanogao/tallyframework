package com.taoswork.tallybook.descriptor.description.service;

import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.StringEntry;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typedcollection.PrimitiveCollectionFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typedcollection._CollectionFieldInfo;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.embed.VacationEntry;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.NicknameEntry;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetaInfoServiceTest_Fields_Collection extends MetaInfoServiceTest_Fields_Base {

    @Test
    public void testPrimitiveCollection() {
        PrimitiveCollectionFieldInfo nickFmInTypedSet = (PrimitiveCollectionFieldInfo) employeeInfo.getField("nickNameSet");
        PrimitiveCollectionFieldInfo nickFmInSet = (PrimitiveCollectionFieldInfo) employeeInfo.getField("nickNameSetNonType");
        PrimitiveCollectionFieldInfo nickFmInList = (PrimitiveCollectionFieldInfo) employeeInfo.getField("nickNameList");
//        _CollectionFieldInfo nickFmInArray = (_CollectionFieldInfo) employeeInfo.getField("nickNameArray");

        assertValidCollectionFieldInfo(nickFmInTypedSet, employeeInfo, StringEntry.class);
        assertValidCollectionFieldInfo(nickFmInSet, employeeInfo, NicknameEntry.class);
        assertValidCollectionFieldInfo(nickFmInList, employeeInfo, StringEntry.class);
//        assertValidCollectionFieldInfo(nickFmInArray, employeeInfo, NicknameEntry.class);
    }

    @Test
    public void testEmbeddedCollection() {
        _CollectionFieldInfo vacationBookingsInfo = (_CollectionFieldInfo) employeeInfo.getField("vacationBookings");
        assertValidCollectionFieldInfo(vacationBookingsInfo, employeeInfo, VacationEntry.class);
    }

    @Test
    public void testEntityCollection() {
        _CollectionFieldInfo departmentEmployeeFm = (_CollectionFieldInfo) departmentInfo.getField("employees");
        assertValidCollectionFieldInfo(departmentEmployeeFm, departmentInfo, EmployeeImpl.class);
        _CollectionFieldInfo departmentEmployeeListFm = (_CollectionFieldInfo) departmentInfo.getField("employeesList");
        assertValidCollectionFieldInfo(departmentEmployeeListFm, departmentInfo, EmployeeImpl.class);
    }
}