package com.taoswork.tallybook.descriptor.description.descriptor;

import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/7/4.
 */
public class EntityInfoTypeTest {
    @Test
    public void typeToName() {
        Assert.assertEquals(EntityInfoType.Full, EntityInfoType.instance(EntityInfoType.Names.FULL));
        Assert.assertEquals(EntityInfoType.Form, EntityInfoType.instance(EntityInfoType.Names.FORM));
        Assert.assertEquals(EntityInfoType.Grid, EntityInfoType.instance(EntityInfoType.Names.GRID));
    }
}
