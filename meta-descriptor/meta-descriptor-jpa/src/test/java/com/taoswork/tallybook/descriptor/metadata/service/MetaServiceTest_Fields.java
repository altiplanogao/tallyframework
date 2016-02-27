package com.taoswork.tallybook.descriptor.metadata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.datadomain.base.presentation.typed.BooleanMode;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.*;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.embedded.EmbeddedFieldMeta;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.embed.EmployeeNameX;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.enumtype.CompanyType;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.ParkingSpaceImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.common.Address;
import com.taoswork.tallybook.testmaterial.jpa.domain.nature.impl.CitizenImpl;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetaServiceTest_Fields extends BaseMetadServiceTest_Fields {

    @Test
    public void testIdField() {
        for (IClassMeta classMeta : metadatas) {
            Field idField = classMeta.getIdField();
            Assert.assertNotNull(idField);

            IFieldMeta idFieldMeta = classMeta.getFieldMeta(idField.getName());
            Assert.assertNotNull(idFieldMeta);
        }
    }

    @Test
    public void testNameField() {
        for (IClassMeta classMeta : metadatas) {
            Field nameField = classMeta.getNameField();
            if (parkSpaceMetadata == classMeta) {
                Assert.assertNull(nameField);
                continue;
            }
            Assert.assertNotNull(nameField);

            IFieldMeta nameFieldMeta = classMeta.getFieldMeta(nameField.getName());
            Assert.assertNotNull(nameFieldMeta);

            Assert.assertTrue(nameFieldMeta instanceof StringFieldMeta);
        }
    }

    @Test
    public void testStringField() {
        IFieldMeta companyDesFieldMeta = companyMetadata.getFieldMeta("description");
        Assert.assertTrue(companyDesFieldMeta instanceof StringFieldMeta);
        if (companyDesFieldMeta instanceof StringFieldMeta) {
            int length = ((StringFieldMeta) companyDesFieldMeta).getLength();
            Assert.assertEquals(length, 2000);
        }
    }

    @Test
    public void testBooleanField() {
        IFieldMeta lockedFieldMeta = companyMetadata.getFieldMeta("locked");
        IFieldMeta activeFieldMeta = companyMetadata.getFieldMeta("active");
        Assert.assertTrue(lockedFieldMeta instanceof BooleanFieldMeta);
        Assert.assertTrue(activeFieldMeta instanceof BooleanFieldMeta);
        if (lockedFieldMeta instanceof BooleanFieldMeta) {
            BooleanMode mode = ((BooleanFieldMeta) lockedFieldMeta).getMode();
            Assert.assertEquals(BooleanMode.YesNo, mode);
        }
        if (activeFieldMeta instanceof BooleanFieldMeta) {
            BooleanMode mode = ((BooleanFieldMeta) activeFieldMeta).getMode();
            Assert.assertEquals(BooleanMode.TrueFalse, mode);
        }
    }

    @Test
    public void testEnumField() {
        IFieldMeta companyTypeFieldMeta = companyMetadata.getFieldMeta("companyType");
        Assert.assertTrue(companyTypeFieldMeta instanceof EnumFieldMeta);
        if (companyTypeFieldMeta instanceof EnumFieldMeta) {
            Class enumType = ((EnumFieldMeta) companyTypeFieldMeta).getEnumerationType();
            Assert.assertEquals(CompanyType.class, enumType);
        }
    }

    @Test
    public void testEmbeddedField() {
        {
            EmbeddedFieldMeta addressFieldMeta = (EmbeddedFieldMeta) companyMetadata.getFieldMeta("address");
            Assert.assertNotNull(addressFieldMeta);
            IClassMeta embeddedAddressClassMetadata = addressFieldMeta.getClassMetadata();
            Assert.assertNotNull(embeddedAddressClassMetadata);

            IClassMeta addressCm = metaService.generateMeta(Address.class, null);
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
            EmbeddedFieldMeta nameXFieldMeta = (EmbeddedFieldMeta) employeeMetadata.getFieldMeta("nameX");
            Assert.assertNotNull(nameXFieldMeta);
            IClassMeta embeddedNameXClassMetadata = nameXFieldMeta.getClassMetadata();
            Assert.assertNotNull(embeddedNameXClassMetadata);

            IClassMeta nameXCm = metaService.generateMeta(EmployeeNameX.class, null);
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
        ForeignEntityFieldMeta parkingSpaceFieldMeta = (ForeignEntityFieldMeta) employeeMetadata.getFieldMeta("parkingSpace");
        ForeignEntityFieldMeta departmentFieldMeta = (ForeignEntityFieldMeta) employeeMetadata.getFieldMeta("department");
        Assert.assertEquals(parkingSpaceFieldMeta.getTargetType(), ParkingSpaceImpl.class);
        Assert.assertEquals(departmentFieldMeta.getTargetType(), DepartmentImpl.class);

        ForeignEntityFieldMeta psEmpFieldMeta = (ForeignEntityFieldMeta) parkSpaceMetadata.getFieldMeta("employee");
        ForeignEntityFieldMeta psEmpObjFieldMeta = (ForeignEntityFieldMeta) parkSpaceMetadata.getFieldMeta("employeeObj");
        ForeignEntityFieldMeta psEmpImplFieldMeta = (ForeignEntityFieldMeta) parkSpaceMetadata.getFieldMeta("employeeImpl");
        Assert.assertEquals(psEmpFieldMeta.getTargetType(), EmployeeImpl.class);
        Assert.assertEquals(psEmpObjFieldMeta.getTargetType(), EmployeeImpl.class);
        Assert.assertEquals(psEmpImplFieldMeta.getTargetType(), EmployeeImpl.class);
    }

    @Test
    public void testExternalForeignEntityField() {
        ExternalForeignEntityFieldMeta citizenIdFm = (ExternalForeignEntityFieldMeta) employeeMetadata.getFieldMeta("citizenId");

        Assert.assertEquals(citizenIdFm.getTargetType(), CitizenImpl.class);
    }

}