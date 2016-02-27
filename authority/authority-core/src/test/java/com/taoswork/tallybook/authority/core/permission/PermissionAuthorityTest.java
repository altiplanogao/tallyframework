package com.taoswork.tallybook.authority.core.permission;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.permission.authorities.ISimpleKAuthority;
import com.taoswork.tallybook.authority.mockup.PermissionDataMockuper;
import com.taoswork.tallybook.authority.mockup.resource.TypesEnums;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/20.
 */
public class PermissionAuthorityTest {
    private PermissionDataMockuper mockuper4Doc = new PermissionDataMockuper(TypesEnums.DOC, Access.Read);
    private PermissionDataMockuper mockuper4Img = new PermissionDataMockuper(TypesEnums.Image, Access.Create);

    @Test
    public void testClone() {
        //test clone
        ISimpleKAuthority authDocA = mockuper4Doc.authorityWith(true, false, true, false, true);
        ISimpleKAuthority authDocB = mockuper4Doc.authorityWith(false, true, false, true, false);
        {
            Assert.assertNotEquals(authDocA, authDocB);
            IKAuthority authDocAClone = authDocA.clone();
            Assert.assertEquals(authDocA, authDocAClone);
        }


    }

    void assertElementsEqual(Object... array) {
        if (array.length == 0) {
            return;
        }
        for (int i = 1; i < array.length; ++i) {
            Assert.assertEquals(array[0], array[i]);
        }
    }

    @Test
    public void testMerge() {
        ISimpleKAuthority authDocA = mockuper4Doc.authorityWith(true, false, true, false, true);
        ISimpleKAuthority authDocB = mockuper4Doc.authorityWith(false, true, false, true, false);
        ISimpleKAuthority authDocAB = mockuper4Doc.authorityWith(true, true, true, true, true);
        ISimpleKAuthority authImgA = mockuper4Img.authorityWith(true, false, true, false, true);
        ISimpleKAuthority authImgB = mockuper4Img.authorityWith(false, true, false, true, false);
        ISimpleKAuthority authImgAB = mockuper4Img.authorityWith(true, true, true, true, true);
        {
            IKAuthority authDocABByMerge = authDocA.clone().merge(authDocB);
            Assert.assertEquals(authDocAB, authDocABByMerge);
        }
        {
            //docA + docB + imgA
            IKAuthority o1 = authDocA.clone().merge(authDocB).merge(authImgA);
            IKAuthority o2 = authDocA.clone().merge(authDocAB).merge(authImgA);
            IKAuthority o3 = authDocAB.clone().merge(authImgA);
            IKAuthority o4 = authDocAB.clone().merge(authDocA).merge(authImgA);
            assertElementsEqual(o1, o2, o3, o4);
        }
        {
            //docA + imgA
            IKAuthority o1 = authDocA.clone().merge(authImgA);
            IKAuthority o2 = authImgA.clone().merge(authDocA);
            IKAuthority o3 = authImgA.clone().merge(authImgA).merge(authDocA);
            IKAuthority o4 = authDocA.clone().merge(authDocA).merge(authImgA);
            assertElementsEqual(o1, o2, o3, o4);
        }
        {
            //docA + docB + imgA + imgB
            IKAuthority o1 = authDocA.clone().merge(authDocB).merge(authImgA).merge(authImgB);
            IKAuthority o2 = authDocAB.clone().merge(authImgAB);
            IKAuthority o3 = authDocAB.clone().merge(authImgA).merge(authImgB);
            IKAuthority o4 = authDocA.clone().merge(authDocB).merge(authImgAB);
            assertElementsEqual(o1, o2, o3, o4);
        }

    }
}
