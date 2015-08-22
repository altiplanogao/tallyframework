package com.taoswork.tallybook.general.authority.core.verifier;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.permission.authorities.ISimplePermissionAuthority;
import com.taoswork.tallybook.general.authority.core.resource.ResourceProtectionManager;
import com.taoswork.tallybook.general.authority.core.resource.impl.VirtualResourceProtectionMapping;
import com.taoswork.tallybook.general.authority.core.verifier.impl.VirtualResourceSecurityVerifier;
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

    private final VirtualResourceProtectionMapping protectDocMenuAnyAction = new VirtualResourceProtectionMapping(
        docMenu, menuVisible, resourceEntry, Access.Read.merge(Access.Create), ProtectionMode.FitAny);
    private final VirtualResourceProtectionMapping protectDocMenuClick = new VirtualResourceProtectionMapping(
        docMenu, menuClick, resourceEntry, Access.Read);
    private final VirtualResourceProtectionMapping protectDocMenuDbClick = new VirtualResourceProtectionMapping(
        docMenu, menuDbClick, resourceEntry, Access.Update);

    private final VirtualResourceProtectionMapping protectImgMenuClick = new VirtualResourceProtectionMapping(
        imgMenu, menuClick, TypesEnums.Image, Access.Read);

    @Test
    public void testPermissionForType() {
        ResourceProtectionManager resourceManager = mocker.resourceManager(true, ProtectionMode.FitAll);
        VirtualResourceSecurityVerifier securityVerifier = new VirtualResourceSecurityVerifier(resourceManager);
        securityVerifier
            .register(protectDocMenuAnyAction)
            .register(protectDocMenuClick)
            .register(protectDocMenuDbClick)
            .register(protectImgMenuClick);

        for (IPermissionAuthority user : new IPermissionAuthority[]{mocker.authAB, mocker.authG, mocker.authGAB}) {
            Assert.assertTrue(securityVerifier.canAccess(user, menuVisible, docMenu));
            Assert.assertTrue(securityVerifier.canAccess(user, menuClick, docMenu));
            Assert.assertFalse(securityVerifier.canAccess(user, menuDbClick, docMenu));
            Assert.assertFalse(securityVerifier.canAccess(user, menuClick, imgMenu));

            Assert.assertFalse(securityVerifier.canAccess(user, menuVisible, TypesEnums.Image));
            Assert.assertFalse(securityVerifier.canAccess(user, menuVisible, TypesEnums.File));
            Assert.assertFalse(securityVerifier.canAccess(user, menuVisible, TypesEnums.Menu));
        }

        {
            IPermissionAuthority user = mocker.authN;
            Assert.assertFalse(securityVerifier.canAccess(user, menuVisible, docMenu));
            Assert.assertFalse(securityVerifier.canAccess(user, menuClick, docMenu));
            Assert.assertFalse(securityVerifier.canAccess(user, menuDbClick, docMenu));
            Assert.assertFalse(securityVerifier.canAccess(user, menuClick, imgMenu));

            Assert.assertFalse(securityVerifier.canAccess(user, menuVisible, TypesEnums.Image));
            Assert.assertFalse(securityVerifier.canAccess(user, menuVisible, TypesEnums.File));
            Assert.assertFalse(securityVerifier.canAccess(user, menuVisible, TypesEnums.Menu));
        }
    }
}
