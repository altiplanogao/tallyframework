package com.taoswork.tallybook.authority.core.permission;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.permission.impl.KPermissionCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/20.
 */
public class PermissionEntryTest {

    @Test
    public void testClone() {
        IKPermissionCase permissionEntry = new KPermissionCase("xxx", Access.Read);
        IKPermissionCase clone = permissionEntry.clone();
        Assert.assertEquals(permissionEntry, clone);
    }


    @Test
    public void testMerge() {
        IKPermissionCase permSpAR = new KPermissionCase("aaa", Access.Read);
        IKPermissionCase permSpAU = new KPermissionCase("aaa", Access.Update);
        IKPermissionCase permSpARU = new KPermissionCase("aaa", Access.Read.merge(Access.Update));

        try {
            IKPermissionCase permSpBU = new KPermissionCase("bbb", Access.Update);
            IKPermissionCase permSpManualARU = new KPermissionCase(permSpAR).merge(permSpBU);
            Assert.fail();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            Assert.fail();
        }

        IKPermissionCase permSpManualARU = new KPermissionCase(permSpAR).merge(permSpAU);
        Assert.assertEquals(permSpARU, permSpManualARU);
    }

}
