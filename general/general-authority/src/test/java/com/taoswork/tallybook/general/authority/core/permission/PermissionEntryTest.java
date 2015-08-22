package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.impl.PermissionEntry;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/20.
 */
public class PermissionEntryTest {

    @Test
    public void testClone() {
        IPermissionEntry permissionEntry = new PermissionEntry("xxx", Access.Read);
        IPermissionEntry clone = permissionEntry.clone();
        Assert.assertEquals(permissionEntry, clone);
    }


    @Test
    public void testMerge() {
        IPermissionEntry entryAR = new PermissionEntry("aaa", Access.Read);
        IPermissionEntry entryAU = new PermissionEntry("aaa", Access.Update);
        IPermissionEntry entryARU = new PermissionEntry("aaa", Access.Read.merge(Access.Update));

        try {
            IPermissionEntry entryBU = new PermissionEntry("bbb", Access.Update);
            IPermissionEntry entryManualARU = entryAR.clone().merge(entryBU);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            Assert.fail();
        }

        IPermissionEntry entryManualARU = entryAR.clone().merge(entryAU);
        Assert.assertEquals(entryARU, entryManualARU);
//        IPermissionEntry entryARU =
//        IPermissionEntry clone = permissionEntry.clone();
//        Assert.assertEquals(permissionEntry, clone);
    }

}
