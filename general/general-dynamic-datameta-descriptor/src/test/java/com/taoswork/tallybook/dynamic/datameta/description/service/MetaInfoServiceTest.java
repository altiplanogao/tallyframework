package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.A;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.AA;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.AAA;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

        IClassMetadata classMetadata = metadataService.generateMetadata(classTree, null, true);
        EntityInfo entityInfo = metaInfoService.generateEntityMainInfo(classMetadata);
        Assert.assertNotNull(entityInfo);
        Assert.assertEquals(entityInfo.getEntityType(), A.class.getName());
        Assert.assertEquals(entityInfo.getGridFields().size(), 3);
    }
}
