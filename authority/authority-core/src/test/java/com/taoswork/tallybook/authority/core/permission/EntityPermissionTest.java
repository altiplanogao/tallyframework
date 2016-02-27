package com.taoswork.tallybook.authority.core.permission;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.permission.impl.KPermission;
import com.taoswork.tallybook.authority.core.permission.impl.KPermissionCase;
import com.taoswork.tallybook.authority.core.permission.wirte.IKPermissionW;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/20.
 */
public class EntityPermissionTest {
    @Test
    public void testClone() {
        IKPermissionCase aClassU = new KPermissionCase("a", Access.Update);
        IKPermissionCase bClassD = new KPermissionCase("b", Access.Delete);

        IKPermissionW docPer = new KPermission("doc");
        docPer.setMasterAccess(Access.Read);
        docPer.addCases(aClassU, bClassD);

        IKPermission docPerClone = docPer.clone();
        Assert.assertEquals(docPer, docPerClone);
    }

    @Test
    public void testMerge() {
        IKPermissionW docPerAbc = new KPermission("doc");
        {
            IKPermissionCase aClassU = new KPermissionCase("a", Access.Update);
            IKPermissionCase bClassD = new KPermissionCase("b", Access.Delete);
            IKPermissionCase cClassR = new KPermissionCase("c", Access.Read);
            docPerAbc.setMasterAccess(Access.Read);
            docPerAbc.addCases(aClassU, bClassD, cClassR);
        }

        IKPermissionW docPerAbd = new KPermission("doc");
        {
            IKPermissionCase aClassQ = new KPermissionCase("a", Access.Query);
            IKPermissionCase bClassC = new KPermissionCase("b", Access.Create);
            IKPermissionCase dClassC = new KPermissionCase("d", Access.Create);
            docPerAbd.setMasterAccess(Access.Create);
            docPerAbd.addCases(aClassQ, bClassC, dClassC);
        }

        IKPermissionW docPerAbcdExp = new KPermission("doc");
        {
            IKPermissionCase aClassQU = new KPermissionCase("a", Access.Query.merge(Access.Update));
            IKPermissionCase bClassDC = new KPermissionCase("b", Access.Delete.merge(Access.Create));
            IKPermissionCase cClassR = new KPermissionCase("c", Access.Read);
            IKPermissionCase dClassC = new KPermissionCase("d", Access.Create);
            docPerAbcdExp.setMasterAccess(Access.Create.merge(Access.Read));
            docPerAbcdExp.addCases(aClassQU, bClassDC, cClassR, dClassC);
        }
        {
            IKPermission docPerAbcMerged = new KPermission(docPerAbc).merge(docPerAbd);
            Assert.assertEquals(docPerAbcMerged, docPerAbcdExp);
        }


        try {
            IKPermission picPerAc = new KPermission("pic");
            {
                IKPermissionCase aClassQ = new KPermissionCase("a", Access.Query);
                IKPermissionCase cClassC = new KPermissionCase("b", Access.Create);
                docPerAbd.setMasterAccess(Access.Create);
                docPerAbd.addCases(aClassQ, cClassC);
            }
            IKPermission docPerAbcMerged = new KPermission(docPerAbc).merge(picPerAc);
            Assert.assertEquals(docPerAbcMerged, docPerAbcdExp);

            Assert.fail();
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            Assert.fail();
        }
    }
}
