package com.taoswork.tallybook.dynamic.datameta.description.descriptor;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/7/4.
 */
public class EntityInfoTypeTest {
    @Test
    public void typeToName(){
        Assert.assertEquals(EntityInfoType.Full, EntityInfoType.instance(EntityInfoType.NAME_OF_FULL));
        Assert.assertEquals(EntityInfoType.Form, EntityInfoType.instance(EntityInfoType.NAME_OF_FORM));
        Assert.assertEquals(EntityInfoType.Grid, EntityInfoType.instance(EntityInfoType.NAME_OF_GRID));
    }
}