package com.taoswork.tallybook.descriptor.metadata.service;

import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.StringEntry;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.BasicListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.EntityListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.LookupListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.PrimitiveListFieldMeta;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.embed.VacationEntry;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.NicknameEntry;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/11/13.
 */
public class MetaServiceTest_Fields_Collection extends BaseMetadServiceTest_Fields {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetaServiceTest.class);

    @Test
    public void testPrimitiveCollection() {
        //Set<String>
        PrimitiveListFieldMeta nickFmInTypedSet = (PrimitiveListFieldMeta) employeeMetadata.getFieldMeta("nickNameSet");
        //Set
        PrimitiveListFieldMeta nickFmInSet = (PrimitiveListFieldMeta) employeeMetadata.getFieldMeta("nickNameSetNonType");
        //List<String>
        PrimitiveListFieldMeta nickFmInList = (PrimitiveListFieldMeta) employeeMetadata.getFieldMeta("nickNameList");
//        ArrayFieldMeta nickFmInArray = (ArrayFieldMeta) employeeMetadata.getFieldMeta("nickNameArray");

        Assert.assertNotNull(nickFmInTypedSet);
        Assert.assertNotNull(nickFmInSet);
        Assert.assertNotNull(nickFmInList);
//        Assert.assertNotNull(nickFmInArray);

        Assert.assertTrue(nickFmInTypedSet.isCollectionField());
        Assert.assertTrue(nickFmInSet.isCollectionField());
        Assert.assertTrue(nickFmInList.isCollectionField());
//        Assert.assertTrue(nickFmInArray.isCollectionField());

        Assert.assertEquals(CollectionMode.Primitive, nickFmInTypedSet.getCollectionMode());
        Assert.assertEquals(CollectionMode.Primitive, nickFmInSet.getCollectionMode());
        Assert.assertEquals(CollectionMode.Primitive, nickFmInList.getCollectionMode());

        Assert.assertEquals(String.class, nickFmInTypedSet.getEntryClass());
        Assert.assertEquals(String.class, nickFmInSet.getEntryClass());
        Assert.assertEquals(String.class, nickFmInList.getEntryClass());

        Assert.assertEquals(StringEntry.class, nickFmInTypedSet.getPresentationClass());
        Assert.assertEquals(NicknameEntry.class, nickFmInSet.getPresentationClass());
        Assert.assertEquals(StringEntry.class, nickFmInList.getPresentationClass());

        IClassMeta seCm = employeeMetadata.getReferencingClassMeta(StringEntry.class);
        IClassMeta neCm = employeeMetadata.getReferencingClassMeta(NicknameEntry.class);
        Assert.assertNotNull(seCm);
        Assert.assertNotNull(neCm);
    }

    @Test
    public void testCollectionTypeEmbeddable() {
        IClassMeta employeeCm = metaService.generateMeta(EmployeeImpl.class, null, true);
        BasicListFieldMeta addressesFm = (BasicListFieldMeta)employeeCm.getFieldMeta("addresses");
        Assert.assertNotNull(addressesFm);

        BasicListFieldMeta vacationBookingsFm = (BasicListFieldMeta) employeeMetadata.getFieldMeta("vacationBookings");
        Assert.assertNotNull(vacationBookingsFm);
        Assert.assertTrue(vacationBookingsFm.isCollectionField());

        Class entryClass = vacationBookingsFm.getEntryClass();
        Assert.assertEquals(VacationEntry.class, entryClass);

        IClassMeta classMeta = employeeMetadata.getReferencingClassMeta(entryClass);
        Assert.assertNotNull(classMeta);
    }

    @Test
    public void testCollectionTypeEntity() {
//        _CollectionFieldInfo departmentEmployeeFm = (_CollectionFieldInfo) departmentInfo.getField("employees");
//        assertValidCollectionFieldInfo(departmentEmployeeFm, departmentInfo, EmployeeImpl.class);
//        _CollectionFieldInfo departmentEmployeeListFm = (_CollectionFieldInfo) departmentInfo.getField("employeesList");
//        assertValidCollectionFieldInfo(departmentEmployeeListFm, departmentInfo, EmployeeImpl.class);

        EntityListFieldMeta departmentEmployeeFm = (EntityListFieldMeta) departmentMetadata.getFieldMeta("employees");
        EntityListFieldMeta departmentEmployeeListFm = (EntityListFieldMeta) departmentMetadata.getFieldMeta("employeesList");
        Assert.assertNotNull(departmentEmployeeFm);
        Assert.assertNotNull(departmentEmployeeListFm);
        Assert.assertTrue(departmentEmployeeFm.isCollectionField());
        Assert.assertTrue(departmentEmployeeListFm.isCollectionField());

        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeFm.getEntryClass());
        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeFm.getPresentationClass());
        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeListFm.getEntryClass());
        Assert.assertEquals(EmployeeImpl.class, departmentEmployeeListFm.getPresentationClass());

        IClassMeta classMeta = departmentMetadata.getReferencingClassMeta(EmployeeImpl.class);
        Assert.assertNotNull(classMeta);

    }

    @Test
    public void testCollectionTypeLookup() {
        IClassMeta employeeCm = metaService.generateMeta(EmployeeImpl.class, null, true);
        LookupListFieldMeta projectListFm = (LookupListFieldMeta)employeeCm.getFieldMeta("projects");

        Assert.assertNotNull(projectListFm);
    }

    @Test
    public void testCollectionTypeAdornedLookup() {

    }
}
