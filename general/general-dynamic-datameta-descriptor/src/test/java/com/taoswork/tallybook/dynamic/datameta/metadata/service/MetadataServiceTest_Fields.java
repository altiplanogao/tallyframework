package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.*;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.ArrayFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.MapFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanModel;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeNameX;
import com.taoswork.tallybook.testframework.domain.business.enumtype.CompanyType;
import com.taoswork.tallybook.testframework.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.ParkingSpaceImpl;
import com.taoswork.tallybook.testframework.domain.common.Address;
import com.taoswork.tallybook.testframework.domain.common.PhoneType;
import com.taoswork.tallybook.testframework.domain.nature.impl.CitizenImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetadataServiceTest_Fields {
    private MetadataService metadataService;
    private ClassMetadata companyMetadata;
    private ClassMetadata departmentMetadata;
    private ClassMetadata employeeMetadata;
    private ClassMetadata parkSpaceMetadata;
    private ClassMetadata[] metadatas;

    @Before
    public void setup() {
        metadataService = new MetadataServiceImpl();
        companyMetadata = metadataService.generateMetadata(CompanyImpl.class);
        departmentMetadata = metadataService.generateMetadata(DepartmentImpl.class);
        employeeMetadata = metadataService.generateMetadata(EmployeeImpl.class);
        parkSpaceMetadata = metadataService.generateMetadata(ParkingSpaceImpl.class);
        metadatas = new ClassMetadata[]{
            companyMetadata,
            departmentMetadata,
            employeeMetadata};
    }

    @After
    public void teardown() {
        metadatas = null;
        companyMetadata = null;
        departmentMetadata = null;
        employeeMetadata = null;
        parkSpaceMetadata = null;
        metadataService = null;
    }

    @Test
    public void testIdField() {
        for (ClassMetadata classMetadata : metadatas) {
            Field idField = classMetadata.getIdField();
            Assert.assertNotNull(idField);

            IFieldMetadata idFieldMetadata = classMetadata.getFieldMetadata(idField.getName());
            Assert.assertNotNull(idFieldMetadata);
        }
    }

    @Test
    public void testNameField() {
        for (ClassMetadata classMetadata : metadatas) {
            Field nameField = classMetadata.getNameField();
            Assert.assertNotNull(nameField);

            IFieldMetadata nameFieldMetadata = classMetadata.getFieldMetadata(nameField.getName());
            Assert.assertNotNull(nameFieldMetadata);

            Assert.assertTrue(nameFieldMetadata instanceof StringFieldMetadata);
        }
    }

    @Test
    public void testStringField() {
        IFieldMetadata companyDesFieldMeta = companyMetadata.getFieldMetadata("description");
        Assert.assertTrue(companyDesFieldMeta instanceof StringFieldMetadata);
        if (companyDesFieldMeta instanceof StringFieldMetadata) {
            int length = ((StringFieldMetadata) companyDesFieldMeta).getLength();
            Assert.assertEquals(length, 2000);
        }
    }

    @Test
    public void testBooleanField() {
        IFieldMetadata lockedFieldMeta = companyMetadata.getFieldMetadata("locked");
        IFieldMetadata activeFieldMeta = companyMetadata.getFieldMetadata("active");
        Assert.assertTrue(lockedFieldMeta instanceof BooleanFieldMetadata);
        Assert.assertTrue(activeFieldMeta instanceof BooleanFieldMetadata);
        if (lockedFieldMeta instanceof BooleanFieldMetadata) {
            BooleanModel model = ((BooleanFieldMetadata) lockedFieldMeta).getModel();
            Assert.assertEquals(BooleanModel.YesNo, model);
        }
        if (activeFieldMeta instanceof BooleanFieldMetadata) {
            BooleanModel model = ((BooleanFieldMetadata) activeFieldMeta).getModel();
            Assert.assertEquals(BooleanModel.TrueFalse, model);
        }
    }

    @Test
    public void testEnumField() {
        IFieldMetadata companyTypeFieldMeta = companyMetadata.getFieldMetadata("companyType");
        Assert.assertTrue(companyTypeFieldMeta instanceof EnumFieldMetadata);
        if (companyTypeFieldMeta instanceof EnumFieldMetadata) {
            Class enumType = ((EnumFieldMetadata) companyTypeFieldMeta).getEnumerationType();
            Assert.assertEquals(CompanyType.class, enumType);
        }
    }

    @Test
    public void testEmbeddedField() {
        {
            EmbeddedFieldMetadata addressFieldMeta = (EmbeddedFieldMetadata) companyMetadata.getFieldMetadata("address");
            Assert.assertNotNull(addressFieldMeta);
            ClassMetadata embeddedAddressClassMetadata = addressFieldMeta.getClassMetadata();
            Assert.assertNotNull(embeddedAddressClassMetadata);

            ClassMetadata addressCm = metadataService.generateMetadata(Address.class);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String ref = objectMapper.writeValueAsString(addressCm);
                String got = objectMapper.writeValueAsString(embeddedAddressClassMetadata);
                Assert.assertEquals(ref, got);
            } catch (Exception e) {
                Assert.fail();
            }
        }

        {
            EmbeddedFieldMetadata nameXFieldMeta = (EmbeddedFieldMetadata) employeeMetadata.getFieldMetadata("nameX");
            Assert.assertNotNull(nameXFieldMeta);
            ClassMetadata embeddedNameXClassMetadata = nameXFieldMeta.getClassMetadata();
            Assert.assertNotNull(embeddedNameXClassMetadata);

            ClassMetadata nameXCm = metadataService.generateMetadata(EmployeeNameX.class);
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String ref = objectMapper.writeValueAsString(nameXCm);
                String got = objectMapper.writeValueAsString(embeddedNameXClassMetadata);
                Assert.assertEquals(ref, got);
            } catch (Exception e) {
                Assert.fail();
            }
        }
    }

    @Test
    public void testForeignEntityField() {
        ForeignEntityFieldMetadata parkingSpaceFieldMeta = (ForeignEntityFieldMetadata) employeeMetadata.getFieldMetadata("parkingSpace");
        ForeignEntityFieldMetadata departmentFieldMeta = (ForeignEntityFieldMetadata) employeeMetadata.getFieldMetadata("department");
        Assert.assertEquals(parkingSpaceFieldMeta.getEntityType(), ParkingSpaceImpl.class);
        Assert.assertEquals(departmentFieldMeta.getEntityType(), DepartmentImpl.class);

        ForeignEntityFieldMetadata psEmpFieldMeta = (ForeignEntityFieldMetadata) parkSpaceMetadata.getFieldMetadata("employee");
        ForeignEntityFieldMetadata psEmpObjFieldMeta = (ForeignEntityFieldMetadata) parkSpaceMetadata.getFieldMetadata("employeeObj");
        ForeignEntityFieldMetadata psEmpImplFieldMeta = (ForeignEntityFieldMetadata) parkSpaceMetadata.getFieldMetadata("employeeImpl");
        Assert.assertEquals(psEmpFieldMeta.getEntityType(), EmployeeImpl.class);
        Assert.assertEquals(psEmpObjFieldMeta.getEntityType(), EmployeeImpl.class);
        Assert.assertEquals(psEmpImplFieldMeta.getEntityType(), EmployeeImpl.class);
    }

    @Test
    public void testExternalForeignEntityField() {
        ExternalForeignEntityFieldMetadata citizenIdFm = (ExternalForeignEntityFieldMetadata) employeeMetadata.getFieldMetadata("citizenId");

        Assert.assertEquals(citizenIdFm.getEntityType(), CitizenImpl.class);
    }

    @Test
    public void testPrimitiveCollection() {
        CollectionFieldMetadata nickFmInTypedSet = (CollectionFieldMetadata) employeeMetadata.getFieldMetadata("nickNameSet");
        CollectionFieldMetadata nickFmInSet = (CollectionFieldMetadata) employeeMetadata.getFieldMetadata("nickNameSetNonType");
        CollectionFieldMetadata nickFmInList = (CollectionFieldMetadata) employeeMetadata.getFieldMetadata("nickNameList");
        ArrayFieldMetadata nickFmInArray = (ArrayFieldMetadata) employeeMetadata.getFieldMetadata("nickNameArray");

        Assert.assertNotNull(nickFmInTypedSet);
        Assert.assertNotNull(nickFmInSet);
        Assert.assertNotNull(nickFmInList);
        Assert.assertNotNull(nickFmInArray);

        Assert.assertTrue(nickFmInTypedSet.isCollectionField());
        Assert.assertTrue(nickFmInSet.isCollectionField());
        Assert.assertTrue(nickFmInList.isCollectionField());
        Assert.assertTrue(nickFmInArray.isCollectionField());

        Assert.assertEquals(String.class, nickFmInTypedSet.getElementType().getBasicType());
        Assert.assertEquals(String.class, nickFmInSet.getElementType().getBasicType());
        Assert.assertEquals(String.class, nickFmInList.getElementType().getBasicType());
    }

    @Test
    public void testPrimitiveMap() {
        MapFieldMetadata phoneNumbersFm = (MapFieldMetadata) employeeMetadata.getFieldMetadata("phoneNumbers");

        Assert.assertNotNull(phoneNumbersFm);
        Assert.assertTrue(phoneNumbersFm.isCollectionField());

        Assert.assertEquals(phoneNumbersFm.getKeyType().getBasicType(), PhoneType.class);
        Assert.assertEquals(phoneNumbersFm.getValueType().getBasicType(), String.class);
    }

    @Test
    public void testEmbeddedCollection() {
        CollectionFieldMetadata vacationBookingsFm = (CollectionFieldMetadata) employeeMetadata.getFieldMetadata("vacationBookings");
        Assert.assertNotNull(vacationBookingsFm);
        Assert.assertTrue(vacationBookingsFm.isCollectionField());
        Assert.assertNull(vacationBookingsFm.getElementType().getBasicType());
        Assert.assertNotNull(vacationBookingsFm.getElementType().getAsEmbeddedClassMetadata());
        Assert.assertNull(vacationBookingsFm.getElementType().getEntityType());
    }

    @Test
    public void testEmbeddedMap() {
        MapFieldMetadata employeesByNameXFm = (MapFieldMetadata) departmentMetadata.getFieldMetadata("employeesByNameX");
        Assert.assertNotNull(employeesByNameXFm);
        Assert.assertTrue(employeesByNameXFm.isCollectionField());
        Assert.assertNull(employeesByNameXFm.getKeyType().getBasicType());
        Assert.assertNotNull(employeesByNameXFm.getKeyType().getAsEmbeddedClassMetadata());
        Assert.assertNull(employeesByNameXFm.getKeyType().getEntityType());
    }

    @Test
    public void testEntityCollection() {
        CollectionFieldMetadata departmentEmployeeFm = (CollectionFieldMetadata) departmentMetadata.getFieldMetadata("employees");
        CollectionFieldMetadata departmentEmployeeListFm = (CollectionFieldMetadata) departmentMetadata.getFieldMetadata("employeesList");
        Assert.assertNotNull(departmentEmployeeFm);
        Assert.assertNotNull(departmentEmployeeListFm);
        Assert.assertTrue(departmentEmployeeFm.isCollectionField());
        Assert.assertTrue(departmentEmployeeListFm.isCollectionField());

        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeFm.getElementType().getEntityType());
        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeListFm.getElementType().getEntityType());
    }

    @Test
    public void testEntityMap() {
        {
            MapFieldMetadata employeesByNameFm = (MapFieldMetadata) departmentMetadata.getFieldMetadata("employeesByName");
            Assert.assertNotNull(employeesByNameFm);
            Assert.assertTrue(employeesByNameFm.isCollectionField());
            Assert.assertNull(employeesByNameFm.getValueType().getBasicType());
            Assert.assertNull(employeesByNameFm.getValueType().getAsEmbeddedClassMetadata());
            Assert.assertEquals(EmployeeImpl.class, employeesByNameFm.getValueType().getEntityType());
        }
        {
            MapFieldMetadata employeesFm = (MapFieldMetadata) departmentMetadata.getFieldMetadata("employeesByUnTypedId");
            Assert.assertNotNull(employeesFm);
            Assert.assertTrue(employeesFm.isCollectionField());
            Assert.assertNull(employeesFm.getValueType().getBasicType());
            Assert.assertNull(employeesFm.getValueType().getAsEmbeddedClassMetadata());
            Assert.assertEquals(EmployeeImpl.class, employeesFm.getValueType().getEntityType());
        }
        {
            MapFieldMetadata employeesFm = (MapFieldMetadata) departmentMetadata.getFieldMetadata("employeesByUnTypedName");
            Assert.assertNotNull(employeesFm);
            Assert.assertTrue(employeesFm.isCollectionField());
            Assert.assertNull(employeesFm.getValueType().getBasicType());
            Assert.assertNull(employeesFm.getValueType().getAsEmbeddedClassMetadata());
            Assert.assertEquals(EmployeeImpl.class, employeesFm.getValueType().getEntityType());
        }
//        CollectionFieldMetadata departmentEmployeeFm = (CollectionFieldMetadata) departmentMetadata.getFieldMetadata("employees");
//        CollectionFieldMetadata departmentEmployeeListFm = (CollectionFieldMetadata) departmentMetadata.getFieldMetadata("employeesList");
//        Assert.assertNotNull(departmentEmployeeFm);
//        Assert.assertNotNull(departmentEmployeeListFm);
//        Assert.assertTrue(departmentEmployeeFm.isCollectionField());
//        Assert.assertTrue(departmentEmployeeListFm.isCollectionField());
//
//        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeFm.getEntityType());
//        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeListFm.getEntityType());
    }
}