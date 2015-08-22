package com.taoswork.tallybook.general.authority.core.verifier;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.permission.authorities.DelegatePermissionAuthority;
import com.taoswork.tallybook.general.authority.core.permission.authorities.ISimplePermissionAuthority;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtectionManager;
import com.taoswork.tallybook.general.authority.core.verifier.impl.AccessVerifier;
import com.taoswork.tallybook.general.authority.mockup.PermissionDataMockuper;
import com.taoswork.tallybook.general.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.general.authority.mockup.resource.TypesEnums;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifierForDeletegateAuthorityTest {
    private final String resourceEntry = TypesEnums.DOC;
    private final PermissionDataMockuper mocker = new PermissionDataMockuper(resourceEntry, Access.Read);

    private final ISimplePermissionAuthority auth4MergeTestA = mocker.authorityWith(true, true, false, false, false);
    private final ISimplePermissionAuthority auth4MergeTestB = mocker.authorityWith(true, false, true, false, false);
    private final ISimplePermissionAuthority auth4MergeTestC = mocker.authorityWith(true, false, false, true, false);
    private final ISimplePermissionAuthority auth4MergeTestD = mocker.authorityWith(true, false, false, false, true);

    private AccessChecker accessChecker(IAccessVerifier accessVerifier, IPermissionAuthority user) {
        return new AccessChecker(accessVerifier, user);
    }

    @Test
    public void testPermissionForInstanceTag_CheckDelegate() {
        ResourceProtectionManager masterControlledResourceManager = mocker.resourceManager(true, ProtectionMode.FitAll);
        IAccessVerifier accessVerifier = new AccessVerifier(masterControlledResourceManager);

        IPermissionAuthority localAuthGAB = new DelegatePermissionAuthority().addAuthority(auth4MergeTestA).addAuthority(auth4MergeTestB);
        IPermissionAuthority localAuthGAC = new DelegatePermissionAuthority().addAuthority(auth4MergeTestA).addAuthority(auth4MergeTestC);
        IPermissionAuthority localAuthGCD = new DelegatePermissionAuthority().addAuthority(auth4MergeTestC).addAuthority(auth4MergeTestD);
        IPermissionAuthority localAuthGBD = new DelegatePermissionAuthority().addAuthority(auth4MergeTestB).addAuthority(auth4MergeTestD);
        IPermissionAuthority localAuthGABC = new DelegatePermissionAuthority().addAuthority(auth4MergeTestA).addAuthority(auth4MergeTestB).addAuthority(auth4MergeTestC);
        {
            IPermissionAuthority user = localAuthGAB;
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
            IPermissionAuthority user = localAuthGAC;
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
            IPermissionAuthority user = localAuthGCD;
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
            IPermissionAuthority user = localAuthGBD;
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
            IPermissionAuthority user = localAuthGABC;
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

    class AccessChecker {
        private final IAccessVerifier accessVerifier;
        private final IPermissionAuthority user;
        private int checked = 0;

        public AccessChecker(IAccessVerifier accessVerifier, IPermissionAuthority user) {
            this.accessVerifier = accessVerifier;
            this.user = user;
        }

        public AccessChecker multiCheck(boolean allowed, GuardedDocInstance... instances) {
            if (allowed) {
                Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry, instances));
            } else {
                Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry, instances));
            }
            checked++;
            return this;
        }

        public AccessChecker check(GuardedDocInstance instance, boolean allowed) {
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


}
