package com.taoswork.tallybook.descriptor.description.service;

import com.taoswork.tallybook.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallybook.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallybook.descriptor.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.descriptor.service.MetaInfoService;
import com.taoswork.tallybook.descriptor.service.MetaService;
import com.taoswork.tallybook.testmaterial.general.domain.meta.A;
import com.taoswork.tallybook.testmaterial.general.domain.meta.AA;
import com.taoswork.tallybook.testmaterial.general.domain.meta.AAA;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MetaInfoServiceTest {
    private MetaInfoService metaInfoService;
    private MetaService metaService;

    @Before
    public void setup() {
        metaService = new JpaMetaServiceImpl();
        MetaInfoServiceImpl metaInfoServiceImpl
                = new MetaInfoServiceImpl();
        metaInfoService = metaInfoServiceImpl;
    }

    @After
    public void teardown() {
        metaInfoService = null;
        metaService = null;
    }

    @Test
    public void testClassInfo() {
        EntityClassTreeAccessor accessor = new EntityClassTreeAccessor();
        EntityClassTree classTree = new EntityClassTree(A.class);
        accessor.add(classTree, AA.class);
        accessor.add(classTree, AAA.class);

        IClassMeta classMeta = metaService.generateMeta(classTree, null, true);
        EntityInfo entityInfo = metaInfoService.generateEntityMainInfo(classMeta);
        Assert.assertNotNull(entityInfo);
        Assert.assertEquals(entityInfo.getType(), A.class.getName());
        Assert.assertEquals(entityInfo.getGridFields().size(), 3);
    }
}
