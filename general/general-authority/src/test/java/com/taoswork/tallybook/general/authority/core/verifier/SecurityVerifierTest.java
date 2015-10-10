package com.taoswork.tallybook.general.authority.core.verifier;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.permission.authorities.ISimplePermissionAuthority;
import com.taoswork.tallybook.general.authority.core.resource.AccessibleAreas;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtectionManager;
import com.taoswork.tallybook.general.authority.core.verifier.impl.AccessVerifier;
import com.taoswork.tallybook.general.authority.mockup.PermissionDataMockuper;
import com.taoswork.tallybook.general.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.general.authority.mockup.resource.TypesEnums;
import com.taoswork.tallybook.general.authority.mockup.resource.domain.GuardedDoc;
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

    private final ISimplePermissionAuthority auth4MergeTestA = mocker.authorityWith(true, true, false, false, false);
    private final ISimplePermissionAuthority auth4MergeTestB = mocker.authorityWith(true, false, true, false, false);
    private final ISimplePermissionAuthority auth4MergeTestC = mocker.authorityWith(true, false, false, true, false);
    private final ISimplePermissionAuthority auth4MergeTestD = mocker.authorityWith(true, false, false, false, true);

    private AccessChecker accessChecker(IAccessVerifier accessVerifier, IPermissionAuthority user) {
        return new AccessChecker(accessVerifier, user);
    }

    private QueryLikeChecker queryLikeChecker(List<?> results) {
        return new QueryLikeChecker(results);
    }

    @Test
    public void testPermissionForType() {
        ResourceProtectionManager resourceManager = mocker.resourceManager(true, ProtectionMode.FitAll);
        IAccessVerifier accessVerifier = new AccessVerifier(resourceManager);

        for (IPermissionAuthority user : new IPermissionAuthority[]{mocker.authAB, mocker.authG, mocker.authGAB}) {
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }

        {
            IPermissionAuthority user = mocker.authN;
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }
    }

    @Test
    public void testPermissionForType_ByAlias() {
        ResourceProtectionManager resourceManager = mocker.resourceManager(true, ProtectionMode.FitAll);
        IAccessVerifier accessVerifier = new AccessVerifier(resourceManager);

        for (IPermissionAuthority user : new IPermissionAuthority[]{mocker.authAB, mocker.authG, mocker.authGAB}) {
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }

        {
            IPermissionAuthority user = mocker.authN;
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }

        resourceManager.registerAlias(resourceEntry,TypesEnums.Image)
            .registerAlias(resourceEntry, TypesEnums.File)
            .registerAlias(resourceEntry,TypesEnums.Menu);
        for (IPermissionAuthority user : new IPermissionAuthority[]{mocker.authAB, mocker.authG, mocker.authGAB}) {
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }

        {
            IPermissionAuthority user = mocker.authN;
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
        }
    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAll() {
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(true, ProtectionMode.FitAll);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);

        for (IPermissionAuthority user : new IPermissionAuthority[]{mocker.authN, mocker.authAB, mocker.authABCD}) {
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
            IPermissionAuthority user = mocker.authG;
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
            IPermissionAuthority user = mocker.authGAB;
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
            IPermissionAuthority user = mocker.authGABCD;
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
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(true, ProtectionMode.FitAny);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);


        for (IPermissionAuthority user : new IPermissionAuthority[]{mocker.authN, mocker.authAB, mocker.authABCD}) {
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
            IPermissionAuthority user = mocker.authG;
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
            IPermissionAuthority user = mocker.authGAB;
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
            IPermissionAuthority user = mocker.authGABCD;
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
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(false, ProtectionMode.FitAll);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);

        {
            IPermissionAuthority user = mocker.authN;
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
            IPermissionAuthority user = mocker.authAB;
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
            IPermissionAuthority user = mocker.authABCD;
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
            IPermissionAuthority user = mocker.authG;
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
            IPermissionAuthority user = mocker.authGAB;
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
            IPermissionAuthority user = mocker.authGABCD;
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
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(false, ProtectionMode.FitAny);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);

        {
            IPermissionAuthority user = mocker.authN;
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
            IPermissionAuthority user = mocker.authAB;
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
            IPermissionAuthority user = mocker.authABCD;
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
            IPermissionAuthority user = mocker.authG;
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
            IPermissionAuthority user = mocker.authGAB;
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
            IPermissionAuthority user = mocker.authGABCD;
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
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(true, ProtectionMode.FitAll);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);

        {
            IPermissionAuthority user = mocker.authG;
            accessChecker(accessVerifier, user)
                .multiCheck(true, mocker.docG, mocker.docG)
                .multiCheck(false, mocker.docG, mocker.docA)
                .multiCheck(false, mocker.docA, mocker.docAB)
                .multiCheck(false, mocker.docG, mocker.docAC)
                .multiCheck(true, mocker.docG, mocker.docE)
                .multiCheck(false, mocker.docG, mocker.docABCD);
        }
        {
            IPermissionAuthority user = mocker.authGAB;
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
            IPermissionAuthority user = mocker.authGABCD;

            accessChecker(accessVerifier, user)
                .multiCheck(true, mocker.docG, mocker.docA, mocker.docC, mocker.docAB, mocker.docAC, mocker.docCD, mocker.docABC, mocker.docACD, mocker.docABCD, mocker.docE, mocker.docABCDE);
        }
    }

    @Test
    public void testPermissionForInstanceTag_CheckMerge() {
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(true, ProtectionMode.FitAll);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);

        IPermissionAuthority localUserGAB = auth4MergeTestA.clone().merge(auth4MergeTestB);
        IPermissionAuthority localUserGAC = auth4MergeTestA.clone().merge(auth4MergeTestC);
        IPermissionAuthority localUserGCD = auth4MergeTestC.clone().merge(auth4MergeTestD);
        IPermissionAuthority localUserGBD = auth4MergeTestB.clone().merge(auth4MergeTestD);
        IPermissionAuthority localUserGABC = auth4MergeTestA.clone().merge(auth4MergeTestB).merge(auth4MergeTestC);
        {
            IPermissionAuthority user = localUserGAB;
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
            IPermissionAuthority user = localUserGAC;
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
            IPermissionAuthority user = localUserGCD;
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
            IPermissionAuthority user = localUserGBD;
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
            IPermissionAuthority user = localUserGABC;
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
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(true, ProtectionMode.FitAll);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);

        for (IPermissionAuthority user : new IPermissionAuthority[]{mocker.authN, mocker.authAB, mocker.authABCD}) {
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authG;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authGAB;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authGABCD;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(true, ProtectionMode.FitAny);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);

        for (IPermissionAuthority user : new IPermissionAuthority[]{mocker.authN, mocker.authAB, mocker.authABCD}) {
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authG;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authGAB;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authGABCD;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(false, ProtectionMode.FitAll);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);

        {
            IPermissionAuthority user = mocker.authN;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authAB;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authABCD;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authG;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authGAB;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authGABCD;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(false, ProtectionMode.FitAny);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);

        {
            IPermissionAuthority user = mocker.authN;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authAB;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authABCD;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authG;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authGAB;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
            IPermissionAuthority user = mocker.authGABCD;
            AccessibleAreas accessibleAreas = accessVerifier.calcAccessibleAreas(user, mocker.normalAccess, resourceEntry);
            List<GuardedDoc> results = mocker.docRepo.query(accessibleAreas, masterControlledResourceManager.getResourceProtection(resourceEntry));
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
        private final IAccessVerifier accessVerifier;
        private final IPermissionAuthority user;
        private int checked = 0;

        public AccessChecker(IAccessVerifier accessVerifier, IPermissionAuthority user) {
            this.accessVerifier = accessVerifier;
            this.user = user;
        }

        public AccessChecker multiCheck(boolean allowed, Serializable... instances) {
            if (allowed) {
                Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess,resourceEntry, instances));
            } else {
                Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess,resourceEntry, instances));
            }
            checked++;
            return this;
        }

        public AccessChecker check(Serializable instance, boolean allowed) {
            if (allowed) {
                Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess,resourceEntry, instance));
            } else {
                Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess,resourceEntry, instance));
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
