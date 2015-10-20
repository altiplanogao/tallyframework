package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.testframework.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetadataServiceTest_NoMissing {
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
    public void testClassMetadata_CompanyImpl(){
        ClassMetadata classMetadata = metadataService.generateMetadata(CompanyImpl.class);

        Map<String, IFieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        Assert.assertTrue(fieldMetadataMap.size() == NativeClassHelper.getFields(CompanyImpl.class,
                NativeClassHelper.scanAllPersistentNoSuper).size());
        int callCounter = 0;

        callCounter = assertFieldTabGroup(fieldMetadataMap, "id", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "asset", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "name", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "description", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "description2", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        assertFieldNotExist(fieldMetadataMap, "handyMemo1");
        assertFieldNotExist(fieldMetadataMap, "handyMemo2");
        callCounter = assertFieldTabGroup(fieldMetadataMap, "creationDate", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.Advanced, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "companyType", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "locked", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "active", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "taxCode", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.Private, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "adminId", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.Private, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "publicProducts", CompanyImpl.Presentation.Tab.Marketing, CompanyImpl.Presentation.Group.Public, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "privateProducts", CompanyImpl.Presentation.Tab.Marketing, CompanyImpl.Presentation.Group.Private, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "email", CompanyImpl.Presentation.Tab.Contact, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "phone", CompanyImpl.Presentation.Tab.Contact, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "address", CompanyImpl.Presentation.Tab.Contact, CompanyImpl.Presentation.Group.General, callCounter);
        Assert.assertEquals(fieldMetadataMap.size(), callCounter);

        Assert.assertNotNull(classMetadata);
    }

    @Test
    public void testClassMetadata_DepartmentImpl(){
        ClassMetadata classMetadata = metadataService.generateMetadata(DepartmentImpl.class);

        Map<String, IFieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        Assert.assertTrue(fieldMetadataMap.size() == NativeClassHelper.getFields(DepartmentImpl.class,
            NativeClassHelper.scanAllPersistentNoSuper).size());
        int callCounter = 0;

        callCounter = assertFieldTabGroup(fieldMetadataMap, "id", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "name",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "employees",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "employeesList",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "employeesByCubicle", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "employeesMap",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "employeesByName",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "employeesByNameX",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "employeesByUnTypedId",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "employeesByUnTypedName",  null, null, callCounter);
        Assert.assertEquals(fieldMetadataMap.size(), callCounter);

        Assert.assertNotNull(classMetadata);
    }

    @Test
    public void testClassMetadata_EmployeeImpl(){
        ClassMetadata classMetadata = metadataService.generateMetadata(EmployeeImpl.class);

        Map<String, IFieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
        Assert.assertTrue(fieldMetadataMap.size() == NativeClassHelper.getFields(EmployeeImpl.class,
            NativeClassHelper.scanAllPersistentNoSuper).size());
        int callCounter = 0;

        callCounter = assertFieldTabGroup(fieldMetadataMap, "id",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "name",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "firstName",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "lastName",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "nameX",  null, null, callCounter);
        assertFieldNotExist(fieldMetadataMap, "translatedName");
        callCounter = assertFieldTabGroup(fieldMetadataMap, "nickNameSet",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "nickNameSetNonType",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "nickNameList",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "nickNameArray",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "salary",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "type",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "citizenId",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "dob",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "startDate",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "comments",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "picture",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "parkingSpace",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "department",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "cube",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "projects",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "vacationBookings",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "nickNames",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "address",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "phoneNumbers",  null, null, callCounter);
        Assert.assertEquals(fieldMetadataMap.size(), callCounter);

        Assert.assertNotNull(classMetadata);
    }

    private void assertFieldNotExist(Map<String, IFieldMetadata> fieldMetadataMap,
                                    String fieldName){
        IFieldMetadata fieldMetadata = fieldMetadataMap.get(fieldName);
        Assert.assertNull(fieldMetadata);
    }

    private int assertFieldTabGroup(Map<String, IFieldMetadata> fieldMetadataMap,
                                    String fieldName, String tabName, String groupName, int callCounter){
        if(StringUtils.isEmpty(tabName)){
            tabName =  PresentationClass.Tab.DEFAULT_NAME;
        }
        if(StringUtils.isEmpty(groupName)){
            groupName =  PresentationClass.Group.DEFAULT_NAME;
        }
        IFieldMetadata fieldMetadata = fieldMetadataMap.get(fieldName);
        Assert.assertNotNull(fieldMetadata);
        Assert.assertEquals(fieldMetadata.getTabName(), tabName);
        Assert.assertEquals(fieldMetadata.getGroupName(), groupName);
        callCounter++;
        return callCounter;
    }

}
