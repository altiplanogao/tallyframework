package com.taoswork.tallybook.descriptor.dataio.in.translator;

import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IClassMetaAccess;
import com.taoswork.tallybook.descriptor.service.MetaService;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.ICompany;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.IDepartment;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.IEmployee;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.enumtype.CompanyType;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.common.Address;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
public class EntityTranslatorTest {
    static IClassMetaAccess classMetaAccess = new IClassMetaAccess() {
        private MetaService metaService = new JpaMetaServiceImpl();

        @Override
        public IClassMeta getClassMeta(Class<?> entityType, boolean withHierarchy) {
            return metaService.generateMeta(entityType, null, true);
        }

        @Override
        public IClassMeta getClassTreeMeta(Class<?> entityCeilingType) {
            return null;
        }
    };
    EntityTranslator translator = new EntityTranslator();

    @Test
    public void testStringField() throws TranslateException {
        Entity entity = new Entity();
        entity.setType(CompanyImpl.class);
        entity.setProperty("description", "ABCD abcd");
        ICompany company = (ICompany) translator.translate(classMetaAccess, entity, null);
        Assert.assertEquals("ABCD abcd", company.getDescription());
    }

    @Test
    public void testBooleanFields() throws TranslateException {
        Entity entity = new Entity();
        entity.setType(CompanyImpl.class);
        entity.setProperty("locked", "true");
        entity.setProperty("active", "true");
        ICompany company = (ICompany) translator.translate(classMetaAccess, entity, null);
        Assert.assertTrue(company.isLocked());
        Assert.assertTrue(company.isActive());
        entity.setProperty("locked", "false");
        entity.setProperty("active", "false");
        ICompany company2 = (ICompany) translator.translate(classMetaAccess, entity, null);
        Assert.assertFalse(company2.isLocked());
        Assert.assertFalse(company2.isActive());
    }

    @Test
    public void testEnumFields() throws TranslateException {
        Entity entity = new Entity();
        entity.setType(CompanyImpl.class);
        entity.setProperty("companyType", CompanyType.National.toString());
        ICompany company = (ICompany) translator.translate(classMetaAccess, entity, null);
        Assert.assertEquals(CompanyType.National, company.getCompanyType());
        entity.setProperty("companyType", CompanyType.Private.toString());
        ICompany company2 = (ICompany) translator.translate(classMetaAccess, entity, null);
        Assert.assertEquals(CompanyType.Private, company2.getCompanyType());
    }

    @Test
    public void testEmbeddedField() throws TranslateException {
        Entity entity = new Entity();
        entity.setType(CompanyImpl.class);
        entity.setProperty("address.street", "Street A");
        entity.setProperty("address.city", "City B");
        entity.setProperty("address.state", "State C");
        entity.setProperty("address.zip", "zip D");
        ICompany company = (ICompany) translator.translate(classMetaAccess, entity, null);
        Address address = company.getAddress();

        Assert.assertNotNull(address);
        Assert.assertEquals("Street A", address.getStreet());
        Assert.assertEquals("City B", address.getCity());
        Assert.assertEquals("State C", address.getState());
        Assert.assertEquals("zip D", address.getZip());
    }

    @Test
    public void testForeignEntityField() throws TranslateException {
        Entity entity = new Entity();
        entity.setType(EmployeeImpl.class);
        entity.setProperty("department", "{\"id\":-123,\"display\":\"department a\"}");
        IEmployee employee = (IEmployee) translator.translate(classMetaAccess, entity, null);
        Assert.assertNotNull(employee);
        IDepartment department = employee.getDepartment();
        Assert.assertNotNull(department);
        Assert.assertEquals(-123L, department.getId().longValue());
    }

    @Test
    public void testExternalForeignEntityField() throws TranslateException {
        Entity entity = new Entity();
        entity.setType(EmployeeImpl.class);
        entity.setProperty("citizenId", "{\"id\":-123,\"display\":\"Citizen Kane\"}");
        IEmployee employee = (IEmployee) translator.translate(classMetaAccess, entity, null);
        Assert.assertNotNull(employee);
        Assert.assertEquals(-123L, employee.getCitizenId().longValue());
    }
}