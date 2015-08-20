package com.taoswork.tallybook.general.authority.mockup;

import com.taoswork.tallybook.general.authority.core.ISecurityVerifier;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.engine.SecuredResourceManager;
import com.taoswork.tallybook.general.authority.core.engine.SecurityVerifier;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionEntry;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionOwner;
import com.taoswork.tallybook.general.authority.core.permission.impl.EntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.impl.PermissionEntry;
import com.taoswork.tallybook.general.authority.core.permission.impl.PermissionOwner;
import com.taoswork.tallybook.general.authority.core.resource.*;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtection;
import com.taoswork.tallybook.general.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.general.authority.mockup.resource.TypesEnums;
import com.taoswork.tallybook.general.authority.mockup.resource.domain.GuardedDoc;
import com.taoswork.tallybook.general.authority.mockup.resource.filters.doc.DocTagFilter;
import com.taoswork.tallybook.general.authority.mockup.resource.repo.DocRepo;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class PermissionTest {
    private static final String TAGA = "a";
    private static final String TAGB = "b";
    private static final String TAGC = "c";
    private static final String TAGD = "d";
    private static final String TAGE = "e";
    private static final Access normalAccess = Access.Read;

    private static final IResourceFilter docFilterA = new DocTagFilter(TAGA);
    private static final IResourceFilter docFilterB = new DocTagFilter(TAGB);
    private static final IResourceFilter docFilterC = new DocTagFilter(TAGC);
    private static final IResourceFilter docFilterD = new DocTagFilter(TAGD);

    private static final IPermissionEntry accessA = new PermissionEntry(docFilterA.getCode(), normalAccess);
    private static final IPermissionEntry accessB = new PermissionEntry(docFilterB.getCode(), normalAccess);
    private static final IPermissionEntry accessC = new PermissionEntry(docFilterC.getCode(), normalAccess);
    private static final IPermissionEntry accessD = new PermissionEntry(docFilterD.getCode(), normalAccess);

    private static DocRepo docRepo = new DocRepo();

    private static final IResourceInstance docG = docWith(false, false, false, false, false);
    private static final IResourceInstance docA = docWith(true, false, false, false, false);
    private static final IResourceInstance docC = docWith(false, false, true, false, false);
    private static final IResourceInstance docAB = docWith(true, true, false, false, false);
    private static final IResourceInstance docAC = docWith(true, false, true, false, false);
    private static final IResourceInstance docCD = docWith(false, false, true, true, false);
    private static final IResourceInstance docABC = docWith(true, true, true, false, false);
    private static final IResourceInstance docACD = docWith(true, false, true, true, false);
    private static final IResourceInstance docABCD = docWith(true, true, true, true, false);
    private static final IResourceInstance docE = docWith(false, false, false, false, true);
    private static final IResourceInstance docABCDE = docWith(true, true, true, true, true);
    private static final int docCount = 11;

    private static final IPermissionOwner userN = userWithPermission(false, false, false, false, false);
    private static final IPermissionOwner userG = userWithPermission(true, false, false, false, false);
    private static final IPermissionOwner userAB = userWithPermission(false, true, true, false, false);
    private static final IPermissionOwner userGAB = userWithPermission(true, true, true, false, false);
    private static final IPermissionOwner userABCD = userWithPermission(false, true, true, true, true);
    private static final IPermissionOwner userGABCD = userWithPermission(true, true, true, true, true);

    private static IResourceInstance docWith(boolean a, boolean b, boolean c, boolean d, boolean e) {
        StringBuilder sb = new StringBuilder();
        if (a) sb.append(TAGA);
        if (b) sb.append(TAGB);
        if (c) sb.append(TAGC);
        if (d) sb.append(TAGD);
        if (e) sb.append(TAGE);
        GuardedDoc doc = new GuardedDoc(sb.toString(), null);
        if (a) doc.addTag(TAGA);
        if (b) doc.addTag(TAGB);
        if (c) doc.addTag(TAGC);
        if (d) doc.addTag(TAGD);
        if (e) doc.addTag(TAGE);
        docRepo.pushIn(doc);
        return new GuardedDocInstance(doc);
    }

    private static IPermissionOwner userWithPermission(boolean g, boolean a, boolean b, boolean c, boolean d) {
        IEntityPermission entityPermission = new EntityPermission(TypesEnums.DOC);
        entityPermission.addEntries(
            a ? accessA : null,
            b ? accessB : null,
            c ? accessC : null,
            d ? accessD : null);

        entityPermission.setMasterAccess(g ? normalAccess : Access.None);

        IPermissionOwner permissionOwner = new PermissionOwner().addEntityPermission(entityPermission);
        return permissionOwner;
    }

    private static SecuredResourceManager resourceManager(boolean masterControlled,
                                                          ProtectionMode protectionMode) {
        SecuredResourceManager securedResourceManager = new SecuredResourceManager();
        IResourceProtection resourceProtection = new ResourceProtection(TypesEnums.DOC);
        resourceProtection.setMasterControlled(masterControlled);
        resourceProtection.setProtectionMode(protectionMode);
        resourceProtection.addFilters(docFilterA, docFilterB, docFilterC, docFilterD);

        securedResourceManager.registerResourceProtection(resourceProtection);
        return securedResourceManager;
    }

    private AccessChecker accessChecker(ISecurityVerifier securityVerifier, IPermissionOwner user) {
        return new AccessChecker(securityVerifier, user);
    }
    private AccessChecker queryLikeChecker(ISecurityVerifier securityVerifier, IPermissionOwner user) {
        return new AccessChecker(securityVerifier, user);
    }

    private QueryLikeChecker queryLikeChecker(List<?> results) {
        return new QueryLikeChecker(results);
    }

    @Test
    public void testPermissionForType() {
        SecuredResourceManager resourceManager = this.resourceManager(true, ProtectionMode.FitAll);
        ISecurityVerifier securityVerifier = new SecurityVerifier(resourceManager);

        for (IPermissionOwner user : new IPermissionOwner[]{userAB, userG, userGAB}) {
            Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, TypesEnums.DOC));
            Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, TypesEnums.Image));
            Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, TypesEnums.File));
            Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, TypesEnums.Menu));
        }

        {
            IPermissionOwner user = userN;
            Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, TypesEnums.DOC));
            Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, TypesEnums.Image));
            Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, TypesEnums.File));
            Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, TypesEnums.Menu));
        }
    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAll() {
        SecuredResourceManager masterControlledResourceManager = this.resourceManager(true, ProtectionMode.FitAll);
        ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

        for (IPermissionOwner user : new IPermissionOwner[]{userN, userAB, userABCD}) {
            accessChecker(securityVerifier, user)
                .check(docG, false)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, false)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userG;
            accessChecker(securityVerifier, user)
                .check(docG, true)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGAB;
            accessChecker(securityVerifier, user)
                .check(docG, true)
                .check(docA, true)
                .check(docC, false)
                .check(docAB, true)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGABCD;
            accessChecker(securityVerifier, user)
                .check(docG, true)
                .check(docA, true)
                .check(docC, true)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, true)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, true)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAny() {
        SecuredResourceManager masterControlledResourceManager = this.resourceManager(true, ProtectionMode.FitAny);
        ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);


        for (IPermissionOwner user : new IPermissionOwner[]{userN, userAB, userABCD}) {
            accessChecker(securityVerifier, user)
                .check(docG, false)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, false)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userG;
            accessChecker(securityVerifier, user)
                .check(docG, true)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGAB;
            accessChecker(securityVerifier, user)
                .check(docG, true)
                .check(docA, true)
                .check(docC, false)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, false)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, true)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGABCD;
            accessChecker(securityVerifier, user)
                .check(docG, true)
                .check(docA, true)
                .check(docC, true)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, true)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, true)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_SelfControl_FitAll() {
        SecuredResourceManager masterControlledResourceManager = this.resourceManager(false, ProtectionMode.FitAll);
        ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

        {
            IPermissionOwner user = userN;
            accessChecker(securityVerifier, user)
                .check(docG, false)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, false)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userAB;
            accessChecker(securityVerifier, user)
                .check(docG, false)
                .check(docA, true)
                .check(docC, false)
                .check(docAB, true)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, false)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userABCD;
            accessChecker(securityVerifier, user)
                .check(docG, false)
                .check(docA, true)
                .check(docC, true)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, true)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, false)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userG;
            accessChecker(securityVerifier, user)
                .check(docG, true)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGAB;
            accessChecker(securityVerifier, user)
                .check(docG, true)
                .check(docA, true)
                .check(docC, false)
                .check(docAB, true)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGABCD;
            accessChecker(securityVerifier, user)
                .check(docG, true)
                .check(docA, true)
                .check(docC, true)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, true)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, true)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_SelfControl_FitAny() {
            SecuredResourceManager masterControlledResourceManager = this.resourceManager(false, ProtectionMode.FitAny);
            ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

            {
                IPermissionOwner user = userN;
                accessChecker(securityVerifier, user)
                    .check(docG, false)
                    .check(docA, false)
                    .check(docC, false)
                    .check(docAB, false)
                    .check(docAC, false)
                    .check(docCD, false)
                    .check(docABC, false)
                    .check(docACD, false)
                    .check(docABCD, false)
                    .check(docE, false)
                    .check(docABCDE, false)
                    .ensureFullyChecked();
            }
            {
                IPermissionOwner user = userAB;
                accessChecker(securityVerifier, user)
                    .check(docG, false)
                    .check(docA, true)
                    .check(docC, false)
                    .check(docAB, true)
                    .check(docAC, true)
                    .check(docCD, false)
                    .check(docABC, true)
                    .check(docACD, true)
                    .check(docABCD, true)
                    .check(docE, false)
                    .check(docABCDE, true)
                    .ensureFullyChecked();
            }
            {
                IPermissionOwner user = userABCD;
                accessChecker(securityVerifier, user)
                    .check(docG, false)
                    .check(docA, true)
                    .check(docC, true)
                    .check(docAB, true)
                    .check(docAC, true)
                    .check(docCD, true)
                    .check(docABC, true)
                    .check(docACD, true)
                    .check(docABCD, true)
                    .check(docE, false)
                    .check(docABCDE, true)
                    .ensureFullyChecked();
            }
            {
                IPermissionOwner user = userG;
                accessChecker(securityVerifier, user)
                    .check(docG, true)
                    .check(docA, false)
                    .check(docC, false)
                    .check(docAB, false)
                    .check(docAC, false)
                    .check(docCD, false)
                    .check(docABC, false)
                    .check(docACD, false)
                    .check(docABCD, false)
                    .check(docE, true)
                    .check(docABCDE, false)
                    .ensureFullyChecked();
            }
            {
                IPermissionOwner user = userGAB;
                accessChecker(securityVerifier, user)
                    .check(docG, true)
                    .check(docA, true)
                    .check(docC, false)
                    .check(docAB, true)
                    .check(docAC, true)
                    .check(docCD, false)
                    .check(docABC, true)
                    .check(docACD, true)
                    .check(docABCD, true)
                    .check(docE, true)
                    .check(docABCDE, true)
                    .ensureFullyChecked();
            }
            {
                IPermissionOwner user = userGABCD;
                accessChecker(securityVerifier, user)
                    .check(docG, true)
                    .check(docA, true)
                    .check(docC, true)
                    .check(docAB, true)
                    .check(docAC, true)
                    .check(docCD, true)
                    .check(docABC, true)
                    .check(docACD, true)
                    .check(docABCD, true)
                    .check(docE, true)
                    .check(docABCDE, true)
                    .ensureFullyChecked();
            }
        }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAll_FileChain() {
        SecuredResourceManager masterControlledResourceManager = this.resourceManager(true, ProtectionMode.FitAll);
        ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

        {
            IPermissionOwner user = userG;
            accessChecker(securityVerifier, user)
                .multiCheck(true, docG, docG)
                .multiCheck(false, docG, docA)
                .multiCheck(false, docA, docAB)
                .multiCheck(false, docG, docAC)
                .multiCheck(true, docG, docE)
                .multiCheck(false, docG, docABCD);
        }
        {
            IPermissionOwner user = userGAB;
            accessChecker(securityVerifier, user)
                .multiCheck(true, docG, docA, docAB, docE)
                .multiCheck(false, docG, docA, docAB, docE, docC)
                .multiCheck(false, docG, docA, docAB, docE, docAC)
                .multiCheck(false, docG, docA, docAB, docE, docCD)
                .multiCheck(false, docG, docA, docAB, docE, docABC)
                .multiCheck(false, docG, docA, docAB, docE, docACD)
                .multiCheck(false, docG, docA, docAB, docE, docABCD)
                .multiCheck(false, docG, docA, docAB, docE, docABCDE);
        }
        {
            IPermissionOwner user = userGABCD;

            accessChecker(securityVerifier, user)
                .multiCheck(true, docG, docA, docC, docAB, docAC, docCD, docABC, docACD, docABCD, docE, docABCDE);
        }
    }

    class AccessChecker {
        private final ISecurityVerifier securityVerifier;
        private final IPermissionOwner user;
        private int checked = 0;

        public AccessChecker(ISecurityVerifier securityVerifier, IPermissionOwner user) {
            this.securityVerifier = securityVerifier;
            this.user = user;
        }

        public AccessChecker multiCheck(boolean allowed, IResourceInstance... instances) {
            if (allowed) {
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, instances));
            } else {
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, instances));
            }
            checked++;
            return this;
        }

        public AccessChecker check(IResourceInstance instance, boolean allowed) {
            if (allowed) {
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, instance));
            } else {
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, instance));
            }
            checked++;
            return this;
        }

        public void ensureFullyChecked() {
            Assert.assertEquals(docCount, checked);
        }
    }

    class QueryLikeChecker {
        private List<?> results;
        private int checked = 0;

        public QueryLikeChecker(List<?> results) {
            this.results = results;
        }

        public QueryLikeChecker check(IResourceInstance instance, boolean exist) {
            GuardedDocInstance docInstance = (GuardedDocInstance)instance;
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
            Assert.assertEquals(docCount, checked);
        }
    }

    @Test
    public void testPermissionForQueryLike_MasterControl_FitAll() {
        SecuredResourceManager masterControlledResourceManager = this.resourceManager(true, ProtectionMode.FitAll);
        ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

        for (IPermissionOwner user : new IPermissionOwner[]{userN, userAB, userABCD}) {
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, false)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, false)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userG;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGAB;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, true)
                .check(docC, false)
                .check(docAB, true)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGABCD;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, true)
                .check(docC, true)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, true)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, true)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_MasterControl_FitAny() {
        SecuredResourceManager masterControlledResourceManager = this.resourceManager(true, ProtectionMode.FitAny);
        ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

        for (IPermissionOwner user : new IPermissionOwner[]{userN, userAB, userABCD}) {
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, false)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, false)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userG;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGAB;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, true)
                .check(docC, false)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, false)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, true)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGABCD;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, true)
                .check(docC, true)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, true)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, true)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_SelfControl_FitAll() {
        SecuredResourceManager masterControlledResourceManager = this.resourceManager(false, ProtectionMode.FitAll);
        ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

        {
            IPermissionOwner user = userN;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, false)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, false)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userAB;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, false)
                .check(docA, true)
                .check(docC, false)
                .check(docAB, true)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, false)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userABCD;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, false)
                .check(docA, true)
                .check(docC, true)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, true)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, false)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userG;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGAB;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, true)
                .check(docC, false)
                .check(docAB, true)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGABCD;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, true)
                .check(docC, true)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, true)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, true)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_SelfControl_FitAny() {
        SecuredResourceManager masterControlledResourceManager = this.resourceManager(false, ProtectionMode.FitAny);
        ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

        {
            IPermissionOwner user = userN;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, false)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, false)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userAB;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, false)
                .check(docA, true)
                .check(docC, false)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, false)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, false)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userABCD;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, false)
                .check(docA, true)
                .check(docC, true)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, true)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, false)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userG;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, false)
                .check(docC, false)
                .check(docAB, false)
                .check(docAC, false)
                .check(docCD, false)
                .check(docABC, false)
                .check(docACD, false)
                .check(docABCD, false)
                .check(docE, true)
                .check(docABCDE, false)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGAB;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, true)
                .check(docC, false)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, false)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, true)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
        {
            IPermissionOwner user = userGABCD;
            AccessibleFitting accessibleFitting = securityVerifier.calcAccessibleFitting(user, normalAccess, TypesEnums.DOC);
            List<GuardedDoc> results = docRepo.query(accessibleFitting, masterControlledResourceManager.getResourceProtection(TypesEnums.DOC));
            queryLikeChecker(results)
                .check(docG, true)
                .check(docA, true)
                .check(docC, true)
                .check(docAB, true)
                .check(docAC, true)
                .check(docCD, true)
                .check(docABC, true)
                .check(docACD, true)
                .check(docABCD, true)
                .check(docE, true)
                .check(docABCDE, true)
                .ensureFullyChecked();
        }
    }


}
