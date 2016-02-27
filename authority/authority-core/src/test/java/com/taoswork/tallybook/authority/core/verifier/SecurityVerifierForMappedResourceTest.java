package com.taoswork.tallybook.authority.core.verifier;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.authorities.ISimpleKAuthority;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.link.KProtectionLink;
import com.taoswork.tallybook.authority.core.resource.link.KProtectionMapping;
import com.taoswork.tallybook.authority.core.verifier.impl.KAccessVerifier;
import com.taoswork.tallybook.authority.mockup.PermissionDataMockuper;
import com.taoswork.tallybook.authority.mockup.resource.TypesEnums;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifierForMappedResourceTest {
    private final String resourceEntry = TypesEnums.DOC;
    private final PermissionDataMockuper mocker = new PermissionDataMockuper(resourceEntry, Access.Read);

    private final ISimpleKAuthority auth4MergeTestA = mocker.authorityWith(true, true, false, false, false);
    private final ISimpleKAuthority auth4MergeTestB = mocker.authorityWith(true, false, true, false, false);
    private final ISimpleKAuthority auth4MergeTestC = mocker.authorityWith(true, false, false, true, false);
    private final ISimpleKAuthority auth4MergeTestD = mocker.authorityWith(true, false, false, false, true);

    private final String docMenu = "menu.doc";
    private final Access menuClick = Access.makeExtendedAccess(0x1);
    private final Access menuDbClick = Access.makeExtendedAccess(0x2);
    private final Access menuVisible = Access.makeExtendedAccess(0x4);

    private final String imgMenu = "menu.img";

    private final KProtectionLink protectDocMenuAnyAction = new KProtectionLink(
            docMenu, menuVisible, resourceEntry, Access.Read.merge(Access.Create), ProtectionMode.FitAny);
    private final KProtectionLink protectDocMenuClick = new KProtectionLink(
            docMenu, menuClick, resourceEntry, Access.Read);
    private final KProtectionLink protectDocMenuDbClick = new KProtectionLink(
            docMenu, menuDbClick, resourceEntry, Access.Update);

    private final KProtectionLink protectImgMenuClick = new KProtectionLink(
            imgMenu, menuClick, TypesEnums.Image, Access.Read);

    @Test
    public void testPermissionForType() {
        KProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter resourceManager = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAll);
        protectionMapping
                .registerLink(protectDocMenuAnyAction)
                .registerLink(protectDocMenuClick)
                .registerLink(protectDocMenuDbClick)
                .registerLink(protectImgMenuClick);


        IKAccessVerifier accessVerifier = new KAccessVerifier(resourceManager, protectionMapping);

        for (IKAuthority user : new IKAuthority[]{mocker.authAB, mocker.authG, mocker.authGAB}) {
            Assert.assertTrue(accessVerifier.canAccessMappedResource(user, menuVisible, docMenu));
            Assert.assertTrue(accessVerifier.canAccessMappedResource(user, menuClick, docMenu));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuDbClick, docMenu));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuClick, imgMenu));

            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuVisible, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuVisible, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuVisible, TypesEnums.Menu));
        }

        {
            IKAuthority user = mocker.authN;
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuVisible, docMenu));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuClick, docMenu));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuDbClick, docMenu));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuClick, imgMenu));

            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuVisible, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuVisible, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuVisible, TypesEnums.Menu));
        }
    }
}
