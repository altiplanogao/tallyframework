package com.taoswork.tallybook.general.authority.core.verifier;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.permission.authorities.ISimplePermissionAuthority;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtectionManager;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtectionMapping;
import com.taoswork.tallybook.general.authority.core.verifier.impl.AccessVerifier;
import com.taoswork.tallybook.general.authority.mockup.PermissionDataMockuper;
import com.taoswork.tallybook.general.authority.mockup.resource.TypesEnums;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifierForSimulatedResourceTest {
    private final String resourceEntry = TypesEnums.DOC;
    private final PermissionDataMockuper mocker = new PermissionDataMockuper(resourceEntry, Access.Read);

    private final ISimplePermissionAuthority auth4MergeTestA = mocker.authorityWith(true, true, false, false, false);
    private final ISimplePermissionAuthority auth4MergeTestB = mocker.authorityWith(true, false, true, false, false);
    private final ISimplePermissionAuthority auth4MergeTestC = mocker.authorityWith(true, false, false, true, false);
    private final ISimplePermissionAuthority auth4MergeTestD = mocker.authorityWith(true, false, false, false, true);

    private final String docMenu = "menu.doc";
    private final Access menuClick = Access.makeExtendedAccess(0x1);
    private final Access menuDbClick = Access.makeExtendedAccess(0x2);
    private final Access menuVisible = Access.makeExtendedAccess(0x4);

    private final String imgMenu = "menu.img";

    private final ResourceProtectionMapping protectDocMenuAnyAction = new ResourceProtectionMapping(
        docMenu, menuVisible, resourceEntry, Access.Read.merge(Access.Create), ProtectionMode.FitAny);
    private final ResourceProtectionMapping protectDocMenuClick = new ResourceProtectionMapping(
        docMenu, menuClick, resourceEntry, Access.Read);
    private final ResourceProtectionMapping protectDocMenuDbClick = new ResourceProtectionMapping(
        docMenu, menuDbClick, resourceEntry, Access.Update);

    private final ResourceProtectionMapping protectImgMenuClick = new ResourceProtectionMapping(
        imgMenu, menuClick, TypesEnums.Image, Access.Read);

    @Test
    public void testPermissionForType() {
        ResourceProtectionManager resourceManager = mocker.resourceManager(true, ProtectionMode.FitAll);
        IMappedAccessVerifier accessVerifier = new AccessVerifier(resourceManager);
        accessVerifier
            .registerResourceMapping(protectDocMenuAnyAction)
            .registerResourceMapping(protectDocMenuClick)
            .registerResourceMapping(protectDocMenuDbClick)
            .registerResourceMapping(protectImgMenuClick);

        for (IPermissionAuthority user : new IPermissionAuthority[]{mocker.authAB, mocker.authG, mocker.authGAB}) {
            Assert.assertTrue(accessVerifier.canAccessMappedResource(user, menuVisible, docMenu));
            Assert.assertTrue(accessVerifier.canAccessMappedResource(user, menuClick, docMenu));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuDbClick, docMenu));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuClick, imgMenu));

            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuVisible, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuVisible, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccessMappedResource(user, menuVisible, TypesEnums.Menu));
        }

        {
            IPermissionAuthority user = mocker.authN;
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
