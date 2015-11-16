package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection._CollectionFieldInfo;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.StringEntryDelegate;
import com.taoswork.tallybook.testframework.domain.business.embed.VacationEntry;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.NicknameEntryDelegate;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetaInfoServiceTest_Fields_Collection extends MetaInfoServiceTest_Fields_Base {

    @Test
    public void testPrimitiveCollection() {
        _CollectionFieldInfo nickFmInTypedSet = (_CollectionFieldInfo) employeeInfo.getField("nickNameSet");
        _CollectionFieldInfo nickFmInSet = (_CollectionFieldInfo) employeeInfo.getField("nickNameSetNonType");
        _CollectionFieldInfo nickFmInList = (_CollectionFieldInfo) employeeInfo.getField("nickNameList");
//        _CollectionFieldInfo nickFmInArray = (_CollectionFieldInfo) employeeInfo.getField("nickNameArray");

        assertValidCollectionFieldInfo(nickFmInTypedSet, employeeInfo, StringEntryDelegate.class);
        assertValidCollectionFieldInfo(nickFmInSet, employeeInfo, NicknameEntryDelegate.class);
        assertValidCollectionFieldInfo(nickFmInList, employeeInfo, StringEntryDelegate.class);
//        assertValidCollectionFieldInfo(nickFmInArray, employeeInfo, NicknameEntryDelegate.class);
    }

    @Test
    public void testEmbeddedCollection() {
        _CollectionFieldInfo vacationBookingsInfo = (_CollectionFieldInfo) employeeInfo.getField("vacationBookings");
        assertValidCollectionFieldInfo(vacationBookingsInfo, employeeInfo, VacationEntry.class);
    }

    @Test
    public void testEntityCollection() {
        _CollectionFieldInfo departmentEmployeeFm = (_CollectionFieldInfo) departmentInfo.getField("employees");
        assertValidCollectionFieldInfo(departmentEmployeeFm,departmentInfo,EmployeeImpl.class );
        _CollectionFieldInfo departmentEmployeeListFm = (_CollectionFieldInfo) departmentInfo.getField("employeesList");
        assertValidCollectionFieldInfo(departmentEmployeeListFm,departmentInfo,EmployeeImpl.class );
    }
}