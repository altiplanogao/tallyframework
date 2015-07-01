package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.CompanyImpl;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.TabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaDescriptionServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MetaDescriptionServiceTest_CompanyImpl {
    private MetaDescriptionService metaDescriptionService;

    @Before
    public void setup() {
        MetaDescriptionServiceImpl metaDescriptionServiceImpl
                = new MetaDescriptionServiceImpl();
        metaDescriptionServiceImpl.setMetadataService(new MetadataServiceImpl());
        metaDescriptionService = metaDescriptionServiceImpl;
    }

    @After
    public void teardown() {
        metaDescriptionService = null;
    }

    @Test
    public void testEntityInfo() {
        ClassMetadata classMetadata = metaDescriptionService.createClassMetadata(CompanyImpl.class);
        EntityInfo entityInfo = metaDescriptionService.getEntityInfo(classMetadata);
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

        EntityGridInfo entityGridInfo = metaDescriptionService.getEntityGridInfo(classMetadata);
        Assert.assertNotNull(entityGridInfo);
        if (entityGridInfo != null) {
            Assert.assertEquals(entityGridInfo.getFields().size(), 8);
        }
    }
}
