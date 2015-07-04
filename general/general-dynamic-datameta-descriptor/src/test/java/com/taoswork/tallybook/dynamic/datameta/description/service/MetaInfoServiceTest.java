package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.A;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.AA;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.AAA;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MetaInfoServiceTest {
    private MetaInfoService metaInfoService;
    private MetadataService metadataService;

    @Before
    public void setup() {
        metadataService = new MetadataServiceImpl();
        MetaInfoServiceImpl metaInfoServiceImpl
                = new MetaInfoServiceImpl();
        metaInfoService = metaInfoServiceImpl;
    }

    @After
    public void teardown() {
        metaInfoService = null;
        metadataService = null;
    }

    @Test
    public void testClassInfo() {
        EntityClassTreeAccessor accessor = new EntityClassTreeAccessor();
        EntityClassTree classTree = new EntityClassTree(A.class);
        accessor.add(classTree, AA.class);
        accessor.add(classTree, AAA.class);

        ClassMetadata classMetadata = metadataService.generateMetadata(classTree, true);
        EntityInfo entityInfo = metaInfoService.generateEntityInfo(classMetadata);
        Assert.assertNotNull(entityInfo);
        if (entityInfo != null) {
            Assert.assertNotNull(entityInfo);
            Assert.assertEquals(entityInfo.getGridFields().size(), 3);
        }

        EntityGridInfo entityGridInfo = metaInfoService.generateEntityGridInfo(classMetadata);
        Assert.assertNotNull(entityGridInfo);
        {
            Collection<? extends FieldInfo> gridFieldInfos = entityGridInfo.getFields();
            FieldInfo[] gridFieldNameArray = gridFieldInfos.toArray(new FieldInfo[]{});
//            Assert.assertTrue("a".equals(gridFieldNameArray[0].getName()));
//            Assert.assertTrue("aa".equals(gridFieldNameArray[1].getName()));
//            Assert.assertTrue("aaa".equals(gridFieldNameArray[2].getName()));
        }
    }
}
