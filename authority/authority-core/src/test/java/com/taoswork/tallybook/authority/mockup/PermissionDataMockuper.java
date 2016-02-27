package com.taoswork.tallybook.authority.mockup;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.IKPermissionCase;
import com.taoswork.tallybook.authority.core.permission.authorities.ISimpleKAuthority;
import com.taoswork.tallybook.authority.core.permission.authorities.SimpleKAuthority;
import com.taoswork.tallybook.authority.core.permission.impl.KPermission;
import com.taoswork.tallybook.authority.core.permission.impl.KPermissionCase;
import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCase;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.impl.KProtection;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;
import com.taoswork.tallybook.authority.mockup.resource.CrudeKProtectionCenter;
import com.taoswork.tallybook.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.authority.mockup.resource.cases.doc.DocTagProtectionCase;
import com.taoswork.tallybook.authority.mockup.resource.domain.GuardedDoc;
import com.taoswork.tallybook.authority.mockup.resource.repo.DocRepo;

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
    public final IKProtectionCase docCaseA;
    public final IKProtectionCase docCaseB;
    public final IKProtectionCase docCaseC;
    public final IKProtectionCase docCaseD;
    public final IKPermissionCase accessA;
    public final IKPermissionCase accessB;
    public final IKPermissionCase accessC;
    public final IKPermissionCase accessD;
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
    public final IKAuthority authN;
    public final IKAuthority authG;
    public final IKAuthority authAB;
    public final IKAuthority authGAB;
    public final IKAuthority authABCD;
    public final IKAuthority authGABCD;
    public final String resource;

    public PermissionDataMockuper(String resource, Access normalAccess) {
        this.resource = resource;
        this.normalAccess = normalAccess;

        docCaseA = new DocTagProtectionCase(TAGA);
        docCaseB = new DocTagProtectionCase(TAGB);
        docCaseC = new DocTagProtectionCase(TAGC);
        docCaseD = new DocTagProtectionCase(TAGD);

        accessA = new KPermissionCase(docCaseA.getCode(), normalAccess);
        accessB = new KPermissionCase(docCaseB.getCode(), normalAccess);
        accessC = new KPermissionCase(docCaseC.getCode(), normalAccess);
        accessD = new KPermissionCase(docCaseD.getCode(), normalAccess);

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

    private GuardedDocInstance docWith(boolean a, boolean b, boolean c, boolean d, boolean e) {
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

    public ISimpleKAuthority authorityWith(boolean g, boolean a, boolean b, boolean c, boolean d) {
        KPermission entityPermission = new KPermission(resource);
        entityPermission.addCases(
                a ? accessA : null,
                b ? accessB : null,
                c ? accessC : null,
                d ? accessD : null);

        entityPermission.setMasterAccess(g ? normalAccess : Access.None);

        ISimpleKAuthority authority = new SimpleKAuthority().addPermission(entityPermission);
        return authority;
    }

    public IKProtectionCenter resourceProtectionCenter(IKProtectionMapping mapping, boolean masterControlled,
                                                        ProtectionMode protectionMode) {
        CrudeKProtectionCenter securedResourceManager = new CrudeKProtectionCenter(mapping);
        IKProtection resourceProtection = new KProtection(resource);
        resourceProtection.setMasterControlled(masterControlled);
        resourceProtection.setProtectionMode(protectionMode);
        resourceProtection.addCases(docCaseA, docCaseB, docCaseC, docCaseD);

        securedResourceManager.registerProtection(resourceProtection);
        return securedResourceManager;
    }

}
