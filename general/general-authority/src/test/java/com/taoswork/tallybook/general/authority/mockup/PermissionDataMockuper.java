package com.taoswork.tallybook.general.authority.mockup;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermissionSpecial;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.permission.authorities.ISimplePermissionAuthority;
import com.taoswork.tallybook.general.authority.core.permission.authorities.SimplePermissionAuthority;
import com.taoswork.tallybook.general.authority.core.permission.impl.EntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.impl.EntityPermissionSpecial;
import com.taoswork.tallybook.general.authority.core.resource.IResourceFilter;
import com.taoswork.tallybook.general.authority.core.resource.IResourceProtection;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtection;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtectionManager;
import com.taoswork.tallybook.general.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.general.authority.mockup.resource.domain.GuardedDoc;
import com.taoswork.tallybook.general.authority.mockup.resource.filters.doc.DocTagFilter;
import com.taoswork.tallybook.general.authority.mockup.resource.repo.DocRepo;

/**
 * Created by Gao Yuan on 2015/8/20.
 */
public class PermissionDataMockuper {
    public static final int docCount = 11;
    public final String TAGA = "a";
    public final String TAGB = "b";
    public final String TAGC = "c";
    public final String TAGD = "d";
    public final String TAGE = "e";
    //Use a fixed access to make easy
    public final Access normalAccess;
    public final DocRepo docRepo = new DocRepo();
    public final IResourceFilter docFilterA;
    public final IResourceFilter docFilterB;
    public final IResourceFilter docFilterC;
    public final IResourceFilter docFilterD;
    public final IEntityPermissionSpecial accessA;
    public final IEntityPermissionSpecial accessB;
    public final IEntityPermissionSpecial accessC;
    public final IEntityPermissionSpecial accessD;
    public final GuardedDocInstance docG;
    public final GuardedDocInstance docA;
    public final GuardedDocInstance docC;
    public final GuardedDocInstance docAB;
    public final GuardedDocInstance docAC;
    public final GuardedDocInstance docCD;
    public final GuardedDocInstance docABC;
    public final GuardedDocInstance docACD;
    public final GuardedDocInstance docABCD;
    public final GuardedDocInstance docE;
    public final GuardedDocInstance docABCDE;
    public final IPermissionAuthority authN;
    public final IPermissionAuthority authG;
    public final IPermissionAuthority authAB;
    public final IPermissionAuthority authGAB;
    public final IPermissionAuthority authABCD;
    public final IPermissionAuthority authGABCD;
    public final String resourceEntity;

    public PermissionDataMockuper(String resourceEntity, Access normalAccess) {
        this.resourceEntity = resourceEntity;
        this.normalAccess = normalAccess;

        docFilterA = new DocTagFilter(TAGA);
        docFilterB = new DocTagFilter(TAGB);
        docFilterC = new DocTagFilter(TAGC);
        docFilterD = new DocTagFilter(TAGD);

        accessA = new EntityPermissionSpecial(docFilterA.getCode(), normalAccess);
        accessB = new EntityPermissionSpecial(docFilterB.getCode(), normalAccess);
        accessC = new EntityPermissionSpecial(docFilterC.getCode(), normalAccess);
        accessD = new EntityPermissionSpecial(docFilterD.getCode(), normalAccess);

        docG = docWith(false, false, false, false, false);
        docA = docWith(true, false, false, false, false);
        docC = docWith(false, false, true, false, false);
        docAB = docWith(true, true, false, false, false);
        docAC = docWith(true, false, true, false, false);
        docCD = docWith(false, false, true, true, false);
        docABC = docWith(true, true, true, false, false);
        docACD = docWith(true, false, true, true, false);
        docABCD = docWith(true, true, true, true, false);
        docE = docWith(false, false, false, false, true);
        docABCDE = docWith(true, true, true, true, true);

        authN = authorityWith(false, false, false, false, false);
        authG = authorityWith(true, false, false, false, false);
        authAB = authorityWith(false, true, true, false, false);
        authGAB = authorityWith(true, true, true, false, false);
        authABCD = authorityWith(false, true, true, true, true);
        authGABCD = authorityWith(true, true, true, true, true);
    }

    public GuardedDocInstance docWith(boolean a, boolean b, boolean c, boolean d, boolean e) {
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

    public ISimplePermissionAuthority authorityWith(boolean g, boolean a, boolean b, boolean c, boolean d) {
        IEntityPermission entityPermission = new EntityPermission(resourceEntity);
        entityPermission.addSpecials(
            a ? accessA : null,
            b ? accessB : null,
            c ? accessC : null,
            d ? accessD : null);

        entityPermission.setMasterAccess(g ? normalAccess : Access.None);

        ISimplePermissionAuthority authority = new SimplePermissionAuthority().addEntityPermission(entityPermission);
        return authority;
    }

    public ResourceProtectionManager resourceManager(boolean masterControlled,
                                                  ProtectionMode protectionMode) {
        ResourceProtectionManager securedResourceManager = new ResourceProtectionManager();
        IResourceProtection resourceProtection = new ResourceProtection(resourceEntity);
        resourceProtection.setMasterControlled(masterControlled);
        resourceProtection.setProtectionMode(protectionMode);
        resourceProtection.addFilters(docFilterA, docFilterB, docFilterC, docFilterD);

        securedResourceManager.registerResourceProtection(resourceProtection);
        return securedResourceManager;
    }

}
