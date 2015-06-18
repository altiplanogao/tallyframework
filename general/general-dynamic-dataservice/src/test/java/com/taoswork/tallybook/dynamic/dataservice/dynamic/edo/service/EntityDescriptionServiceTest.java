package com.taoswork.tallybook.dynamic.dataservice.dynamic.edo.service;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.data4test.domain.CompanyImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.TabEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.impl.EntityDescriptionServiceImpl;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.impl.EntityMetadataServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class EntityDescriptionServiceTest {
    private EntityDescriptionService entityDescriptionService;

    @Before
    public void setup(){
        EntityDescriptionServiceImpl entityDescriptionServiceImpl
                = new EntityDescriptionServiceImpl();
        entityDescriptionServiceImpl.setEntityMetadataService(new EntityMetadataServiceImpl());
        entityDescriptionService = entityDescriptionServiceImpl;
    }

    @After
    public void teardown(){
        entityDescriptionService = null;
    }

    @Test
    public void testClassEdo(){
        ClassEdo classEdo = entityDescriptionService.getClassEdo(CompanyImpl.class);
        Assert.assertNotNull(classEdo);
        TabEdo[] tabEdos = classEdo.getTabs().toArray(new TabEdo[]{});
        Assert.assertEquals(tabEdos.length, 3);

        TabEdo generalTab = tabEdos[0];
        Assert.assertEquals(generalTab.getName(), "General");
        Assert.assertEquals(generalTab.getGroups().size(), 3);

        TabEdo marketingTab = tabEdos[1];
        Assert.assertEquals(marketingTab.getName(), "Marketing");
        Assert.assertEquals(marketingTab.getGroups().size(), 2);

        TabEdo contactTab = tabEdos[2];
        Assert.assertEquals(contactTab.getName(), "Contact");
        Assert.assertEquals(contactTab.getGroups().size(), 1);

        Assert.assertEquals(classEdo.getGridFields().size(), 8);
    }
}
