package com.taoswork.tallybook.descriptor.metadata.service;

import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.mapentry.IMapEntryDelegate;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.BasicMapFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.EntityMapFieldMeta;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.PhoneNumberByType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/11/13.
 */
public class MetaServiceTest_Fields_Map extends BaseMetadServiceTest_Fields {
    @Test
    public void testPrimitiveMap() {
        BasicMapFieldMeta pnFm = (BasicMapFieldMeta) employeeMetadata.getFieldMeta("phoneNumbers");
        Assert.assertNotNull(pnFm);

        Class<? extends IMapEntryDelegate> mapEntryDelegate = pnFm.getMapEntryDelegate();
        Assert.assertEquals(mapEntryDelegate, PhoneNumberByType.class);
    }

    @Test
    public void testEntityMap() {
        {
            EntityMapFieldMeta employeesByNameFm = (EntityMapFieldMeta) departmentMetadata.getFieldMeta("employeesByName");
            Assert.assertNotNull(employeesByNameFm);
            Assert.assertTrue(employeesByNameFm.isCollectionField());
            IClassMeta refCm = departmentMetadata.getReferencingClassMeta(EmployeeImpl.class);
            Assert.assertNotNull(refCm);
        }
        {
            EntityMapFieldMeta employeesFm = (EntityMapFieldMeta) departmentMetadata.getFieldMeta("employeesByUnTypedId");
            Assert.assertNotNull(employeesFm);
            Assert.assertTrue(employeesFm.isCollectionField());
//            EntryTypeUnion valueTypeU = employeesFm.getValueType();
//            Assert.assertNull(valueTypeU.getAsSimpleClass());
//            Assert.assertNull(valueTypeU.getAsEmbeddableClass());
//            Assert.assertEquals(EmployeeImpl.class, valueTypeU.getAsEntityClass());
        }
        {
            EntityMapFieldMeta employeesFm = (EntityMapFieldMeta) departmentMetadata.getFieldMeta("employeesByUnTypedName");
            Assert.assertNotNull(employeesFm);
            Assert.assertTrue(employeesFm.isCollectionField());
//            EntryTypeUnion valueTypeU = employeesFm.getValueType();
//            Assert.assertNull(employeesFm.getValueType().getAsSimpleClass());
//            Assert.assertNull(employeesFm.getValueType().getAsEmbeddableClass());
//            Assert.assertEquals(EmployeeImpl.class, employeesFm.getValueType().getAsEntityClass());
        }
    }
}
