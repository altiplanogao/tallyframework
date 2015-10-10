package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.impl.EntityPermissionSpecial;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/20.
 */
public class PermissionEntryTest {

    @Test
    public void testClone() {
        IEntityPermissionSpecial permissionEntry = new EntityPermissionSpecial("xxx", Access.Read);
        IEntityPermissionSpecial clone = permissionEntry.clone();
        Assert.assertEquals(permissionEntry, clone);
    }


    @Test
    public void testMerge() {
        IEntityPermissionSpecial permSpAR = new EntityPermissionSpecial("aaa", Access.Read);
        IEntityPermissionSpecial permSpAU = new EntityPermissionSpecial("aaa", Access.Update);
        IEntityPermissionSpecial permSpARU = new EntityPermissionSpecial("aaa", Access.Read.merge(Access.Update));

        try {
            IEntityPermissionSpecial permSpBU = new EntityPermissionSpecial("bbb", Access.Update);
            IEntityPermissionSpecial permSpManualARU = new EntityPermissionSpecial(permSpAR).merge(permSpBU);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            Assert.fail();
        }

        IEntityPermissionSpecial permSpManualARU = new EntityPermissionSpecial(permSpAR).merge(permSpAU);
        Assert.assertEquals(permSpARU, permSpManualARU);
    }

}
