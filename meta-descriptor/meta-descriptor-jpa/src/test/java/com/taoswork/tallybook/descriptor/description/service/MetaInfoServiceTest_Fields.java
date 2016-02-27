package com.taoswork.tallybook.descriptor.description.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typed.*;
import com.taoswork.tallybook.descriptor.description.descriptor.group.IGroupInfo;
import com.taoswork.tallybook.descriptor.description.descriptor.tab.ITabInfo;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallybook.descriptor.description.infos.handy.EntityGridInfo;
import com.taoswork.tallybook.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.testmaterial.general.utils.CollectionAssert;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.embed.EmployeeNameX;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.ParkingSpaceImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.nature.impl.CitizenImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetaInfoServiceTest_Fields extends MetaInfoServiceTest_Fields_Base {

    @Test
    public void testEntityInfoTabs() {
        IClassMeta classMeta = metaService.generateMeta(CompanyImpl.class, null);
        EntityInfo entityInfo = metaInfoService.generateEntityMainInfo(classMeta);
        Assert.assertNotNull(entityInfo);

        Assert.assertEquals(classMeta.getReadonlyFieldMetaMap().size(), 16);
        Assert.assertEquals(entityInfo.getFields().size(), 19); //Address expanded

        if (entityInfo != null) {
            Assert.assertEquals(entityInfo.getType(), CompanyImpl.class.getName());

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
    public void testAsGrid() {
        IEntityInfo entityGridInfo = metaInfoService.generateEntityInfo(companyMetadata, EntityInfoType.Grid);
        Assert.assertNotNull(entityGridInfo);
        if (entityGridInfo != null) {
            Assert.assertEquals(((EntityGridInfo) entityGridInfo).fields.size(), 14);
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

            if (parkSpaceInfo == entityInfo) {
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
        EnumFieldInfo companyTypeField = (EnumFieldInfo) fieldInfo;
        Assert.assertNotNull(companyTypeField);

        Collection<String> options = companyTypeField.getOptions();
        Map<String, String> optionsFriendly = companyTypeField.getOptionsFriendly();

        Assert.assertArrayEquals(new Object[]{"National", "Multinationals", "Private", "Unknown"}, options.toArray());
        Assert.assertEquals(options.size(), optionsFriendly.size());
        for (String op : options) {
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

            IClassMeta nameXCm = metaService.generateMeta(EmployeeNameX.class, null);
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
                    fnFiJ = fnFiJ.replace("nameX.", "").replace("" + firstNameFieldInfo.getOrder(), "" + firstNameFieldInfoRef.getOrder());

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


}