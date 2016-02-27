package com.taoswork.tallybook.authority.solution;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.solution.mockup.domain.access.FileResourceAccess;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/18.
 */
public class AccessTest {

    @Test
    public void extended() {

        assertEqual(Access.None, Access.NONE, Access.EXTENDED_NONE);
        assertEqual(FileResourceAccess.open, Access.NONE, FileResourceAccess.OPEN);
        assertEqual(FileResourceAccess.write, Access.NONE, FileResourceAccess.WRITE);
        assertEqual(FileResourceAccess.execute, Access.NONE, FileResourceAccess.EXECUTE);

        //hasGeneral
        Assert.assertFalse(FileResourceAccess.open.hasGeneral());
        Assert.assertFalse(FileResourceAccess.write.hasGeneral());
        Assert.assertFalse(FileResourceAccess.execute.hasGeneral());

        //hasExtended
        Assert.assertTrue(FileResourceAccess.open.hasExtended());
        Assert.assertTrue(FileResourceAccess.open.hasExtended(FileResourceAccess.OPEN));
        Assert.assertFalse(FileResourceAccess.open.hasExtended(FileResourceAccess.WRITE));
        Assert.assertFalse(FileResourceAccess.open.hasExtended(FileResourceAccess.EXECUTE));
        Assert.assertTrue(FileResourceAccess.write.hasExtended());
        Assert.assertFalse(FileResourceAccess.write.hasExtended(FileResourceAccess.OPEN));
        Assert.assertTrue(FileResourceAccess.write.hasExtended(FileResourceAccess.WRITE));
        Assert.assertFalse(FileResourceAccess.write.hasExtended(FileResourceAccess.EXECUTE));
        Assert.assertTrue(FileResourceAccess.execute.hasExtended());
        Assert.assertFalse(FileResourceAccess.execute.hasExtended(FileResourceAccess.OPEN));
        Assert.assertFalse(FileResourceAccess.execute.hasExtended(FileResourceAccess.WRITE));
        Assert.assertTrue(FileResourceAccess.execute.hasExtended(FileResourceAccess.EXECUTE));

        Access rx = Access.makeExtendedAccess(FileResourceAccess.OPEN, FileResourceAccess.EXECUTE);

        {//or
            Access rxByOr = FileResourceAccess.open.or(FileResourceAccess.execute);
            Assert.assertEquals(rx, rxByOr);
        }
        {//not
            Access rxByNot = FileResourceAccess.write.not(FileResourceAccess.ALL).extendedPart();
            Assert.assertEquals(rx, rxByNot);
        }
        {//and
            Access rxByAnd = FileResourceAccess.all.and(FileResourceAccess.write.not().extendedPart());
            Assert.assertEquals(rx, rxByAnd);
        }
        {//xor
            Access rxByXor = FileResourceAccess.all.xor(FileResourceAccess.write);
            Assert.assertEquals(rx, rxByXor);
        }
    }

    private void assertEqual(Access access, int general, int extended) {
        Assert.assertEquals(access.getGeneral(), general);
        Assert.assertEquals(access.getExtended(), extended);
    }
}
