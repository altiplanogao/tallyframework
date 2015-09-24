package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.testframework.domain.TPerson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class DynamicEntityMetadataAccessTest {
    private IDataService dataService = null;

    @Before
    public void setup(){
        dataService = new TallyMockupDataService();
    }

    @After
    public void teardown(){
        dataService = null;
    }

    @Test
    public void testEntityMetadataAccess() {
        DynamicEntityMetadataAccess dynamicEntityMetadataAccess = dataService.getService(DynamicEntityMetadataAccess.COMPONENT_NAME);
        Assert.assertNotNull(dynamicEntityMetadataAccess);

        Collection<Class> entityInterfaces = dynamicEntityMetadataAccess.getAllEntityInterfaces();
        Assert.assertNotNull(entityInterfaces);
        Assert.assertEquals(entityInterfaces.size(), 1);

        EntityClassTree entityClassTree = dynamicEntityMetadataAccess.getEntityClassTree(TPerson.class);
        Assert.assertEquals(TPerson.class.getName(), entityClassTree.getData().clz.getName());

        ClassTreeMetadata entityClassTreeMetadata = dynamicEntityMetadataAccess.getClassTreeMetadata(TPerson.class);
        Assert.assertEquals(TPerson.class.getName(), entityClassTreeMetadata.getName());
    }

}
