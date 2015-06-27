package com.taoswork.tallybook.dynamic.dataservice.entity.description.service;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.data4test.domain.meta.A;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.data4test.domain.meta.AA;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.data4test.domain.meta.AAA;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.service.impl.EntityDescriptionServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.impl.EntityMetadataServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class EntityDescriptionServiceTest {
    private EntityDescriptionService entityDescriptionService;
    private EntityMetadataService entityMetadataService;

    @Before
    public void setup() {
        entityMetadataService = new EntityMetadataServiceImpl();
        EntityDescriptionServiceImpl entityDescriptionServiceImpl
                = new EntityDescriptionServiceImpl();
        entityDescriptionServiceImpl.setEntityMetadataService(entityMetadataService);
        entityDescriptionService = entityDescriptionServiceImpl;
    }

    @After
    public void teardown() {
        entityDescriptionService = null;
        entityMetadataService = null;
    }

    @Test
    public void testClassInfo() {
        EntityClassTreeAccessor accessor = new EntityClassTreeAccessor();
        EntityClassTree classTree = new EntityClassTree(A.class);
        accessor.add(classTree, AA.class);
        accessor.add(classTree, AAA.class);

        ClassMetadata classMetadata = entityMetadataService.getClassTreeMetadata(classTree);
        EntityInfo entityInfo = entityDescriptionService.getEntityInfo(classMetadata);
        Assert.assertNotNull(entityInfo);
        if (entityInfo != null) {
            Assert.assertNotNull(entityInfo);
            Assert.assertEquals(entityInfo.getGridFields().size(), 3);
        }

        EntityGridInfo entityGridInfo = entityDescriptionService.getEntityGridInfo(classMetadata);
        Assert.assertNotNull(entityGridInfo);
        {
            Collection<FieldInfo> gridFieldInfos = entityGridInfo.getFields();
            FieldInfo[] gridFieldNameArray = gridFieldInfos.toArray(new FieldInfo[]{});
//            Assert.assertTrue("a".equals(gridFieldNameArray[0].getName()));
//            Assert.assertTrue("aa".equals(gridFieldNameArray[1].getName()));
//            Assert.assertTrue("aaa".equals(gridFieldNameArray[2].getName()));
        }
    }
}
