package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.impl.EntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.impl.EntityPermissionSpecial;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/20.
 */
public class EntityPermissionTest {
    @Test
    public void testClone() {
        IEntityPermissionSpecial aClassU = new EntityPermissionSpecial("a", Access.Update);
        IEntityPermissionSpecial bClassD = new EntityPermissionSpecial("b", Access.Delete);

        IEntityPermission docPer = new EntityPermission("doc");
        docPer.setMasterAccess(Access.Read);
        docPer.addSpecials(aClassU, bClassD);

        IEntityPermission docPerClone = docPer.clone();
        Assert.assertEquals(docPer, docPerClone);
    }

    @Test
    public void testMerge() {
        IEntityPermission docPerAbc = new EntityPermission("doc");
        {
            IEntityPermissionSpecial aClassU = new EntityPermissionSpecial("a", Access.Update);
            IEntityPermissionSpecial bClassD = new EntityPermissionSpecial("b", Access.Delete);
            IEntityPermissionSpecial cClassR = new EntityPermissionSpecial("c", Access.Read);
            docPerAbc.setMasterAccess(Access.Read);
            docPerAbc.addSpecials(aClassU, bClassD, cClassR);
        }

        IEntityPermission docPerAbd = new EntityPermission("doc");
        {
            IEntityPermissionSpecial aClassQ = new EntityPermissionSpecial("a", Access.Query);
            IEntityPermissionSpecial bClassC = new EntityPermissionSpecial("b", Access.Create);
            IEntityPermissionSpecial dClassC = new EntityPermissionSpecial("d", Access.Create);
            docPerAbd.setMasterAccess(Access.Create);
            docPerAbd.addSpecials(aClassQ, bClassC, dClassC);
        }

        IEntityPermission docPerAbcdExp = new EntityPermission("doc");
        {
            IEntityPermissionSpecial aClassQU = new EntityPermissionSpecial("a", Access.Query.merge(Access.Update));
            IEntityPermissionSpecial bClassDC = new EntityPermissionSpecial("b", Access.Delete.merge(Access.Create));
            IEntityPermissionSpecial cClassR = new EntityPermissionSpecial("c", Access.Read);
            IEntityPermissionSpecial dClassC = new EntityPermissionSpecial("d", Access.Create);
            docPerAbcdExp.setMasterAccess(Access.Create.merge(Access.Read));
            docPerAbcdExp.addSpecials(aClassQU, bClassDC, cClassR, dClassC);
        }
        {
            IEntityPermission docPerAbcMerged = new EntityPermission(docPerAbc).merge(docPerAbd);
            Assert.assertEquals(docPerAbcMerged, docPerAbcdExp);
        }


        try {
            IEntityPermission picPerAc = new EntityPermission("pic");
            {
                IEntityPermissionSpecial aClassQ = new EntityPermissionSpecial("a", Access.Query);
                IEntityPermissionSpecial cClassC = new EntityPermissionSpecial("b", Access.Create);
                docPerAbd.setMasterAccess(Access.Create);
                docPerAbd.addSpecials(aClassQ, cClassC);
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
