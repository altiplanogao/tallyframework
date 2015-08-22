package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.authorities.ISimplePermissionAuthority;
import com.taoswork.tallybook.general.authority.mockup.PermissionDataMockuper;
import com.taoswork.tallybook.general.authority.mockup.resource.TypesEnums;
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
        ISimplePermissionAuthority authDocA = mockuper4Doc.authorityWith(true, false, true, false, true);
        ISimplePermissionAuthority authDocB = mockuper4Doc.authorityWith(false, true, false, true, false);
        {
            Assert.assertNotEquals(authDocA, authDocB);
            IPermissionAuthority authDocAClone = authDocA.clone();
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
        ISimplePermissionAuthority authDocA = mockuper4Doc.authorityWith(true, false, true, false, true);
        ISimplePermissionAuthority authDocB = mockuper4Doc.authorityWith(false, true, false, true, false);
        ISimplePermissionAuthority authDocAB = mockuper4Doc.authorityWith(true, true, true, true, true);
        ISimplePermissionAuthority authImgA = mockuper4Img.authorityWith(true, false, true, false, true);
        ISimplePermissionAuthority authImgB = mockuper4Img.authorityWith(false, true, false, true, false);
        ISimplePermissionAuthority authImgAB = mockuper4Img.authorityWith(true, true, true, true, true);
        {
            IPermissionAuthority authDocABByMerge = authDocA.clone().merge(authDocB);
            Assert.assertEquals(authDocAB, authDocABByMerge);
        }
        {
            //docA + docB + imgA
            IPermissionAuthority o1 = authDocA.clone().merge(authDocB).merge(authImgA);
            IPermissionAuthority o2 = authDocA.clone().merge(authDocAB).merge(authImgA);
            IPermissionAuthority o3 = authDocAB.clone().merge(authImgA);
            IPermissionAuthority o4 = authDocAB.clone().merge(authDocA).merge(authImgA);
            assertElementsEqual(o1, o2, o3, o4);
        }
        {
            //docA + imgA
            IPermissionAuthority o1 = authDocA.clone().merge(authImgA);
            IPermissionAuthority o2 = authImgA.clone().merge(authDocA);
            IPermissionAuthority o3 = authImgA.clone().merge(authImgA).merge(authDocA);
            IPermissionAuthority o4 = authDocA.clone().merge(authDocA).merge(authImgA);
            assertElementsEqual(o1, o2, o3, o4);
        }
        {
            //docA + docB + imgA + imgB
            IPermissionAuthority o1 = authDocA.clone().merge(authDocB).merge(authImgA).merge(authImgB);
            IPermissionAuthority o2 = authDocAB.clone().merge(authImgAB);
            IPermissionAuthority o3 = authDocAB.clone().merge(authImgA).merge(authImgB);
            IPermissionAuthority o4 = authDocA.clone().merge(authDocB).merge(authImgAB);
            assertElementsEqual(o1, o2, o3, o4);
        }

    }
}
