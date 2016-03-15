package com.taoswork.tallybook.authority.solution.mockup;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.authority.solution.domain.ProtectionSpace;
import com.taoswork.tallybook.authority.solution.domain.ResourceAccess;
import com.taoswork.tallybook.authority.solution.domain.permission.Permission;
import com.taoswork.tallybook.authority.solution.domain.permission.PermissionCase;
import com.taoswork.tallybook.authority.solution.domain.resource.DProtectionMode;
import com.taoswork.tallybook.authority.solution.domain.resource.FilterType;
import com.taoswork.tallybook.authority.solution.domain.resource.Protection;
import com.taoswork.tallybook.authority.solution.domain.resource.ProtectionCase;
import com.taoswork.tallybook.authority.solution.domain.resource.protection.ResourceNameFieldGate;
import com.taoswork.tallybook.authority.solution.domain.user.BaseAuthority;
import com.taoswork.tallybook.authority.solution.domain.user.GroupAuthority;
import com.taoswork.tallybook.authority.solution.domain.user.UserAuthority;
import com.taoswork.tallybook.authority.solution.mockup.domain.auth.TGroupAuthority;
import com.taoswork.tallybook.authority.solution.mockup.domain.auth.TUserAuthority;
import com.taoswork.tallybook.authority.solution.mockup.domain.resource.XFile;
import com.taoswork.tallybook.authority.solution.mockup.service.AuthSolutionDataService;
import com.taoswork.tallybook.authority.solution.mockup.service.EasyEntityServiceAccess;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.mongo.core.entityservice.MongoEntityService;
import com.taoswork.tallybook.dataservice.service.IEntityService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class PermissionMockuper {
    public static final String PROTECTION_SPACE = "test";

    public static final String CASE_A_NAME = "A";
    public static final String CASE_B_NAME = "B";
    public static final String CASE_C_NAME = "C";
    public static final String CASE_D_NAME = "D";
    public static final String[] CASE_NAMES = new String[]{CASE_A_NAME, CASE_B_NAME, CASE_C_NAME, CASE_D_NAME};

    public static final String CASE_A_TAG = "a";
    public static final String CASE_B_TAG = "b";
    public static final String CASE_C_TAG = "c";
    public static final String CASE_D_TAG = "d";
    public static final String CASE_E_TAG = "e";

    public static final String CASE_A_PARA = "a";
    public static final String CASE_B_PARA = "b";
    public static final String CASE_C_PARA = "c";
    public static final String CASE_D_PARA = "d";
    public static final String[] CASE_PARAS = new String[]{CASE_A_PARA, CASE_B_PARA, CASE_C_PARA, CASE_D_PARA};

    public static final int CASE_COUNT = 4;

    private final AuthSolutionDataService dataService;
    private final MongoEntityService entityService;
    private final EasyEntityServiceAccess easyEntityAccess;
    public final Access normalAccess;

    public PermissionMockuper(AuthSolutionDataService dataService, Access normalAccess) {
        this.dataService = dataService;
        this.entityService = dataService.getService(IEntityService.COMPONENT_NAME);
        this.easyEntityAccess = new EasyEntityServiceAccess(entityService);
        this.normalAccess = normalAccess;
    }

    public void makeProtectionSpace() {
        ProtectionSpace ps = new ProtectionSpace();
        ps.setSpaceName(PROTECTION_SPACE);
        easyEntityAccess.create(ProtectionSpace.class, ps);
    }

    public void makeSecuredResource(String tenant, Class resource,
                                    boolean masterControlled,
                                    ProtectionMode pm, boolean addCases) {
        Protection sr = new Protection();
        sr.setProtectionSpace(PROTECTION_SPACE);
        sr.setNamespace(tenant);
        sr.setResource(resource.getName());
        sr.setName(resource.getSimpleName());
        sr.setProtectionMode(DProtectionMode.convert(pm));
        sr.setMasterControlled(masterControlled);

        if(addCases) {
            for (int i = 0; i < CASE_COUNT; ++i) {
                String caseName = CASE_NAMES[i];
                String casePara = CASE_PARAS[i];
                ProtectionCase _case = new ProtectionCase();
                _case.autoUuid();
                _case.setName(caseName);
                _case.setFilter(FilterType.Classified);
                _case.setFilterParameter(casePara);
                sr.addCase(_case);
            }
        }
        easyEntityAccess.create(Protection.class, sr);
    }

    public Protection getResource(String tanantId, Class resource) {
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.addFilterCriteria(Protection.FN_PROTECTION_SPACE, PROTECTION_SPACE)
                .addFilterCriteria(Protection.FN_NAMESPACE, tanantId)
                .addFilterCriteria(Protection.FN_RESOURCE_ENTITY, ResourceNameFieldGate.unifiedResourceName(resource.getName()));
        Protection sr = easyEntityAccess.queryOne(Protection.class, cto);
        return sr;
    }

    public <T extends UserAuthority> T getUserAuthority(String tenant, String userId){
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.addFilterCriteria(UserAuthority.FN_PROTECTION_SPACE, PROTECTION_SPACE)
                .addFilterCriteria(UserAuthority.FN_NAMESPACE, tenant)
                .addFilterCriteria(UserAuthority.FN_OWNER_ID, userId);
        BaseAuthority pp = easyEntityAccess.queryOne(UserAuthority.class, cto);
        return (T) pp;
    }

    public <T extends GroupAuthority> T getGroupAuthority(String tenant, String groupId){
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.addFilterCriteria(UserAuthority.FN_PROTECTION_SPACE, PROTECTION_SPACE)
                .addFilterCriteria(UserAuthority.FN_NAMESPACE, tenant)
                .addFilterCriteria(UserAuthority.FN_OWNER_ID, groupId);
        BaseAuthority pp = easyEntityAccess.queryOne(GroupAuthority.class, cto);
        return (T) pp;
    }

    public void makePerson(String tenant, String userId, Class resource,
                           boolean g, boolean a, boolean b, boolean c, boolean d) {
        UserAuthority ua = new TUserAuthority();
        makeAuthorizable(tenant, userId, resource, UserAuthority.class, ua, g, a, b, c, d);
    }

    public void makeGroup(String tenant, String userId, Class resource,
                           boolean g, boolean a, boolean b, boolean c, boolean d) {
        GroupAuthority ga = new TGroupAuthority();
        ga.setName(userId);
        makeAuthorizable(tenant, userId, resource, GroupAuthority.class, ga, g, a, b, c, d);
    }

    public <T extends BaseAuthority> void makeAuthorizable(String tenant, String ownerId, Class resource,
                                                           Class<T> authorizableClz, T newInstance,
                           boolean g, boolean a, boolean b, boolean c, boolean d) {
        String resourceName = Protection.unifiedResourceName(resource.getName());
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.addFilterCriteria(UserAuthority.FN_PROTECTION_SPACE, PROTECTION_SPACE)
                .addFilterCriteria(UserAuthority.FN_NAMESPACE, tenant)
                .addFilterCriteria(UserAuthority.FN_OWNER_ID, ownerId);
        BaseAuthority pp = easyEntityAccess.queryOne(authorizableClz, cto);
        boolean isNew = false;
        if (pp == null) {
            pp = newInstance;
            pp.setProtectionSpace(PROTECTION_SPACE);
            pp.setNamespace(tenant);
            pp.setOwnerId(ownerId);
            isNew = true;
        }

        Permission rp = pp.getPermission(resourceName);
        if (rp == null) {
            rp = new Permission();
            rp.setResource(resourceName);
            rp.setName(resource.getSimpleName());
            pp.addPermission(rp);
        }
        if (g)
            rp.setAccess(ResourceAccess.createByAccess(normalAccess));
        rp.clearPermissionCases();
        Protection protection = this.getResource(tenant, resource);
        Map<String, ProtectionCase> resourceCases = protection.getCases();
        if (resourceCases == null) {
            resourceCases = new HashMap<String, ProtectionCase>();
        }
        if (resourceCases != null) {
            for (ProtectionCase resCase : resourceCases.values()) {
                PermissionCase permissionCase = new PermissionCase();
                permissionCase.setCode(resCase.getUuid());
                String caseName = resCase.getName();
                if (CASE_A_NAME.equals(caseName) && a) {
                    permissionCase.setAccess(ResourceAccess.createByAccess(normalAccess));
                } else if (CASE_B_NAME.equals(caseName) && b) {
                    permissionCase.setAccess(ResourceAccess.createByAccess(normalAccess));
                } else if (CASE_C_NAME.equals(caseName) && c) {
                    permissionCase.setAccess(ResourceAccess.createByAccess(normalAccess));
                } else if (CASE_D_NAME.equals(caseName) && d) {
                    permissionCase.setAccess(ResourceAccess.createByAccess(normalAccess));
                }
                rp.addPermissionCase(permissionCase);
            }
        }

        if (isNew) {
            easyEntityAccess.create(authorizableClz, (T) pp);
        } else {
            easyEntityAccess.update(authorizableClz, (T) pp);
        }
    }

    public <T extends XFile> void makeInstanceWithTag(String title, Class<T> fileType, boolean a, boolean b, boolean c, boolean d, boolean e) {
        T instance = null;
        try {
            instance = fileType.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        instance.setTitle(title);
        if (a) instance.addClassification(CASE_A_TAG);
        if (b) instance.addClassification(CASE_B_TAG);
        if (c) instance.addClassification(CASE_C_TAG);
        if (d) instance.addClassification(CASE_D_TAG);
        if (e) instance.addClassification(CASE_E_TAG);
        easyEntityAccess.create(fileType, instance);
    }

    public <T extends XFile> T fetchInstance(Class<T> fileType, String fileTitle) {
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.addFilterCriteria("title", fileTitle);
        return easyEntityAccess.queryOne(fileType, cto);
    }

}
