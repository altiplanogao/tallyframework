package com.taoswork.tallybook.authority.solution.domain.permission;

import com.taoswork.tallybook.authority.solution.domain.ResourceAccess;
import com.taoswork.tallybook.authority.solution.domain.permission.validation.PermissionValidator;
import com.taoswork.tallybook.datadomain.base.entity.MapField;
import com.taoswork.tallybook.datadomain.base.entity.MapMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;

import java.util.HashMap;
import java.util.Map;

/**
 * KPermission is used to control resource (Resource is described by type ProtectedResource)
 * access from proper user.
 * User owns permission, and resource requires permission.
 * <p>
 * KPermission controls Resource access:
 * ResourceCriteria {@link SecuredResourceCase}
 * defines a kind of resource need to be secured.
 * <p>
 * {@link PermissionCase} defines the access permission for a specified kind of resource (specified by ResourceCriteria).
 * SUMMARY:
 * {@link PermissionCase} DEFINES ACCESS TO RESOURCE.
 * <p>
 * {@link Permission} contains children '{@link PermissionCase}'.
 * KPermission is designed as a tree, a permission node could be contained
 * in another permission node, thus the parent node has all the permission defined in the child node.
 * SUMMARY:
 * {@link Permission} DEFINES A BUNCH OF {@link PermissionCase}
 * {@link PermissionCase} DEFINES ACCESS TO RESOURCE.
 * <p>
 * {@link Role} contains a bunch of {@link Permission}.
 * SUMMARY:
 * {@link Role} DEFINES A BUNCH OF {@link PermissionCase}
 * {@link PermissionCase} DEFINES ACCESS TO RESOURCE.
 * <p>
 * IPermissionUser {@link com.taoswork.tallybook.general.authority.core.authority.user.IPermissionUser}
 * represents for a permission consumer, having values of {@link Permission} and {@link Role} assigned.
 * SUMMARY:
 * {@link com.taoswork.tallybook.general.authority.core.authority.user.IPermissionUser} DEFINES A BUNCH OF {@link PermissionCase}
 * {@link PermissionCase} DEFINES ACCESS TO RESOURCE.
 * <p>
 * <p>
 * <p>
 * USER OWNS PERMISSIONS {@link PermissionCase}
 * If a tallyuser has permission containing access to a type of object,
 * then it's ok for him to access such type of objects.
 * <p>
 * RESOURCE REQUIRES PERMISSION
 * If the resource requires multi-type permissions,
 * in order to access such resource, the tallyuser must have all these permissions.
 * <p>
 * Created by Gao Yuan on 2015/4/15.
 */
@PersistEntity(
        validators = {PermissionValidator.class}
)
@PresentationClass(groups = {
        @PresentationClass.Group(name = Permission.Presentation.Group.Authority, order = 2)
})
public class Permission {

    protected String resource;
    public static final String FN_RESOURCE = "resource";

    @PersistField(fieldType = FieldType.NAME, required = true, length = 100)
    @PresentationField(order = 2)
    protected String name;

    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(order = 3)
    protected String description;

    protected ResourceAccess access;

//    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
//    @OneToMany(
//            targetEntity = PermissionCase.class,
//            mappedBy = PermissionCase.OWN_M2O_PERM,
//            fetch = FetchType.LAZY)
//    protected List<PS> allEntries = new ArrayList<PS>();
//    public static final String OWN_M2M_ALL_ENTRIES = "allEntries";

    @MapField(mode = MapMode.Entity, keyFieldOnValue = "code")
    protected Map<String, PermissionCase> permissionCases;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResourceAccess getAccess() {
        return access;
    }

    public void setAccess(ResourceAccess access) {
        this.access = access;
    }

    public void clearPermissionCases(){
        if(permissionCases != null)
            permissionCases.clear();
        permissionCases = null;
    }

    public void addPermissionCase(PermissionCase _case){
        if(permissionCases == null){
            permissionCases = new HashMap<String, PermissionCase>();
        }
        permissionCases.put(_case.code, _case);
    }

    public PermissionCase getPermissionCase(String caseCode) {
        return permissionCases.get(caseCode);
    }

    public Map<String, PermissionCase> getPermissionCases() {
        return permissionCases;
    }

    public void setPermissionCases(Map<String, PermissionCase> cases) {
        permissionCases = cases;
    }

    public static class Presentation {
        public static class Tab {
        }

        public static class Group {
            public static final String General = "General";
            public static final String Authority = "Authority";
        }
    }

}
