package com.taoswork.tallybook.dynamic.dataservice.entity.description.service;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.data4test.domain.CompanyImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.tab.TabInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.service.impl.EntityDescriptionServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.impl.EntityMetadataServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class EntityDescriptionServiceTest_CompanyImpl {
    private EntityDescriptionService entityDescriptionService;

    @Before
    public void setup() {
        EntityDescriptionServiceImpl entityDescriptionServiceImpl
                = new EntityDescriptionServiceImpl();
        entityDescriptionServiceImpl.setEntityMetadataService(new EntityMetadataServiceImpl());
        entityDescriptionService = entityDescriptionServiceImpl;
    }

    @After
    public void teardown() {
        entityDescriptionService = null;
    }

    @Test
    public void testClassInfo() {
        ClassMetadata classMetadata = entityDescriptionService.createClassMetadata(CompanyImpl.class);
        EntityInfo classEdo = entityDescriptionService.getEntityInfo(classMetadata);
        Assert.assertNotNull(classEdo);
        if (classEdo != null) {
            Assert.assertNotNull(classEdo);
            TabInfo[] tabEdos = classEdo.getTabs().toArray(new TabInfo[]{});
            Assert.assertEquals(tabEdos.length, 3);

            TabInfo generalTab = tabEdos[0];
            Assert.assertEquals(generalTab.getName(), "General");
            Assert.assertEquals(generalTab.getGroups().size(), 3);

            TabInfo marketingTab = tabEdos[1];
            Assert.assertEquals(marketingTab.getName(), "Marketing");
            Assert.assertEquals(marketingTab.getGroups().size(), 2);

            TabInfo contactTab = tabEdos[2];
            Assert.assertEquals(contactTab.getName(), "Contact");
            Assert.assertEquals(contactTab.getGroups().size(), 1);

            Assert.assertEquals(classEdo.getGridFields().size(), 8);
        }

        EntityGridInfo entityGridInfo = entityDescriptionService.getEntityGridInfo(classMetadata);
        Assert.assertNotNull(entityGridInfo);
        if (entityGridInfo != null) {
            Assert.assertEquals(entityGridInfo.getFields().size(), 8);
        }
    }
}
