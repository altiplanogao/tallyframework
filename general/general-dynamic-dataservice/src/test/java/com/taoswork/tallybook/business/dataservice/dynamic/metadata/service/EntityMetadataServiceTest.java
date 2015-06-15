package com.taoswork.tallybook.business.dataservice.dynamic.metadata.service;

import com.taoswork.tallybook.business.dataservice.dynamic.data4test.domain.CompanyImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.impl.EntityMetadataServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.utils.NativeClassHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class EntityMetadataServiceTest {
    private EntityMetadataService entityMetadataService;
    @Before
    public void setup(){
        entityMetadataService = new EntityMetadataServiceImpl();
    }

    @After
    public void teardown(){
        entityMetadataService = null;
    }

    @Test
    public void testClassMetadata(){
        ClassMetadata classMetadata = entityMetadataService.getClassMetadata(CompanyImpl.class);

        Map<String, FieldMetadata> fieldMetadataMap = classMetadata.getFieldMetadataMap();
        Assert.assertTrue(fieldMetadataMap.size() == NativeClassHelper.getFields(CompanyImpl.class,
                NativeClassHelper.scanAllPersistentNoSuper).size());
        int callCounter = 0;

        callCounter = assertFieldTabGroup(fieldMetadataMap, "id", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "name", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "description", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "description2", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "creationDate", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.Advanced, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "taxCode", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.Private, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "adminId", CompanyImpl.Presentation.Tab.General, CompanyImpl.Presentation.Group.Private, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "publicProducts", CompanyImpl.Presentation.Tab.Marketing, CompanyImpl.Presentation.Group.Public, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "privateProducts", CompanyImpl.Presentation.Tab.Marketing, CompanyImpl.Presentation.Group.Private, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "businessPartners", CompanyImpl.Presentation.Tab.Marketing, CompanyImpl.Presentation.Group.Private, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "email", CompanyImpl.Presentation.Tab.Contact, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "phone", CompanyImpl.Presentation.Tab.Contact, CompanyImpl.Presentation.Group.General, callCounter);
        callCounter = assertFieldTabGroup(fieldMetadataMap, "address", CompanyImpl.Presentation.Tab.Contact, CompanyImpl.Presentation.Group.General, callCounter);
        Assert.assertEquals(fieldMetadataMap.size(), callCounter);

        Assert.assertNotNull(classMetadata);
    }

    private int assertFieldTabGroup(Map<String, FieldMetadata> fieldMetadataMap,
                                    String fieldName, String tabName, String groupName, int callCounter){
        FieldMetadata fieldMetadata = fieldMetadataMap.getOrDefault(fieldName, null);
        Assert.assertNotNull(fieldMetadata);
        Assert.assertEquals(fieldMetadata.tabName, tabName);
        Assert.assertEquals(fieldMetadata.groupName, groupName);
        callCounter++;
        return callCounter;
    }

}
