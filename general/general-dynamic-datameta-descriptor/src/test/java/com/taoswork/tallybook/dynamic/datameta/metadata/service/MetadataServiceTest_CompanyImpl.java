package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.CompanyImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.NativeClassHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetadataServiceTest_CompanyImpl {
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
    public void testClassMetadata(){
        ClassMetadata classMetadata = metadataService.getClassMetadata(CompanyImpl.class);

        Map<String, FieldMetadata> fieldMetadataMap = classMetadata.getReadonlyFieldMetadataMap();
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
        Assert.assertEquals(fieldMetadata.getTabName(), tabName);
        Assert.assertEquals(fieldMetadata.getGroupName(), groupName);
        callCounter++;
        return callCounter;
    }

}
