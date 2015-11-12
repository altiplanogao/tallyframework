package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed.*;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection.CollectionFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection.MapFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.group.IGroupInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.handy.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.StringEntryDelegate;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeName;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeNameX;
import com.taoswork.tallybook.testframework.domain.business.embed.VacationEntry;
import com.taoswork.tallybook.testframework.domain.business.impl.*;
import com.taoswork.tallybook.testframework.domain.common.PhoneTypeEntryDelegate;
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

    private final IClassMetadata companyMetadata;
    private final IClassMetadata departmentMetadata;
    private final IClassMetadata employeeMetadata;
    private final IClassMetadata parkSpaceMetadata;

    private final EntityInfo companyInfo;
    private final EntityInfo departmentInfo;
    private final EntityInfo employeeInfo;
    private final EntityInfo parkSpaceInfo;
    private final EntityInfo[] metainfos;

    public MetaInfoServiceTest_Fields(){
        metadataService = new MetadataServiceImpl();
        metaInfoService = new MetaInfoServiceImpl();

        companyMetadata = metadataService.generateMetadata(CompanyImpl.class, null);
        departmentMetadata = metadataService.generateMetadata(DepartmentImpl.class, null);
        employeeMetadata = metadataService.generateMetadata(EmployeeImpl.class, null);
        parkSpaceMetadata = metadataService.generateMetadata(ParkingSpaceImpl.class, null);

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

    @Test
    public void testEntityInfoTabs() {
        IClassMetadata classMetadata = metadataService.generateMetadata(CompanyImpl.class, null);
        EntityInfo entityInfo = metaInfoService.generateEntityMainInfo(classMetadata);
        Assert.assertNotNull(entityInfo);

        Assert.assertEquals(classMetadata.getReadonlyFieldMetadataMap().size(), 16);
        Assert.assertEquals(entityInfo.getFields().size(), 19); //Address expanded

        if (entityInfo != null) {
            Assert.assertEquals(entityInfo.getEntityType(), CompanyImpl.class.getName());

            Assert.assertNotNull(entityInfo);
            ITabInfo[] tabInfos = entityInfo.getTabs().toArray(new ITabInfo[]{});
            Assert.assertEquals(tabInfos.length, 3);

            ITabInfo generalTab = tabInfos[0];
            Assert.assertEquals(generalTab.getName(), "General");
            Assert.assertEquals(generalTab.getGroups().size(), 3);

            ITabInfo marketingTab = tabInfos[1];
            Assert.assertEquals(marketingTab.getName(), "Marketing");
            Assert.assertEquals(marketingTab.getGroups().size(), 2);

            ITabInfo contactTab = tabInfos[2];
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

            IClassMetadata nameXCm = metadataService.generateMetadata(EmployeeNameX.class, null);
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
        CollectionFieldInfo nickFmInTypedSet = (CollectionFieldInfo) employeeInfo.getField("nickNameSet");
        CollectionFieldInfo nickFmInSet = (CollectionFieldInfo) employeeInfo.getField("nickNameSetNonType");
        CollectionFieldInfo nickFmInList = (CollectionFieldInfo) employeeInfo.getField("nickNameList");
//        CollectionFieldInfo nickFmInArray = (CollectionFieldInfo) employeeInfo.getField("nickNameArray");

        assertValidCollectionFieldInfo(nickFmInTypedSet, employeeInfo, StringEntryDelegate.class);
        assertValidCollectionFieldInfo(nickFmInSet, employeeInfo, StringEntryDelegate.class);
        assertValidCollectionFieldInfo(nickFmInList, employeeInfo, StringEntryDelegate.class);
//        assertValidCollectionFieldInfo(nickFmInArray, employeeInfo, NicknameEntryDelegate.class);

        try {
            ObjectMapper om = new ObjectMapper();
            String st1 = om.writeValueAsString(nickFmInTypedSet).replaceAll("nickNameSet", "--").replaceAll(""+nickFmInTypedSet.order, "");
            String st2 = om.writeValueAsString(nickFmInSet).replaceAll("nickNameSetNonType", "--").replaceAll(""+nickFmInSet.order, "");
            String st3 = om.writeValueAsString(nickFmInList).replaceAll("nickNameList", "--").replaceAll(""+nickFmInList.order, "");
//            String st4 = om.writeValueAsString(nickFmInArray).replaceAll("nickNameArray", "--").replaceAll(""+nickFmInArray.order, "");
//            String st4s = st4.replace(NicknameEntryDelegate.class.getName(), StringEntryDelegate.class.getName());
            Assert.assertEquals(st1, st2);
            Assert.assertEquals(st1, st3);
//            Assert.assertNotEquals(st1, st4);
//            Assert.assertEquals(st1, st4s);
        } catch (JsonProcessingException e) {
            Assert.fail();
        }
    }

    @Test
    public void testPrimitiveMap() {
        IFieldInfo fieldInfo = employeeInfo.getField("phoneNumbers");
        Assert.assertNotNull(fieldInfo);
        MapFieldInfo mapFieldInfo = (MapFieldInfo)fieldInfo;
        assertValidMapFieldInfo(mapFieldInfo, employeeInfo, PhoneTypeEntryDelegate.class, StringEntryDelegate.class);
    }

    @Test
    public void testEmbeddedCollection() {
        CollectionFieldInfo vacationBookingsInfo = (CollectionFieldInfo) employeeInfo.getField("vacationBookings");
        assertValidCollectionFieldInfo(vacationBookingsInfo, employeeInfo, VacationEntry.class);
    }

    @Test
    public void testEmbeddedMap() {
        MapFieldInfo employeesByNameXInfo = (MapFieldInfo) departmentInfo.getField("employeesByNameX");
        assertValidMapFieldInfo(employeesByNameXInfo, departmentInfo, EmployeeNameX.class, EmployeeImpl.class);
    }

    @Test
    public void testEntityCollection() {
        CollectionFieldInfo departmentEmployeeFm = (CollectionFieldInfo) departmentInfo.getField("employees");
        assertValidCollectionFieldInfo(departmentEmployeeFm,departmentInfo,EmployeeImpl.class );
        CollectionFieldInfo departmentEmployeeListFm = (CollectionFieldInfo) departmentInfo.getField("employeesList");
        assertValidCollectionFieldInfo(departmentEmployeeListFm,departmentInfo,EmployeeImpl.class );
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

    private static void assertValidCollectionFieldInfo(CollectionFieldInfo collectionFieldInfo, EntityInfo holder, Class entryClass){
        Assert.assertNotNull(collectionFieldInfo);
        String entryType = collectionFieldInfo.getEntryType();
        Assert.assertEquals(entryClass.getName(), entryType);
        Map<String, IEntityInfo> referencingEntryInfos = holder.getEntryInfos();
        IEntityInfo entryInfo = referencingEntryInfos.get(collectionFieldInfo.getEntryType());
        Assert.assertNotNull(entryInfo);
    }

    private static void assertValidMapFieldInfo(MapFieldInfo mapFieldInfo, EntityInfo holder, Class keyClass, Class valueClass){
        Map<String, IEntityInfo> referencingEntryInfos = holder.getEntryInfos();
        Assert.assertNotNull(mapFieldInfo);

        if(keyClass != null) {
            String keyEntryType = mapFieldInfo.getKeyEntryType();
            Assert.assertEquals(keyClass.getName(), keyEntryType);
            IEntityInfo keyEntityInfo = referencingEntryInfos.get(keyEntryType);
            Assert.assertNotNull(keyEntityInfo);
        }
        if (valueClass != null) {
            String valueEntryType = mapFieldInfo.getValueEntryType();
            Assert.assertEquals(valueClass.getName(), valueEntryType);
            IEntityInfo valueEntityInfo = referencingEntryInfos.get(valueEntryType);
            Assert.assertNotNull(valueEntityInfo);
        }
    }
}