package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.EnumFacetInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.handy.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.CompanyImpl;
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
            Assert.assertEquals(entityInfo.getEntityType(), CompanyImpl.class.getName());
            Assert.assertEquals(entityInfo.getFields().size(), 15);

            Assert.assertNotNull(entityInfo);
            ITabInfo[] tabInsights = entityInfo.getTabs().toArray(new ITabInfo[]{});
            Assert.assertEquals(tabInsights.length, 3);

            ITabInfo generalTab = tabInsights[0];
            Assert.assertEquals(generalTab.getName(), "General");
            Assert.assertEquals(generalTab.getGroups().size(), 3);

            ITabInfo marketingTab = tabInsights[1];
            Assert.assertEquals(marketingTab.getName(), "Marketing");
            Assert.assertEquals(marketingTab.getGroups().size(), 2);

            ITabInfo contactTab = tabInsights[2];
            Assert.assertEquals(contactTab.getName(), "Contact");
            Assert.assertEquals(contactTab.getGroups().size(), 1);

            Assert.assertEquals(entityInfo.getGridFields().size(), 11);
        }

        IEntityInfo entityGridInfo = metaInfoService.generateEntityInfo(classMetadata, EntityInfoType.Grid);
        Assert.assertNotNull(entityGridInfo);
        if (entityGridInfo != null) {
            Assert.assertEquals(((EntityGridInfo)entityGridInfo).fields.size(), 11);
        }
    }

    @Test
    public void testEntityInfoEnum() {
        ClassMetadata classMetadata = metadataService.generateMetadata(CompanyImpl.class);
        EntityInfo entityInfo = metaInfoService.generateEntityMainInfo(classMetadata);
        Assert.assertNotNull(entityInfo);
        if (entityInfo != null) {
            FieldInfo fieldInfo = entityInfo.getField("companyType");
            EnumFacetInfo facet = (EnumFacetInfo)fieldInfo.getFacet(FieldFacetType.Enum);

            Assert.assertEquals(facet.getOptions().size(), 4);
        }
    }
}
