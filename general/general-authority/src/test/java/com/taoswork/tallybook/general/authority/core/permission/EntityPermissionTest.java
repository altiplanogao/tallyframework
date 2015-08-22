package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.impl.EntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.impl.PermissionEntry;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/20.
 */
public class EntityPermissionTest {
    @Test
    public void testClone() {
        IPermissionEntry aClassU = new PermissionEntry("a", Access.Update);
        IPermissionEntry bClassD = new PermissionEntry("b", Access.Delete);

        IEntityPermission docPer = new EntityPermission("doc");
        docPer.setMasterAccess(Access.Read);
        docPer.addEntries(aClassU, bClassD);

        IEntityPermission docPerClone = docPer.clone();
        Assert.assertEquals(docPer, docPerClone);
    }

    @Test
    public void testMerge() {
        IEntityPermission docPerAbc = new EntityPermission("doc");
        {
            IPermissionEntry aClassU = new PermissionEntry("a", Access.Update);
            IPermissionEntry bClassD = new PermissionEntry("b", Access.Delete);
            IPermissionEntry cClassR = new PermissionEntry("c", Access.Read);
            docPerAbc.setMasterAccess(Access.Read);
            docPerAbc.addEntries(aClassU, bClassD, cClassR);
        }

        IEntityPermission docPerAbd = new EntityPermission("doc");
        {
            IPermissionEntry aClassQ = new PermissionEntry("a", Access.Query);
            IPermissionEntry bClassC = new PermissionEntry("b", Access.Create);
            IPermissionEntry dClassC = new PermissionEntry("d", Access.Create);
            docPerAbd.setMasterAccess(Access.Create);
            docPerAbd.addEntries(aClassQ, bClassC, dClassC);
        }

        IEntityPermission docPerAbcdExp = new EntityPermission("doc");
        {
            IPermissionEntry aClassQU = new PermissionEntry("a", Access.Query.merge(Access.Update));
            IPermissionEntry bClassDC = new PermissionEntry("b", Access.Delete.merge(Access.Create));
            IPermissionEntry cClassR = new PermissionEntry("c", Access.Read);
            IPermissionEntry dClassC = new PermissionEntry("d", Access.Create);
            docPerAbcdExp.setMasterAccess(Access.Create.merge(Access.Read));
            docPerAbcdExp.addEntries(aClassQU, bClassDC, cClassR, dClassC);
        }
        {
            IEntityPermission docPerAbcMerged = new EntityPermission(docPerAbc).merge(docPerAbd);
            Assert.assertEquals(docPerAbcMerged, docPerAbcdExp);
        }


        try {
            IEntityPermission picPerAc = new EntityPermission("pic");
            {
                IPermissionEntry aClassQ = new PermissionEntry("a", Access.Query);
                IPermissionEntry cClassC = new PermissionEntry("b", Access.Create);
                docPerAbd.setMasterAccess(Access.Create);
                docPerAbd.addEntries(aClassQ, cClassC);
            }
            IEntityPermission docPerAbcMerged = new EntityPermission(docPerAbc).merge(picPerAc);
            Assert.assertEquals(docPerAbcMerged, docPerAbcdExp);

            Assert.fail();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            Assert.fail();
        }
    }
}
