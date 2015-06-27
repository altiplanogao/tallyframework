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
    public void testEntityInfo() {
        ClassMetadata classMetadata = entityDescriptionService.createClassMetadata(CompanyImpl.class);
        EntityInfo entityInfo = entityDescriptionService.getEntityInfo(classMetadata);
        Assert.assertNotNull(entityInfo);
        if (entityInfo != null) {
            Assert.assertNotNull(entityInfo);
            TabInfo[] tabInfos = entityInfo.getTabs().toArray(new TabInfo[]{});
            Assert.assertEquals(tabInfos.length, 3);

            TabInfo generalTab = tabInfos[0];
            Assert.assertEquals(generalTab.getName(), "General");
            Assert.assertEquals(generalTab.getGroups().size(), 3);

            TabInfo marketingTab = tabInfos[1];
            Assert.assertEquals(marketingTab.getName(), "Marketing");
            Assert.assertEquals(marketingTab.getGroups().size(), 2);

            TabInfo contactTab = tabInfos[2];
            Assert.assertEquals(contactTab.getName(), "Contact");
            Assert.assertEquals(contactTab.getGroups().size(), 1);

            Assert.assertEquals(entityInfo.getGridFields().size(), 8);
        }

        EntityGridInfo entityGridInfo = entityDescriptionService.getEntityGridInfo(classMetadata);
        Assert.assertNotNull(entityGridInfo);
        if (entityGridInfo != null) {
            Assert.assertEquals(entityGridInfo.getFields().size(), 8);
        }
    }
}
