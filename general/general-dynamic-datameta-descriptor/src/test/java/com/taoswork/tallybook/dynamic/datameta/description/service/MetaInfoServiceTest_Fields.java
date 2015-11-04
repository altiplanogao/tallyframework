package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed.*;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection.CollectionFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection.MapFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.IGroupInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.handy.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.MapFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeNameX;
import com.taoswork.tallybook.testframework.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.ParkingSpaceImpl;
import com.taoswork.tallybook.testframework.domain.common.PhoneType;
import com.taoswork.tallybook.testframework.domain.nature.impl.CitizenImpl;
import com.taoswork.tallybook.testframework.general.CollectionAssert;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetaInfoServiceTest_Fields {
    private final MetadataService metadataService;
    private final MetaInfoService metaInfoService;

    private final ClassMetadata companyMetadata;
    private final ClassMetadata departmentMetadata;
    private final ClassMetadata employeeMetadata;
    private final ClassMetadata parkSpaceMetadata;
    private final ClassMetadata[] metadatas;

    private final EntityInfo companyInfo;
    private final EntityInfo departmentInfo;
    private final EntityInfo employeeInfo;
    private final EntityInfo parkSpaceInfo;
    private final EntityInfo[] metainfos;

    public MetaInfoServiceTest_Fields(){
        metadataService = new MetadataServiceImpl();
        metaInfoService = new MetaInfoServiceImpl();

        companyMetadata = metadataService.generateMetadata(CompanyImpl.class);
        departmentMetadata = metadataService.generateMetadata(DepartmentImpl.class);
        employeeMetadata = metadataService.generateMetadata(EmployeeImpl.class);
        parkSpaceMetadata = metadataService.generateMetadata(ParkingSpaceImpl.class);
        metadatas = new ClassMetadata[]{
            companyMetadata,
            departmentMetadata,
            employeeMetadata,
            parkSpaceMetadata};

        companyInfo = metaInfoService.generateEntityMainInfo(companyMetadata);
        departmentInfo = metaInfoService.generateEntityMainInfo(departmentMetadata);
        employeeInfo = metaInfoService.generateEntityMainInfo(employeeMetadata);
        parkSpaceInfo = metaInfoService.generateEntityMainInfo(parkSpaceMetadata);
        metainfos = new EntityInfo[]{
            companyInfo,
            departmentInfo,
            employeeInfo,
            parkSpaceInfo
        };
    }

    boolean skipCollection(){
        return true;
    }

    boolean skipMap(){
        return true;
    }

    @Test
    public void testEntityInfoTabs() {
        ClassMetadata classMetadata = metadataService.generateMetadata(CompanyImpl.class);
        EntityInfo entityInfo = metaInfoService.generateEntityMainInfo(classMetadata);
        Assert.assertNotNull(entityInfo);

        Assert.assertEquals(classMetadata.getReadonlyFieldMetadataMap().size(), 16);
        Assert.assertEquals(entityInfo.getFields().size(), 19); //Address expanded

        if (entityInfo != null) {
            Assert.assertEquals(entityInfo.getEntityType(), CompanyImpl.class.getName());

            Assert.assertNotNull(entityInfo);
            ITabInfo[] tabInsights = entityInfo.getTabs().toArray(new ITabInfo[]{});
            Assert.assertEquals(tabInsights.length, 3);

            ITabInfo generalTab = tabInsights[0];
            Assert.assertEquals(generalTab.getName(), "General");
            Assert.assertEquals(generalTab.getGroups().size(), 3);

            ITabInfo marketingTab = tabInsights[1];
            Assert.assertEquals(marketingTab.getName(), "Marketing");
            Assert.assertEquals(marketingTab.getGroups().size(), 2);

            ITabInfo contactTab = tabInsights[2];
            Assert.assertEquals(contactTab.getName(), "Contact");
            Assert.assertEquals(contactTab.getGroups().size(), 1);
            IGroupInfo generalGp = contactTab.getGroups().get(0);
            Assert.assertNotNull(generalGp);
            Collection<String> contactGeneralFields = generalGp.getFields();
            Assert.assertEquals(contactGeneralFields.size(), 6);
            CollectionAssert.ensureFullyCover(contactGeneralFields,
                "email", "phone",
                "address.street", "address.city", "address.state", "address.zip");

            Assert.assertEquals(entityInfo.getGridFields().size(), 14);
        }
    }

    @Test
    public void testAsGrid(){
        IEntityInfo entityGridInfo = metaInfoService.generateEntityInfo(companyMetadata, EntityInfoType.Grid);
        Assert.assertNotNull(entityGridInfo);
        if (entityGridInfo != null) {
            Assert.assertEquals(((EntityGridInfo)entityGridInfo).fields.size(), 14);
        }
    }

    @Test
    public void testIdField() {
        for (EntityInfo entityInfo : metainfos) {
            String idFieldName = entityInfo.getIdField();
            Assert.assertTrue(StringUtils.isNotEmpty(idFieldName));

            IFieldInfo fieldInfo = entityInfo.getField(idFieldName);
            Assert.assertNotNull(fieldInfo);
        }
    }

    @Test
    public void testNameField() {
        for (EntityInfo entityInfo : metainfos) {
            String nameFieldName = entityInfo.getNameField();
            IFieldInfo nameFieldInfo = entityInfo.getField(nameFieldName);

            if(parkSpaceInfo == entityInfo){
                Assert.assertNull(nameFieldInfo);
                Assert.assertTrue(StringUtils.isEmpty(nameFieldName));
                continue;
            }
            Assert.assertNotNull(nameFieldInfo);
            Assert.assertTrue(nameFieldInfo instanceof StringFieldInfo);
        }
    }

    @Test
    public void testStringField() {
        IFieldInfo companyDesFieldInfo = companyInfo.getField("description");
        Assert.assertTrue(companyDesFieldInfo instanceof StringFieldInfo);
        if (companyDesFieldInfo instanceof StringFieldInfo) {
            int length = ((StringFieldInfo) companyDesFieldInfo).getLength();
            Assert.assertEquals(length, 2000);
        }
    }

    @Test
    public void testBooleanField() {
        IFieldInfo lockedFieldInfo = companyInfo.getField("locked");
        IFieldInfo activeFieldInfo = companyInfo.getField("active");
        Assert.assertTrue(lockedFieldInfo instanceof BooleanFieldInfo);
        Assert.assertTrue(activeFieldInfo instanceof BooleanFieldInfo);
        if (lockedFieldInfo instanceof BooleanFieldInfo) {
            Map<String, String> options = ((BooleanFieldInfo) lockedFieldInfo).getOptions();
            Assert.assertEquals("general.boolean.yes", options.get(BooleanFieldInfo.TRUE));
            Assert.assertEquals("general.boolean.no", options.get(BooleanFieldInfo.FALSE));
        }
        if (activeFieldInfo instanceof BooleanFieldInfo) {
            Map<String, String> options = ((BooleanFieldInfo) activeFieldInfo).getOptions();
            Assert.assertEquals("general.boolean.true", options.get(BooleanFieldInfo.TRUE));
            Assert.assertEquals("general.boolean.false", options.get(BooleanFieldInfo.FALSE));
        }
    }

    @Test
    public void testEnumField() {
        IFieldInfo fieldInfo = companyInfo.getField("companyType");
        EnumFieldInfo companyTypeField = (EnumFieldInfo)fieldInfo;
        Assert.assertNotNull(companyTypeField);

        Collection<String> options = companyTypeField.getOptions();
        Map<String, String> optionsFriendly = companyTypeField.getOptionsFriendly();

        Assert.assertArrayEquals(new Object[]{"National", "Multinationals", "Private", "Unknown"}, options.toArray());
        Assert.assertEquals(options.size(), optionsFriendly.size());
        for(String op : options){
            Assert.assertTrue(optionsFriendly.containsKey(op));
        }
    }

    @Test
    public void testEmbeddedField() {
        {
            IFieldInfo addressStreetFieldInfo = companyInfo.getField("address.street");
            IFieldInfo addressCityFieldInfo = companyInfo.getField("address.city");
            IFieldInfo addressStateFieldInfo = companyInfo.getField("address.state");
            IFieldInfo addressZipFieldInfo = companyInfo.getField("address.zip");

            Assert.assertNotNull(addressStreetFieldInfo);
            Assert.assertNotNull(addressCityFieldInfo);
            Assert.assertNotNull(addressStateFieldInfo);
            Assert.assertNotNull(addressZipFieldInfo);

            Assert.assertTrue(addressStreetFieldInfo instanceof StringFieldInfo);
            Assert.assertTrue(addressCityFieldInfo instanceof StringFieldInfo);
            Assert.assertTrue(addressStateFieldInfo instanceof StringFieldInfo);
            Assert.assertTrue(addressZipFieldInfo instanceof StringFieldInfo);
        }

        {
            IFieldInfo lastNameFieldInfo = employeeInfo.getField("nameX.last_Name");
            IFieldInfo firstNameFieldInfo = employeeInfo.getField("nameX.first_Name");

            Assert.assertNotNull(lastNameFieldInfo);
            Assert.assertNotNull(firstNameFieldInfo);

            Assert.assertTrue(lastNameFieldInfo instanceof StringFieldInfo);
            Assert.assertTrue(firstNameFieldInfo instanceof StringFieldInfo);

            ClassMetadata nameXCm = metadataService.generateMetadata(EmployeeNameX.class);
            Assert.assertNotNull(nameXCm);
            {
                EntityInfo ei = metaInfoService.generateEntityMainInfo(nameXCm);
                Assert.assertNotNull(ei);
                IFieldInfo lastNameFieldInfoRef = ei.getField("last_Name");
                IFieldInfo firstNameFieldInfoRef = ei.getField("first_Name");
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String lnFiJR = objectMapper.writeValueAsString(lastNameFieldInfoRef);
                    String lnFiJ = objectMapper.writeValueAsString(lastNameFieldInfo);
                    lnFiJ = lnFiJ.replace("nameX.", "").replace("" + lastNameFieldInfo.getOrder(), "" + lastNameFieldInfoRef.getOrder());

                    String fnFiJR = objectMapper.writeValueAsString(firstNameFieldInfoRef);
                    String fnFiJ = objectMapper.writeValueAsString(firstNameFieldInfo);
                    fnFiJ = fnFiJ.replace("nameX.", "").replace(""+firstNameFieldInfo.getOrder(),""+firstNameFieldInfoRef.getOrder());

                    Assert.assertEquals(lnFiJ, lnFiJR);
                    Assert.assertEquals(fnFiJ, fnFiJR);
                } catch (JsonProcessingException e) {
                    Assert.fail();
                }
            }
        }
    }

    @Test
    public void testForeignEntityField() {
        ForeignKeyFieldInfo parkingSpaceFieldMeta = (ForeignKeyFieldInfo) employeeInfo.getField("parkingSpace");
        ForeignKeyFieldInfo departmentFieldMeta = (ForeignKeyFieldInfo) employeeInfo.getField("department");
        Assert.assertEquals(ParkingSpaceImpl.class.getName(), parkingSpaceFieldMeta.entityType);
        Assert.assertEquals(DepartmentImpl.class.getName(), departmentFieldMeta.entityType);

        ForeignKeyFieldInfo psEmpFieldMeta = (ForeignKeyFieldInfo) parkSpaceInfo.getField("employee");
        ForeignKeyFieldInfo psEmpObjFieldMeta = (ForeignKeyFieldInfo) parkSpaceInfo.getField("employeeObj");
        ForeignKeyFieldInfo psEmpImplFieldMeta = (ForeignKeyFieldInfo) parkSpaceInfo.getField("employeeImpl");
        Assert.assertEquals(EmployeeImpl.class.getName(), psEmpFieldMeta.entityType);
        Assert.assertEquals(EmployeeImpl.class.getName(), psEmpObjFieldMeta.entityType);
        Assert.assertEquals(EmployeeImpl.class.getName(), psEmpImplFieldMeta.entityType);
    }

    @Test
    public void testExternalForeignEntityField() {
        IFieldInfo citizenIdFieldInfo = employeeInfo.getField("citizenId");
        Assert.assertNotNull(citizenIdFieldInfo);
        ExternalForeignKeyFieldInfo citizenIdExternalForeignKeyFieldInfo = (ExternalForeignKeyFieldInfo) citizenIdFieldInfo;
        Assert.assertNotNull(citizenIdExternalForeignKeyFieldInfo);
        Assert.assertEquals(CitizenImpl.class.getName(), citizenIdExternalForeignKeyFieldInfo.entityType);
        Assert.assertEquals("citizen", citizenIdExternalForeignKeyFieldInfo.entityFieldName);
    }

    @Test
    public void testPrimitiveCollection() {
        if(skipCollection()){
            return;
        }
        CollectionFieldInfo nickFmInTypedSet = (CollectionFieldInfo) employeeInfo.getField("nickNameSet");
        CollectionFieldInfo nickFmInSet = (CollectionFieldInfo) employeeInfo.getField("nickNameSetNonType");
        CollectionFieldInfo nickFmInList = (CollectionFieldInfo) employeeInfo.getField("nickNameList");
        CollectionFieldInfo nickFmInArray = (CollectionFieldInfo) employeeInfo.getField("nickNameArray");

        Assert.assertNotNull(nickFmInTypedSet);
        Assert.assertNotNull(nickFmInSet);
        Assert.assertNotNull(nickFmInList);
        Assert.assertNotNull(nickFmInArray);

        try {
            ObjectMapper om = new ObjectMapper();
            String st1 = om.writeValueAsString(nickFmInTypedSet).replaceAll("nickNameSet", "--");
            String st2 = om.writeValueAsString(nickFmInSet).replaceAll("nickNameSetNonType", "--");
            String st3 = om.writeValueAsString( nickFmInList).replaceAll("nickNameList", "--");
            String st4 = om.writeValueAsString( nickFmInArray).replaceAll("nickNameArray", "--");
            Assert.assertEquals(st1,st2);
            Assert.assertEquals(st1,st3);
            Assert.assertEquals(st1,st4);
        }catch (JsonProcessingException e) {
            Assert.fail();
        }

//
//        Assert.assertTrue(nickFmInTypedSet.isCollectionField());
//        Assert.assertTrue(nickFmInSet.isCollectionField());
//        Assert.assertTrue(nickFmInList.isCollectionField());
//        Assert.assertTrue(nickFmInArray.isCollectionField());
//
//        Assert.assertEquals(String.class, nickFmInTypedSet.getEntryType().getSimpleType());
//        Assert.assertEquals(String.class, nickFmInSet.getEntryType().getSimpleType());
//        Assert.assertEquals(String.class, nickFmInList.getEntryType().getSimpleType());
    }

    @Test
    public void testPrimitiveMap() {
        if(skipMap()){
            return;
        }
        IFieldInfo fieldInfo = employeeInfo.getField("phoneNumbers");
        Assert.assertNotNull(fieldInfo);
        MapFieldInfo mapFieldInfo = (MapFieldInfo)fieldInfo;

        MapFieldMetadata phoneNumbersFm = (MapFieldMetadata) employeeInfo.getField("phoneNumbers");

        Assert.assertNotNull(phoneNumbersFm);
        Assert.assertTrue(phoneNumbersFm.isCollectionField());

        Assert.assertEquals(phoneNumbersFm.getKeyType().getSimpleType(), PhoneType.class);
        Assert.assertEquals(phoneNumbersFm.getValueType().getSimpleType(), String.class);
    }

    @Test
    public void testEmbeddedCollection() {
        if(skipCollection()){
            return;
        }
        CollectionFieldMetadata vacationBookingsFm = (CollectionFieldMetadata) employeeInfo.getField("vacationBookings");
        Assert.assertNotNull(vacationBookingsFm);
        Assert.assertTrue(vacationBookingsFm.isCollectionField());
        Assert.assertNull(vacationBookingsFm.getEntryType().getSimpleType());
        Assert.assertNotNull(vacationBookingsFm.getEntryType().getAsEmbeddedClassMetadata());
        Assert.assertNull(vacationBookingsFm.getEntryType().getEntityType());
    }

    @Test
    public void testEmbeddedMap() {
        if(skipMap()){
            return;
        }
        MapFieldMetadata employeesByNameXFm = (MapFieldMetadata) departmentInfo.getField("employeesByNameX");
        Assert.assertNotNull(employeesByNameXFm);
        Assert.assertTrue(employeesByNameXFm.isCollectionField());
        Assert.assertNull(employeesByNameXFm.getKeyType().getSimpleType());
        Assert.assertNotNull(employeesByNameXFm.getKeyType().getAsEmbeddedClassMetadata());
        Assert.assertNull(employeesByNameXFm.getKeyType().getEntityType());
    }

    @Test
    public void testEntityCollection() {
        if(skipCollection()){
            return;
        }
        CollectionFieldMetadata departmentEmployeeFm = (CollectionFieldMetadata) departmentInfo.getField("employees");
        CollectionFieldMetadata departmentEmployeeListFm = (CollectionFieldMetadata) departmentInfo.getField("employeesList");
        Assert.assertNotNull(departmentEmployeeFm);
        Assert.assertNotNull(departmentEmployeeListFm);
        Assert.assertTrue(departmentEmployeeFm.isCollectionField());
        Assert.assertTrue(departmentEmployeeListFm.isCollectionField());

        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeFm.getEntryType().getEntityType());
        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeListFm.getEntryType().getEntityType());
    }

    @Test
    public void testEntityMap() {
        if(skipMap()){
            return;
        }
        {
            MapFieldMetadata employeesByNameFm = (MapFieldMetadata) departmentInfo.getField("employeesByName");
            Assert.assertNotNull(employeesByNameFm);
            Assert.assertTrue(employeesByNameFm.isCollectionField());
            Assert.assertNull(employeesByNameFm.getValueType().getSimpleType());
            Assert.assertNull(employeesByNameFm.getValueType().getAsEmbeddedClassMetadata());
            Assert.assertEquals(EmployeeImpl.class, employeesByNameFm.getValueType().getEntityType());
        }
        {
            MapFieldMetadata employeesFm = (MapFieldMetadata) departmentInfo.getField("employeesByUnTypedId");
            Assert.assertNotNull(employeesFm);
            Assert.assertTrue(employeesFm.isCollectionField());
            Assert.assertNull(employeesFm.getValueType().getSimpleType());
            Assert.assertNull(employeesFm.getValueType().getAsEmbeddedClassMetadata());
            Assert.assertEquals(EmployeeImpl.class, employeesFm.getValueType().getEntityType());
        }
        {
            MapFieldMetadata employeesFm = (MapFieldMetadata) departmentInfo.getField("employeesByUnTypedName");
            Assert.assertNotNull(employeesFm);
            Assert.assertTrue(employeesFm.isCollectionField());
            Assert.assertNull(employeesFm.getValueType().getSimpleType());
            Assert.assertNull(employeesFm.getValueType().getAsEmbeddedClassMetadata());
            Assert.assertEquals(EmployeeImpl.class, employeesFm.getValueType().getEntityType());
        }
//        CollectionFieldMetadata departmentEmployeeFm = (CollectionFieldMetadata) departmentInfo.getField("employees");
//        CollectionFieldMetadata departmentEmployeeListFm = (CollectionFieldMetadata) departmentInfo.getField("employeesList");
//        Assert.assertNotNull(departmentEmployeeFm);
//        Assert.assertNotNull(departmentEmployeeListFm);
//        Assert.assertTrue(departmentEmployeeFm.isCollectionField());
//        Assert.assertTrue(departmentEmployeeListFm.isCollectionField());
//
//        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeFm.getEntityType());
//        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeListFm.getEntityType());
    }
}