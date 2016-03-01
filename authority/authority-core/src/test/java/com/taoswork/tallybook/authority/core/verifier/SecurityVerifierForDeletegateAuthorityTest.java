package com.taoswork.tallybook.authority.core.verifier;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.authorities.DelegateKAuthority;
import com.taoswork.tallybook.authority.core.permission.authorities.ISimpleKAuthority;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;
import com.taoswork.tallybook.authority.core.resource.link.KProtectionMapping;
import com.taoswork.tallybook.authority.core.verifier.impl.KAccessVerifier;
import com.taoswork.tallybook.authority.mockup.PermissionDataMockuper;
import com.taoswork.tallybook.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.authority.mockup.resource.TypesEnums;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifierForDeletegateAuthorityTest {
    private final String resourceEntry = TypesEnums.DOC;
    private final PermissionDataMockuper mocker = new PermissionDataMockuper(resourceEntry, Access.Read);

    private final ISimpleKAuthority auth4MergeTestA = mocker.authorityWith(true, true, false, false, false);
    private final ISimpleKAuthority auth4MergeTestB = mocker.authorityWith(true, false, true, false, false);
    private final ISimpleKAuthority auth4MergeTestC = mocker.authorityWith(true, false, false, true, false);
    private final ISimpleKAuthority auth4MergeTestD = mocker.authorityWith(true, false, false, false, true);

    private AccessChecker accessChecker(IKAccessVerifier accessVerifier, IKAuthority user) {
        return new AccessChecker(accessVerifier, user);
    }

    @Test
    public void testPermissionForInstanceTag_CheckDelegate() {
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        IKProtectionCenter masterControlledResourceManager = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAll);
        IKAccessVerifier accessVerifier = new KAccessVerifier(masterControlledResourceManager, protectionMapping);

        IKAuthority localAuthGAB = new DelegateKAuthority().addAuthority(auth4MergeTestA).addAuthority(auth4MergeTestB);
        IKAuthority localAuthGAC = new DelegateKAuthority().addAuthority(auth4MergeTestA).addAuthority(auth4MergeTestC);
        IKAuthority localAuthGCD = new DelegateKAuthority().addAuthority(auth4MergeTestC).addAuthority(auth4MergeTestD);
        IKAuthority localAuthGBD = new DelegateKAuthority().addAuthority(auth4MergeTestB).addAuthority(auth4MergeTestD);
        IKAuthority localAuthGABC = new DelegateKAuthority().addAuthority(auth4MergeTestA).addAuthority(auth4MergeTestB).addAuthority(auth4MergeTestC);
        {
            IKAuthority user = localAuthGAB;
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
            IKAuthority user = localAuthGAC;
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
            IKAuthority user = localAuthGCD;
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
            IKAuthority user = localAuthGBD;
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
            IKAuthority user = localAuthGABC;
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
        private final IKAccessVerifier accessVerifier;
        private final IKAuthority user;
        private int checked = 0;

        public AccessChecker(IKAccessVerifier accessVerifier, IKAuthority user) {
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
