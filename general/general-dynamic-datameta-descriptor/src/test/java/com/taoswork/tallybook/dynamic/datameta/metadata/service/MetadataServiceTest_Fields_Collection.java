package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.StringEntry;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.NicknameEntry;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/11/13.
 */
public class MetadataServiceTest_Fields_Collection  extends MetadataServiceTest_Fields_Base{
    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataServiceTest.class);

    @Test
    public void testPrimitiveCollection() {
        CollectionFieldMetadata nickFmInTypedSet = (CollectionFieldMetadata) employeeMetadata.getFieldMetadata("nickNameSet");
        CollectionFieldMetadata nickFmInSet = (CollectionFieldMetadata) employeeMetadata.getFieldMetadata("nickNameSetNonType");
        CollectionFieldMetadata nickFmInList = (CollectionFieldMetadata) employeeMetadata.getFieldMetadata("nickNameList");
//        ArrayFieldMetadata nickFmInArray = (ArrayFieldMetadata) employeeMetadata.getFieldMetadata("nickNameArray");

        Assert.assertNotNull(nickFmInTypedSet);
        Assert.assertNotNull(nickFmInSet);
        Assert.assertNotNull(nickFmInList);
//        Assert.assertNotNull(nickFmInArray);

        Assert.assertTrue(nickFmInTypedSet.isCollectionField());
        Assert.assertTrue(nickFmInSet.isCollectionField());
        Assert.assertTrue(nickFmInList.isCollectionField());
//        Assert.assertTrue(nickFmInArray.isCollectionField());

        Assert.assertEquals(CollectionMode.Primitive, nickFmInTypedSet.getCollectionTypesSetting().getCollectionMode());
        Assert.assertEquals(CollectionMode.Primitive, nickFmInSet.getCollectionTypesSetting().getCollectionMode());
        Assert.assertEquals(CollectionMode.Primitive, nickFmInList.getCollectionTypesSetting().getCollectionMode());

        Assert.assertEquals(String.class, nickFmInTypedSet.getCollectionTypesSetting().getEntryType());
        Assert.assertEquals(Object.class, nickFmInSet.getCollectionTypesSetting().getEntryType());
        Assert.assertEquals(String.class, nickFmInList.getCollectionTypesSetting().getEntryType());

        Assert.assertEquals(String.class, nickFmInTypedSet.getCollectionTypesSetting().getEntryTargetType());
        Assert.assertEquals(String.class, nickFmInSet.getCollectionTypesSetting().getEntryTargetType());
        Assert.assertEquals(String.class, nickFmInList.getCollectionTypesSetting().getEntryTargetType());

        Assert.assertEquals(null, nickFmInTypedSet.getCollectionTypesSetting().getEntryJoinEntityType());
        Assert.assertEquals(null, nickFmInSet.getCollectionTypesSetting().getEntryJoinEntityType());
        Assert.assertEquals(null, nickFmInList.getCollectionTypesSetting().getEntryJoinEntityType());

        Assert.assertEquals(StringEntry.class, nickFmInTypedSet.getCollectionTypesSetting().getEntryPrimitiveDelegateType());
        Assert.assertEquals(NicknameEntry.class, nickFmInSet.getCollectionTypesSetting().getEntryPrimitiveDelegateType());
        Assert.assertEquals(StringEntry.class, nickFmInList.getCollectionTypesSetting().getEntryPrimitiveDelegateType());
    }

    @Test
    public void testCollectionTypeEmbeddable(){
        IClassMetadata employeeCm = metadataService.generateMetadata(EmployeeImpl.class, null, true);
        IFieldMetadata addressesFm = employeeCm.getFieldMetadata("addresses");
    }

    @Test
    public void testCollectionTypeEntity(){

    }

    @Test
    public void testCollectionTypeLookup(){
        IClassMetadata employeeCm = metadataService.generateMetadata(EmployeeImpl.class, null, true);
        IFieldMetadata nickNameListFm = employeeCm.getFieldMetadata("projects");

    }

    @Test
    public void testCollectionTypeAdornedLookup(){

    }
}
