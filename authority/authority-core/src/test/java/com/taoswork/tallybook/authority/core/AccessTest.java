package com.taoswork.tallybook.authority.core;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/18.
 */
public class AccessTest {
    @Test
    public void general() {
        assertEqual(Access.None, Access.NONE, Access.EXTENDED_NONE);
        assertEqual(Access.Create, Access.CREATE, Access.EXTENDED_NONE);
        assertEqual(Access.Read, Access.READ, Access.EXTENDED_NONE);
        assertEqual(Access.Update, Access.UPDATE, Access.EXTENDED_NONE);
        assertEqual(Access.Delete, Access.DELETE, Access.EXTENDED_NONE);
        assertEqual(Access.Query, Access.QUERY, Access.EXTENDED_NONE);
        assertEqual(Access.Crudq, Access.CRUDQ_ALL, Access.EXTENDED_NONE);

        Access cuq = Access.makeGeneralAccess(Access.CREATE, Access.UPDATE, Access.QUERY);
        Access cr = Access.makeGeneralAccess(Access.CREATE, Access.READ);

        //hasGeneral
        Assert.assertTrue(cuq.hasGeneral());
        Assert.assertTrue(cuq.hasGeneral(Access.NONE));
        Assert.assertTrue(cuq.hasGeneral(Access.CREATE));
        Assert.assertFalse(cuq.hasGeneral(Access.READ));
        Assert.assertTrue(cuq.hasGeneral(Access.UPDATE));
        Assert.assertFalse(cuq.hasGeneral(Access.DELETE));
        Assert.assertTrue(cuq.hasGeneral(Access.QUERY));
        Assert.assertFalse(cuq.hasGeneral(Access.CRUDQ_ALL));
        Assert.assertFalse(cuq.hasGeneral(cr.getGeneral()));
        Assert.assertTrue(cuq.hasAnyGeneral(cr.getGeneral()));

        //hasAccess
        Assert.assertTrue(cuq.hasAccess(Access.None));
        Assert.assertTrue(cuq.hasAccess(Access.Create));
        Assert.assertFalse(cuq.hasAccess(Access.Read));
        Assert.assertTrue(cuq.hasAccess(Access.Update));
        Assert.assertFalse(cuq.hasAccess(Access.Delete));
        Assert.assertTrue(cuq.hasAccess(Access.Query));
        Assert.assertFalse(cuq.hasAccess(Access.Crudq));
        Assert.assertFalse(cuq.hasAccess(cr));
        Assert.assertTrue(cuq.hasAnyAccess(cr));


        //hasExtended
        Assert.assertFalse(cuq.hasExtended());

        {//or
            Access cuqByOr = Access.Create.or(Access.Update).or(Access.Query);
            Assert.assertEquals(cuq, cuqByOr);
        }
        {//not
            Access cuqByNot = Access.Read.or(Access.Delete).not().generalPart();
            Assert.assertEquals(cuq, cuqByNot);
        }
        {//and
            Access cuqByAnd = Access.Crudq.and(Access.Read.not().generalPart())
                .and(Access.Delete.not().generalPart());
            Assert.assertEquals(cuq, cuqByAnd);
        }
        {//xor
            Access cuqByXor = Access.Read.or(Access.Delete).xor(Access.Crudq);
            Assert.assertEquals(cuq, cuqByXor);
        }
    }

    private void assertEqual(Access access, int general, int extended) {
        Assert.assertEquals(access.getGeneral(), general);
        Assert.assertEquals(access.getExtended(), extended);
    }
}
