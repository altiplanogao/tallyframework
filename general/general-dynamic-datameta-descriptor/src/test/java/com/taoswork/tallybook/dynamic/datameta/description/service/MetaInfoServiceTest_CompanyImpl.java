package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.tab.TabInsight;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.CompanyImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MetaInfoServiceTest_CompanyImpl {
    private MetadataService metadataService;
    private MetaInfoService metaInfoService;

    @Before
    public void setup() {
        metadataService = new MetadataServiceImpl();
        metaInfoService = new MetaInfoServiceImpl();
    }

    @After
    public void teardown() {
        metaInfoService = null;
    }

    @Test
    public void testEntityInfo() {
        ClassMetadata classMetadata = metadataService.generateMetadata(CompanyImpl.class);
        EntityInfo entityInfo = metaInfoService.generateEntityMainInfo(classMetadata);
        Assert.assertNotNull(entityInfo);
        if (entityInfo != null) {
            Assert.assertNotNull(entityInfo);
            TabInsight[] tabInsights = entityInfo.getTabs().toArray(new TabInsight[]{});
            Assert.assertEquals(tabInsights.length, 3);

            TabInsight generalTab = tabInsights[0];
            Assert.assertEquals(generalTab.getName(), "General");
            Assert.assertEquals(generalTab.getGroups().size(), 3);

            TabInsight marketingTab = tabInsights[1];
            Assert.assertEquals(marketingTab.getName(), "Marketing");
            Assert.assertEquals(marketingTab.getGroups().size(), 2);

            TabInsight contactTab = tabInsights[2];
            Assert.assertEquals(contactTab.getName(), "Contact");
            Assert.assertEquals(contactTab.getGroups().size(), 1);

            Assert.assertEquals(entityInfo.getGridFields().size(), 8);
        }

        EntityInfo entityGridInfo = metaInfoService.generateEntityMainInfo(classMetadata);
        Assert.assertNotNull(entityGridInfo);
        if (entityGridInfo != null) {
            Assert.assertEquals(entityGridInfo.getFields().size(), 8);
        }
    }
}
