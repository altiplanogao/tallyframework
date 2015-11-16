package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typedcollection.CollectionFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.StringEntryDelegate;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.NicknameEntryDelegate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

        Assert.assertEquals(CollectionModel.Primitive, nickFmInTypedSet.getCollectionTypesUnion().getCollectionModel());
        Assert.assertEquals(CollectionModel.Primitive, nickFmInSet.getCollectionTypesUnion().getCollectionModel());
        Assert.assertEquals(CollectionModel.Primitive, nickFmInList.getCollectionTypesUnion().getCollectionModel());

        Assert.assertEquals(String.class, nickFmInTypedSet.getCollectionTypesUnion().getEntryType());
        Assert.assertEquals(Object.class, nickFmInSet.getCollectionTypesUnion().getEntryType());
        Assert.assertEquals(String.class, nickFmInList.getCollectionTypesUnion().getEntryType());

        Assert.assertEquals(String.class, nickFmInTypedSet.getCollectionTypesUnion().getEntryTargetType());
        Assert.assertEquals(String.class, nickFmInSet.getCollectionTypesUnion().getEntryTargetType());
        Assert.assertEquals(String.class, nickFmInList.getCollectionTypesUnion().getEntryTargetType());

        Assert.assertEquals(null, nickFmInTypedSet.getCollectionTypesUnion().getEntryJoinEntityType());
        Assert.assertEquals(null, nickFmInSet.getCollectionTypesUnion().getEntryJoinEntityType());
        Assert.assertEquals(null, nickFmInList.getCollectionTypesUnion().getEntryJoinEntityType());

        Assert.assertEquals(StringEntryDelegate.class, nickFmInTypedSet.getCollectionTypesUnion().getEntrySimpleDelegateType());
        Assert.assertEquals(NicknameEntryDelegate.class, nickFmInSet.getCollectionTypesUnion().getEntrySimpleDelegateType());
        Assert.assertEquals(StringEntryDelegate.class, nickFmInList.getCollectionTypesUnion().getEntrySimpleDelegateType());
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
