package com.taoswork.tallybook.dynamic.datameta.description.descriptor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/7/4.
 */
public class EntityInfoTypeTest {
    @Test
    public void typeToName(){
        Assert.assertEquals(EntityInfoType.Full.getName(), EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_FULL);
        Assert.assertEquals(EntityInfoType.Form.getName(), EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_FORM);
        Assert.assertEquals(EntityInfoType.Grid.getName(), EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_GRID);
    }

    @Test
    public void nameToType(){
        Assert.assertEquals(EntityInfoType.Full, EntityInfoTypeNames.entityInfoTypeOf(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_FULL));
        Assert.assertEquals(EntityInfoType.Form, EntityInfoTypeNames.entityInfoTypeOf(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_FORM));
        Assert.assertEquals(EntityInfoType.Grid, EntityInfoTypeNames.entityInfoTypeOf(EntityInfoTypeNames.ENTITY_INFO_TYPE_NAME_GRID));
    }
}
