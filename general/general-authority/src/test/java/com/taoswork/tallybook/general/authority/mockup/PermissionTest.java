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
import com.taoswork.tallybook.general.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.general.authority.mockup.resource.TypesEnums;
import com.taoswork.tallybook.general.authority.mockup.resource.domain.GuardedDoc;
import com.taoswork.tallybook.general.authority.mockup.resource.filters.doc.DocTagFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class PermissionTest {
    private static final String TAGA = "a";
    private static final String TAGB = "b";
    private static final String TAGC = "c";
    private static final Access normalAccess = Access.Read;

    private IResourceFilter docFilterA;
    private IResourceFilter docFilterB;

    private IPermissionEntry accessA;
    private IPermissionEntry accessB;

    @Before
    public void setup() {
        docFilterA = new DocTagFilter("a");
        docFilterB = new DocTagFilter("b");

        accessA = new PermissionEntry(docFilterA.getCode(), normalAccess);
        accessB = new PermissionEntry(docFilterB.getCode(), normalAccess);
    }

    @After
    public void teardown() {
        docFilterA = null;
        docFilterB = null;

        accessA = null;
        accessB = null;
    }

    private IResourceInstance docWith(boolean a, boolean b, boolean c) {
        GuardedDoc doc = new GuardedDoc("", null);
        if (a) doc.addTag(TAGA);
        if (b) doc.addTag(TAGB);
        if (c) doc.addTag(TAGC);
        return new GuardedDocInstance(doc);
    }

    private IPermissionOwner userWithPermission(boolean g, boolean a, boolean b) {
        IEntityPermission entityPermission = new EntityPermission(TypesEnums.DOC);
        entityPermission.addEntries(
            a ? accessA : null,
            b ? accessB : null);

        entityPermission.setMasterAccess(g ? normalAccess : Access.None);

        IPermissionOwner permissionOwner = new PermissionOwner().addEntityPermission(entityPermission);
        return permissionOwner;
    }

    private SecuredResourceManager resourceManager(boolean masterControlled,
                                                   ProtectionMode protectionMode) {
        SecuredResourceManager securedResourceManager = new SecuredResourceManager();
        IResourceProtection resourceProtection = new ResourceProtection(TypesEnums.DOC);
        resourceProtection.setMasterControlled(masterControlled);
        resourceProtection.setProtectionMode(protectionMode);
        resourceProtection.addFilters(docFilterA, docFilterB);

        securedResourceManager.registerResourceProtection(resourceProtection);
        return securedResourceManager;
    }

    @Test
    public void testPermissionForType() {
        SecuredResourceManager resourceManager = this.resourceManager(true, ProtectionMode.FitAll);
        IPermissionOwner userGab = this.userWithPermission(true, true, true);
        IPermissionOwner userG = this.userWithPermission(true, false, false);
        IPermissionOwner userA = this.userWithPermission(false, true, false);
        IPermissionOwner userN = this.userWithPermission(false, false, false);

        ISecurityVerifier securityVerifier = new SecurityVerifier(resourceManager);

        for (IPermissionOwner user : new IPermissionOwner[]{userA, userG, userGab}) {
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
        IResourceInstance docG = this.docWith(false, false, false);
        IResourceInstance docA = this.docWith(true, false, false);
        IResourceInstance docB = this.docWith(false, true, false);
        IResourceInstance docAB = this.docWith(true, true, false);
        IResourceInstance docABC = this.docWith(true, true, true);

        IPermissionOwner userN = this.userWithPermission(false, false, false);
        IPermissionOwner userG = this.userWithPermission(true, false, false);
        IPermissionOwner userGA = this.userWithPermission(true, true, false);
        IPermissionOwner userGAB = this.userWithPermission(true, true, true);
        IPermissionOwner userA = this.userWithPermission(false, true, false);
        IPermissionOwner userAB = this.userWithPermission(false, true, true);

        {
            SecuredResourceManager masterControlledResourceManager = this.resourceManager(true, ProtectionMode.FitAll);
            ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

            for (IPermissionOwner user : new IPermissionOwner[]{userN, userA, userAB}) {
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userG;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userGA;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userGAB;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));
            }
        }

    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAny() {
        IResourceInstance docG = this.docWith(false, false, false);
        IResourceInstance docA = this.docWith(true, false, false);
        IResourceInstance docB = this.docWith(false, true, false);
        IResourceInstance docAB = this.docWith(true, true, false);
        IResourceInstance docABC = this.docWith(true, true, true);

        IPermissionOwner userN = this.userWithPermission(false, false, false);
        IPermissionOwner userG = this.userWithPermission(true, false, false);
        IPermissionOwner userGA = this.userWithPermission(true, true, false);
        IPermissionOwner userGAB = this.userWithPermission(true, true, true);
        IPermissionOwner userA = this.userWithPermission(false, true, false);
        IPermissionOwner userAB = this.userWithPermission(false, true, true);

        {
            SecuredResourceManager masterControlledResourceManager = this.resourceManager(true, ProtectionMode.FitAny);
            ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

            {
                IPermissionOwner user = userN;
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userA;
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userAB;
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            for (IPermissionOwner user : new IPermissionOwner[]{userG, userGA, userGAB}) {
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));
            }
        }
    }

    @Test
    public void testPermissionForInstanceTag_SelfControl_FitAll() {
        IResourceInstance docG = this.docWith(false, false, false);
        IResourceInstance docA = this.docWith(true, false, false);
        IResourceInstance docB = this.docWith(false, true, false);
        IResourceInstance docAB = this.docWith(true, true, false);
        IResourceInstance docABC = this.docWith(true, true, true);

        IPermissionOwner userN = this.userWithPermission(false, false, false);
        IPermissionOwner userG = this.userWithPermission(true, false, false);
        IPermissionOwner userGA = this.userWithPermission(true, true, false);
        IPermissionOwner userGAB = this.userWithPermission(true, true, true);
        IPermissionOwner userA = this.userWithPermission(false, true, false);
        IPermissionOwner userAB = this.userWithPermission(false, true, true);

        {
            SecuredResourceManager masterControlledResourceManager = this.resourceManager(false, ProtectionMode.FitAll);
            ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

            {
                IPermissionOwner user = userN;
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userA;
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userAB;
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userG;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userGA;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userGAB;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));
            }
        }

    }

    @Test
    public void testPermissionForInstanceTag_SelfControl_FitAny() {
        IResourceInstance docG = this.docWith(false, false, false);
        IResourceInstance docA = this.docWith(true, false, false);
        IResourceInstance docB = this.docWith(false, true, false);
        IResourceInstance docAB = this.docWith(true, true, false);
        IResourceInstance docABC = this.docWith(true, true, true);

        IPermissionOwner userN = this.userWithPermission(false, false, false);
        IPermissionOwner userG = this.userWithPermission(true, false, false);
        IPermissionOwner userGA = this.userWithPermission(true, true, false);
        IPermissionOwner userGAB = this.userWithPermission(true, true, true);
        IPermissionOwner userA = this.userWithPermission(false, true, false);
        IPermissionOwner userAB = this.userWithPermission(false, true, true);

        {
            SecuredResourceManager masterControlledResourceManager = this.resourceManager(false, ProtectionMode.FitAny);
            ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

            {
                IPermissionOwner user = userN;
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userA;
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userAB;
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userG;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userGA;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));
            }
            {
                IPermissionOwner user = userGAB;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));
            }
        }
    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAll_FileChain() {
        IResourceInstance docG = this.docWith(false, false, false);
        IResourceInstance docA = this.docWith(true, false, false);
        IResourceInstance docB = this.docWith(false, true, false);
        IResourceInstance docAB = this.docWith(true, true, false);
        IResourceInstance docABC = this.docWith(true, true, true);

        IPermissionOwner userN = this.userWithPermission(false, false, false);
        IPermissionOwner userG = this.userWithPermission(true, false, false);
        IPermissionOwner userGA = this.userWithPermission(true, true, false);
        IPermissionOwner userGAB = this.userWithPermission(true, true, true);
        IPermissionOwner userA = this.userWithPermission(false, true, false);
        IPermissionOwner userAB = this.userWithPermission(false, true, true);

        {
            SecuredResourceManager masterControlledResourceManager = this.resourceManager(true, ProtectionMode.FitAll);
            ISecurityVerifier securityVerifier = new SecurityVerifier(masterControlledResourceManager);

            {
                IPermissionOwner user = userG;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));

                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG, docG));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG, docABC));
            }
            {
                IPermissionOwner user = userGA;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docABC));

                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docG, docABC));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA, docA));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA, docB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA, docAB));
                Assert.assertFalse(securityVerifier.canAccess(user, normalAccess, docA, docABC));
            }
            {
                IPermissionOwner user = userGAB;
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docABC));

                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG, docA));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA, docB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docB, docAB));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docAB, docABC));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docA, docB, docAB, docABC));
                Assert.assertTrue(securityVerifier.canAccess(user, normalAccess, docG, docABC));
            }
        }

    }


}
