package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.dynamic.datameta.metadata.CollectionTypesSetting;
import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.*;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.MapFieldMetadata;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.EntryType;
import com.taoswork.tallybook.testframework.domain.business.embed.EmployeeNameX;
import com.taoswork.tallybook.testframework.domain.business.embed.VacationEntry;
import com.taoswork.tallybook.testframework.domain.business.enumtype.CompanyType;
import com.taoswork.tallybook.testframework.domain.business.impl.*;
import com.taoswork.tallybook.testframework.domain.common.Address;
import com.taoswork.tallybook.testframework.domain.common.PhoneType;
import com.taoswork.tallybook.testframework.domain.nature.impl.CitizenImpl;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetadataServiceTest_Fields extends MetadataServiceTest_Fields_Base {

    @Test
    public void testIdField() {
        for (IClassMetadata classMetadata : metadatas) {
            Field idField = classMetadata.getIdField();
            Assert.assertNotNull(idField);

            IFieldMetadata idFieldMetadata = classMetadata.getFieldMetadata(idField.getName());
            Assert.assertNotNull(idFieldMetadata);
        }
    }

    @Test
    public void testNameField() {
        for (IClassMetadata classMetadata : metadatas) {
            Field nameField = classMetadata.getNameField();
            if(parkSpaceMetadata == classMetadata){
                Assert.assertNull(nameField);
                continue;
            }
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
            BooleanMode mode = ((BooleanFieldMetadata) lockedFieldMeta).getMode();
            Assert.assertEquals(BooleanMode.YesNo, mode);
        }
        if (activeFieldMeta instanceof BooleanFieldMetadata) {
            BooleanMode mode = ((BooleanFieldMetadata) activeFieldMeta).getMode();
            Assert.assertEquals(BooleanMode.TrueFalse, mode);
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
            IClassMetadata embeddedAddressClassMetadata = addressFieldMeta.getClassMetadata();
            Assert.assertNotNull(embeddedAddressClassMetadata);

            IClassMetadata addressCm = metadataService.generateMetadata(Address.class, null);
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
            IClassMetadata embeddedNameXClassMetadata = nameXFieldMeta.getClassMetadata();
            Assert.assertNotNull(embeddedNameXClassMetadata);

            IClassMetadata nameXCm = metadataService.generateMetadata(EmployeeNameX.class, null);
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
        Assert.assertEquals(parkingSpaceFieldMeta.getTargetType(), ParkingSpaceImpl.class);
        Assert.assertEquals(departmentFieldMeta.getTargetType(), DepartmentImpl.class);

        ForeignEntityFieldMetadata psEmpFieldMeta = (ForeignEntityFieldMetadata) parkSpaceMetadata.getFieldMetadata("employee");
        ForeignEntityFieldMetadata psEmpObjFieldMeta = (ForeignEntityFieldMetadata) parkSpaceMetadata.getFieldMetadata("employeeObj");
        ForeignEntityFieldMetadata psEmpImplFieldMeta = (ForeignEntityFieldMetadata) parkSpaceMetadata.getFieldMetadata("employeeImpl");
        Assert.assertEquals(psEmpFieldMeta.getTargetType(), EmployeeImpl.class);
        Assert.assertEquals(psEmpObjFieldMeta.getTargetType(), EmployeeImpl.class);
        Assert.assertEquals(psEmpImplFieldMeta.getTargetType(), EmployeeImpl.class);
    }

    @Test
    public void testExternalForeignEntityField() {
        ExternalForeignEntityFieldMetadata citizenIdFm = (ExternalForeignEntityFieldMetadata) employeeMetadata.getFieldMetadata("citizenId");

        Assert.assertEquals(citizenIdFm.getTargetType(), CitizenImpl.class);
    }

    @Test
    public void testPrimitiveMap() {
        MapFieldMetadata phoneNumbersFm = (MapFieldMetadata) employeeMetadata.getFieldMetadata("phoneNumbers");

        Assert.assertNotNull(phoneNumbersFm);
        Assert.assertTrue(phoneNumbersFm.isCollectionField());

        Assert.assertEquals(EntryType.Simple, phoneNumbersFm.getKeyType().getEntryType());
        Assert.assertEquals(EntryType.Simple, phoneNumbersFm.getValueType().getEntryType());

        Assert.assertEquals(phoneNumbersFm.getKeyType().getAsSimpleClass(), PhoneType.class);
        Assert.assertEquals(phoneNumbersFm.getValueType().getAsSimpleClass(), String.class);
    }

    @Test
    public void testEmbeddedCollection() {
        CollectionFieldMetadata vacationBookingsFm = (CollectionFieldMetadata) employeeMetadata.getFieldMetadata("vacationBookings");
        Assert.assertNotNull(vacationBookingsFm);
        Assert.assertTrue(vacationBookingsFm.isCollectionField());

        CollectionTypesSetting collectionTypesSetting = vacationBookingsFm.getCollectionTypesSetting();
        Assert.assertEquals(CollectionMode.Basic, collectionTypesSetting.getCollectionMode());
//        EntryTypeUnion entryTypeUnion =vacationBookingsFm.getEntryTypeUnion();
        Class entryClass = collectionTypesSetting.getEntryType();
        Class entryTargetClass = collectionTypesSetting.getEntryTargetType();
        Class entrySimpleDelegateType = collectionTypesSetting.getEntryPrimitiveDelegateType();
        Class entryJoinEntityType = collectionTypesSetting.getEntryJoinEntityType();

        Assert.assertEquals(Object.class, entryClass);
        Assert.assertEquals(VacationEntry.class, entryTargetClass);
        Assert.assertEquals(null, entrySimpleDelegateType);
        Assert.assertEquals(null, entryJoinEntityType);

        IClassMetadata classMetadata = employeeMetadata.getReferencingClassMetadata(entryTargetClass);
        Assert.assertNotNull(classMetadata);
    }

    @Test
    public void testEmbeddedMap() {
        MapFieldMetadata employeesByNameXFm = (MapFieldMetadata) departmentMetadata.getFieldMetadata("employeesByNameX");
        Assert.assertNotNull(employeesByNameXFm);
        Assert.assertTrue(employeesByNameXFm.isCollectionField());

        EntryTypeUnion keyTypeUnion =employeesByNameXFm.getKeyType();
        Class keyEntryClass = keyTypeUnion.getEntryClass();
        Assert.assertEquals(keyEntryClass, EmployeeNameX.class);
        EntryTypeUnion valueTypeUnion =employeesByNameXFm.getValueType();
        Class valueEntryClass = valueTypeUnion.getEntryClass();
        Assert.assertEquals(valueEntryClass, EmployeeImpl.class);

        Assert.assertNull(keyTypeUnion.getAsSimpleClass());
        Assert.assertNotNull(keyTypeUnion.getAsEmbeddableClass());
        Assert.assertNull(keyTypeUnion.getAsEntityClass());

        IClassMetadata classMetadata = departmentMetadata.getReferencingClassMetadata(keyEntryClass);
        Assert.assertNotNull(classMetadata);
        IClassMetadata classMetadata2 = departmentMetadata.getReferencingClassMetadata(valueEntryClass);
        Assert.assertNotNull(classMetadata2);
    }

    @Test
    public void testEntityCollection() {
        CollectionFieldMetadata departmentEmployeeFm = (CollectionFieldMetadata) departmentMetadata.getFieldMetadata("employees");
        CollectionFieldMetadata departmentEmployeeListFm = (CollectionFieldMetadata) departmentMetadata.getFieldMetadata("employeesList");
        Assert.assertNotNull(departmentEmployeeFm);
        Assert.assertNotNull(departmentEmployeeListFm);
        Assert.assertTrue(departmentEmployeeFm.isCollectionField());
        Assert.assertTrue(departmentEmployeeListFm.isCollectionField());

        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeFm.getCollectionTypesSetting().getEntryTargetType());
        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeListFm.getCollectionTypesSetting().getEntryTargetType());

        IClassMetadata classMetadata = departmentMetadata.getReferencingClassMetadata(EmployeeImpl.class);
        Assert.assertNotNull(classMetadata);
    }

    @Test
    public void testEntityMap() {
        {
            MapFieldMetadata employeesByNameFm = (MapFieldMetadata) departmentMetadata.getFieldMetadata("employeesByName");
            Assert.assertNotNull(employeesByNameFm);
            Assert.assertTrue(employeesByNameFm.isCollectionField());
            EntryTypeUnion valueTypeU = employeesByNameFm.getValueType();
            Assert.assertNull(valueTypeU.getAsSimpleClass());
            Assert.assertNull(valueTypeU.getAsEmbeddableClass());
            Assert.assertEquals(EmployeeImpl.class, valueTypeU.getAsEntityClass());
            IClassMetadata refCm = departmentMetadata.getReferencingClassMetadata(EmployeeImpl.class);
            Assert.assertNotNull(refCm);
        }
        {
            MapFieldMetadata employeesFm = (MapFieldMetadata) departmentMetadata.getFieldMetadata("employeesByUnTypedId");
            Assert.assertNotNull(employeesFm);
            Assert.assertTrue(employeesFm.isCollectionField());
            EntryTypeUnion valueTypeU = employeesFm.getValueType();
            Assert.assertNull(valueTypeU.getAsSimpleClass());
            Assert.assertNull(valueTypeU.getAsEmbeddableClass());
            Assert.assertEquals(EmployeeImpl.class, valueTypeU.getAsEntityClass());
        }
        {
            MapFieldMetadata employeesFm = (MapFieldMetadata) departmentMetadata.getFieldMetadata("employeesByUnTypedName");
            Assert.assertNotNull(employeesFm);
            Assert.assertTrue(employeesFm.isCollectionField());
            EntryTypeUnion valueTypeU = employeesFm.getValueType();
            Assert.assertNull(employeesFm.getValueType().getAsSimpleClass());
            Assert.assertNull(employeesFm.getValueType().getAsEmbeddableClass());
            Assert.assertEquals(EmployeeImpl.class, employeesFm.getValueType().getAsEntityClass());
        }
    }
}