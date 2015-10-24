package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed.EnumFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.IGroupInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.base.ITabInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.handy.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testframework.general.CollectionAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

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

        Assert.assertEquals(classMetadata.getReadonlyFieldMetadataMap().size(), 16);
        Assert.assertEquals(entityInfo.getFields().size(), 19); //Address expanded

        if (entityInfo != null) {
            Assert.assertEquals(entityInfo.getEntityType(), CompanyImpl.class.getName());

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
            IGroupInfo generalGp = contactTab.getGroups().get(0);
            Assert.assertNotNull(generalGp);
            Collection<String> contactGeneralFields = generalGp.getFields();
            Assert.assertEquals(contactGeneralFields.size(), 6);
            CollectionAssert.ensureFullyCover(contactGeneralFields,
                "email", "phone",
                "address.street","address.city","address.state","address.zip");

            Assert.assertEquals(entityInfo.getGridFields().size(), 14);
        }

        IEntityInfo entityGridInfo = metaInfoService.generateEntityInfo(classMetadata, EntityInfoType.Grid);
        Assert.assertNotNull(entityGridInfo);
        if (entityGridInfo != null) {
            Assert.assertEquals(((EntityGridInfo)entityGridInfo).fields.size(), 14);
        }
    }

    @Test
    public void testEntityInfoEnum() {
        ClassMetadata classMetadata = metadataService.generateMetadata(CompanyImpl.class);
        EntityInfo entityInfo = metaInfoService.generateEntityMainInfo(classMetadata);
        Assert.assertNotNull(entityInfo);
        if (entityInfo != null) {
            IFieldInfo fieldInfo = entityInfo.getField("companyType");
            EnumFieldInfo enumFieldInfo = (EnumFieldInfo)fieldInfo;
            Assert.assertNotNull(enumFieldInfo);
            Assert.assertEquals(enumFieldInfo.getOptions().size(), 4);
        }
    }
}
