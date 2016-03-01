package com.taoswork.tallybook.authority.core.verifier;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.authorities.ISimpleKAuthority;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.KAccessibleScope;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;
import com.taoswork.tallybook.authority.core.resource.link.KProtectionMapping;
import com.taoswork.tallybook.authority.core.verifier.impl.KAccessVerifier;
import com.taoswork.tallybook.authority.mockup.PermissionDataMockuper;
import com.taoswork.tallybook.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.authority.mockup.resource.TypesEnums;
import com.taoswork.tallybook.authority.mockup.resource.domain.GuardedDoc;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifierTest {
    private final String resourceEntry = TypesEnums.DOC;
    private final PermissionDataMockuper mocker = new PermissionDataMockuper(resourceEntry, Access.Read);

    private final ISimpleKAuthority auth4A = mocker.authorityWith(true, true, false, false, false);
    private final ISimpleKAuthority auth4B = mocker.authorityWith(true, false, true, false, false);
    private final ISimpleKAuthority auth4C = mocker.authorityWith(true, false, false, true, false);
    private final ISimpleKAuthority auth4D = mocker.authorityWith(true, false, false, false, true);

    private AccessChecker accessChecker(IKAccessVerifier accessVerifier, IKAuthority user) {
        return new AccessChecker(accessVerifier, user);
    }

    private QueryLikeChecker queryLikeChecker(List<?> results) {
        return new QueryLikeChecker(results);
    }

    @Test
    public void testPermissionForType() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter resourceManager = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAll);
        IKAccessVerifier accessVerifier = new KAccessVerifier(resourceManager, protectionMapping);

        for (IKAuthority user : new IKAuthority[]{mocker.authAB, mocker.authG, mocker.authGAB}) {
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }

        {
            IKAuthority user = mocker.authN;
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }
    }

    @Test
    public void testPermissionForType_ByAlias() {
        KProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter resourceManager = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAll);
        IKAccessVerifier accessVerifier = new KAccessVerifier(resourceManager, protectionMapping);

        for (IKAuthority user : new IKAuthority[]{mocker.authAB, mocker.authG, mocker.authGAB}) {
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }

        {
            IKAuthority user = mocker.authN;
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }

        protectionMapping.registerAlias(resourceEntry, TypesEnums.Image)
                .registerAlias(resourceEntry, TypesEnums.File)
                .registerAlias(resourceEntry, TypesEnums.Menu);
        for (IKAuthority user : new IKAuthority[]{mocker.authAB, mocker.authG, mocker.authGAB}) {
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }

        {
            IKAuthority user = mocker.authN;
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }
    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAll() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter protectionCenter = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAll);
        IKAccessVerifier accessVerifier = new KAccessVerifier(protectionCenter, protectionMapping);

        for (IKAuthority user : new IKAuthority[]{mocker.authN, mocker.authAB, mocker.authABCD}) {
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, false)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authG;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGAB;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGABCD;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAny() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter protectionCenter = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAny);
        IKAccessVerifier accessVerifier = new KAccessVerifier(protectionCenter, protectionMapping);

        for (IKAuthority user : new IKAuthority[]{mocker.authN, mocker.authAB, mocker.authABCD}) {
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, false)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authG;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGAB;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGABCD;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_SelfControl_FitAll() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter protectionCenter = mocker.resourceProtectionCenter(protectionMapping, false, ProtectionMode.FitAll);
        IKAccessVerifier accessVerifier = new KAccessVerifier(protectionCenter, protectionMapping);

        {
            IKAuthority user = mocker.authN;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, false)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authAB;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, false)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authABCD;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, false)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authG;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGAB;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGABCD;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_SelfControl_FitAny() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter protectionCenter = mocker.resourceProtectionCenter(protectionMapping, false, ProtectionMode.FitAny);
        IKAccessVerifier accessVerifier = new KAccessVerifier(protectionCenter, protectionMapping);

        {
            IKAuthority user = mocker.authN;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, false)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authAB;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, false)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authABCD;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, false)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authG;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGAB;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGABCD;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAll_FileChain() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter protectionCenter = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAll);
        IKAccessVerifier accessVerifier = new KAccessVerifier(protectionCenter, protectionMapping);

        {
            IKAuthority user = mocker.authG;
            accessChecker(accessVerifier, user)
                    .multiCheck(true, mocker.docG, mocker.docG)
                    .multiCheck(false, mocker.docG, mocker.docA)
                    .multiCheck(false, mocker.docA, mocker.docAB)
                    .multiCheck(false, mocker.docG, mocker.docAC)
                    .multiCheck(true, mocker.docG, mocker.docE)
                    .multiCheck(false, mocker.docG, mocker.docABCD);
        }
        {
            IKAuthority user = mocker.authGAB;
            accessChecker(accessVerifier, user)
                    .multiCheck(true, mocker.docG, mocker.docA, mocker.docAB, mocker.docE)
                    .multiCheck(false, mocker.docG, mocker.docA, mocker.docAB, mocker.docE, mocker.docC)
                    .multiCheck(false, mocker.docG, mocker.docA, mocker.docAB, mocker.docE, mocker.docAC)
                    .multiCheck(false, mocker.docG, mocker.docA, mocker.docAB, mocker.docE, mocker.docCD)
                    .multiCheck(false, mocker.docG, mocker.docA, mocker.docAB, mocker.docE, mocker.docABC)
                    .multiCheck(false, mocker.docG, mocker.docA, mocker.docAB, mocker.docE, mocker.docACD)
                    .multiCheck(false, mocker.docG, mocker.docA, mocker.docAB, mocker.docE, mocker.docABCD)
                    .multiCheck(false, mocker.docG, mocker.docA, mocker.docAB, mocker.docE, mocker.docABCDE);
        }
        {
            IKAuthority user = mocker.authGABCD;

            accessChecker(accessVerifier, user)
                    .multiCheck(true, mocker.docG, mocker.docA, mocker.docC, mocker.docAB, mocker.docAC, mocker.docCD, mocker.docABC, mocker.docACD, mocker.docABCD, mocker.docE, mocker.docABCDE);
        }
    }

    @Test
    public void testPermissionForInstanceTag_CheckMerge() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter protectionCenter = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAll);
        IKAccessVerifier accessVerifier = new KAccessVerifier(protectionCenter, protectionMapping);

        IKAuthority localUserGAB = auth4A.clone().merge(auth4B);
        IKAuthority localUserGAC = auth4A.clone().merge(auth4C);
        IKAuthority localUserGCD = auth4C.clone().merge(auth4D);
        IKAuthority localUserGBD = auth4B.clone().merge(auth4D);
        IKAuthority localUserGABC = auth4A.clone().merge(auth4B).merge(auth4C);
        {
            IKAuthority user = localUserGAB;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = localUserGAC;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = localUserGCD;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, false)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = localUserGBD;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = localUserGABC;
            accessChecker(accessVerifier, user)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_MasterControl_FitAll() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter protectionCenter = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAll);
        IKAccessVerifier accessVerifier = new KAccessVerifier(protectionCenter, protectionMapping);

        for (IKAuthority user : new IKAuthority[]{mocker.authN, mocker.authAB, mocker.authABCD}) {
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, false)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authG;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGAB;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_MasterControl_FitAny() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter protectionCenter = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAny);
        IKAccessVerifier accessVerifier = new KAccessVerifier(protectionCenter, protectionMapping);

        for (IKAuthority user : new IKAuthority[]{mocker.authN, mocker.authAB, mocker.authABCD}) {
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, false)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authG;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGAB;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_SelfControl_FitAll() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter protectionCenter = mocker.resourceProtectionCenter(protectionMapping, false, ProtectionMode.FitAll);
        IKAccessVerifier accessVerifier = new KAccessVerifier(protectionCenter, protectionMapping);

        {
            IKAuthority user = mocker.authN;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, false)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authAB;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, false)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, false)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authG;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGAB;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_SelfControl_FitAny() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter protectionCenter = mocker.resourceProtectionCenter(protectionMapping, false, ProtectionMode.FitAny);
        IKAccessVerifier accessVerifier = new KAccessVerifier(protectionCenter, protectionMapping);

        {
            IKAuthority user = mocker.authN;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, false)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authAB;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, false)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, false)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, false)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authG;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, false)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, false)
                    .check(mocker.docAC, false)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, false)
                    .check(mocker.docACD, false)
                    .check(mocker.docABCD, false)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGAB;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, false)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, false)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            IKAuthority user = mocker.authGABCD;
            KAccessibleScope accessibleScope = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleScope, protectionCenter.getProtection(resourceEntry));
            queryLikeChecker(results)
                    .check(mocker.docG, true)
                    .check(mocker.docA, true)
                    .check(mocker.docC, true)
                    .check(mocker.docAB, true)
                    .check(mocker.docAC, true)
                    .check(mocker.docCD, true)
                    .check(mocker.docABC, true)
                    .check(mocker.docACD, true)
                    .check(mocker.docABCD, true)
                    .check(mocker.docE, true)
                    .check(mocker.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    class AccessChecker {
        private final IKAccessVerifier accessVerifier;
        private final IKAuthority user;
        private int checked = 0;

        public AccessChecker(IKAccessVerifier accessVerifier, IKAuthority user) {
            this.accessVerifier = accessVerifier;
            this.user = user;
        }

        public AccessChecker multiCheck(boolean allowed, Serializable... instances) {
            if (allowed) {
                Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry, instances));
            } else {
                Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry, instances));
            }
            checked++;
            return this;
        }

        public AccessChecker check(Serializable instance, boolean allowed) {
            if (allowed) {
                Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry, instance));
            } else {
                Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry, instance));
            }
            checked++;
            return this;
        }

        public void ensureFullyChecked() {
            Assert.assertEquals(mocker.docCount, checked);
        }
    }

    class QueryLikeChecker {
        private List<?> results;
        private int checked = 0;

        public QueryLikeChecker(List<?> results) {
            this.results = results;
        }

        public QueryLikeChecker check(Serializable instance, boolean exist) {
            GuardedDocInstance docInstance = (GuardedDocInstance) instance;
            GuardedDoc doc = docInstance.getDomainObject();
            if (exist) {
                Assert.assertTrue(results.contains(doc));
            } else {
                Assert.assertFalse(results.contains(doc));
            }
            checked++;
            return this;
        }

        public void ensureFullyChecked() {
            Assert.assertEquals(mocker.docCount, checked);
        }
    }


}
