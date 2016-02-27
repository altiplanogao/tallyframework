package com.taoswork.tallybook.descriptor.metadata.service;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.service.MetaService;
import com.taoswork.tallybook.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallybook.descriptor.metadata.utils.GeneralFieldScanMethod;
import com.taoswork.tallybook.general.solution.reflect.FieldScanner;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.EmployeeImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetaServiceTest_NoMissing {
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
    public void testClassMetadata_CompanyImpl() {
        IClassMeta classMeta = metaService.generateMeta(CompanyImpl.class, null);

        Map<String, IFieldMeta> fieldMetaMap = classMeta.getReadonlyFieldMetaMap();
        Assert.assertTrue(fieldMetaMap.size() == FieldScanner.getFields(CompanyImpl.class,
                GeneralFieldScanMethod.scanAllPersistentNoSuper).size());
        int callCounter = 0;

        callCounter = assertFieldTabGroup(fieldMetaMap, "id", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "asset", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "name", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "description", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "description2", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        assertFieldNotExist(fieldMetaMap, "handyMemo1");
        assertFieldNotExist(fieldMetaMap, "handyMemo2");
        callCounter = assertFieldTabGroup(fieldMetaMap, "creationDate", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.Advanced, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "companyType", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "locked", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "active", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "taxCode", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.Private, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "adminId", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.Private, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "publicProducts", CompanyImpl.Presentation.Tab.Marketing, CompanyImpl.Presentation.Group.Public, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "privateProducts", CompanyImpl.Presentation.Tab.Marketing, CompanyImpl.Presentation.Group.Private, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "email", CompanyImpl.Presentation.Tab.Contact, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "phone", CompanyImpl.Presentation.Tab.Contact, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "address", CompanyImpl.Presentation.Tab.Contact, CompanyImpl.Presentation.Group.General, callCounter);
        Assert.assertEquals(fieldMetaMap.size(), callCounter);

        Assert.assertNotNull(classMeta);
    }

    @Test
    public void testClassMetadata_DepartmentImpl() {
        IClassMeta classMeta = metaService.generateMeta(DepartmentImpl.class, null);

        Map<String, IFieldMeta> fieldMetaMap = classMeta.getReadonlyFieldMetaMap();
        Assert.assertTrue(fieldMetaMap.size() == FieldScanner.getFields(DepartmentImpl.class,
                GeneralFieldScanMethod.scanAllPersistentNoSuper).size());
        int callCounter = 0;

        callCounter = assertFieldTabGroup(fieldMetaMap, "id", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "name", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "employees", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "employeesList", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "employeesByCubicle", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "employeesMap", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "employeesByName", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "employeesByNameX", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "employeesByUnTypedId", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "employeesByUnTypedName", null, null, callCounter);
        Assert.assertEquals(fieldMetaMap.size(), callCounter);

        Assert.assertNotNull(classMeta);
    }

    @Test
    public void testClassMetadata_EmployeeImpl() {
        IClassMeta classMeta = metaService.generateMeta(EmployeeImpl.class, null);

        Map<String, IFieldMeta> fieldMetaMap = classMeta.getReadonlyFieldMetaMap();
        Assert.assertTrue(fieldMetaMap.size() == FieldScanner.getFields(EmployeeImpl.class,
                GeneralFieldScanMethod.scanAllPersistentNoSuper).size());
        int callCounter = 0;

        callCounter = assertFieldTabGroup(fieldMetaMap, "id", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "name", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "firstName", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "lastName", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "nameX", null, null, callCounter);
        assertFieldNotExist(fieldMetaMap, "translatedName");
        callCounter = assertFieldTabGroup(fieldMetaMap, "nickNameSet", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "nickNameSetNonType", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "nickNameList", null, null, callCounter);
//        callCounter = assertFieldTabGroup(fieldMetaMap, "nickNameArray",  null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "salary", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "type", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "citizenId", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "dob", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "startDate", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "comments", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "picture", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "parkingSpace", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "department", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "cube", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "projects", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "vacationBookings", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "nickNames", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "address", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "addresses", null, null, callCounter);
        callCounter = assertFieldTabGroup(fieldMetaMap, "phoneNumbers", null, null, callCounter);
        Assert.assertEquals(fieldMetaMap.size(), callCounter);

        Assert.assertNotNull(classMeta);
    }

    private void assertFieldNotExist(Map<String, IFieldMeta> fieldMetaMap,
                                     String fieldName) {
        IFieldMeta fieldMeta = fieldMetaMap.get(fieldName);
        Assert.assertNull(fieldMeta);
    }

    private int assertFieldTabGroup(Map<String, IFieldMeta> fieldMetaMap,
                                    String fieldName, String tabName, String groupName, int callCounter) {
        if (StringUtils.isEmpty(tabName)) {
            tabName = PresentationClass.Tab.DEFAULT_NAME;
        }
        if (StringUtils.isEmpty(groupName)) {
            groupName = PresentationClass.Group.DEFAULT_NAME;
        }
        IFieldMeta fieldMeta = fieldMetaMap.get(fieldName);
        Assert.assertNotNull(fieldMeta);
        Assert.assertEquals(fieldMeta.getTabName(), tabName);
        Assert.assertEquals(fieldMeta.getGroupName(), groupName);
        callCounter++;
        return callCounter;
    }

}
