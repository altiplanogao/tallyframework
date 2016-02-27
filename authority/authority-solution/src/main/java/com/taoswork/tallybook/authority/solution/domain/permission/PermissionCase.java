package com.taoswork.tallybook.authority.solution.domain.permission;

import com.taoswork.tallybook.authority.solution.domain.ResourceAccess;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Solution A:
 * +-----------------------+  1     .*    +-----------------------+  1     .*    +-----------------------+
 * |   KPermission          |  --------->  | KPermission 4 Entity   |   ---------> |   KPermission Entry    |
 * | ( KPermission Package) |              |                       |              |                       |
 * | owned by user or group|              |                       |              |                       |
 * | SimpleKAuthority.java       | KPermission.java |              | EntityPermissionEntry.java  |
 * |-----------------------|              |-----------------------|              |-----------------------|
 * | id                    |              | id                    |              | id                    |
 * |                       |              | entity name (resource)         | filter (ProtectionCase.java)
 * |                       |              | master access         |              | access                |
 * |                       |              |                       |              |                       |
 * |                       |              |                       |              |                       |
 * |                       |              |                       |              |                       |
 * |                       |              |                       |              |                       |
 * |                       |              |                       |              |                       |
 * |                       |              |                       |              |                       |
 * | version               |              |                       |              |                       |
 * +-----------------------+              +-----------------------+              +-----------------------+
 * <p>
 * Solution B:
 * +-----------------------+  1     .*    +-----------------------+
 * |   KPermission          |  --------->  |   KPermission Entry    |
 * | ( KPermission Package) |              |                       |
 * | owned by user or group|              |                       |
 * | SimpleKAuthority.java       | EntityPermissionEntry.java  |
 * |-----------------------|              |-----------------------|
 * | id                    |              | id                    |
 * | name                  |              | entity name (resource)
 * | owner (optional)      |              | is main or filtered   |
 * |                       |              | filter (ProtectionCase.java)
 * |                       |              | access (or master access)
 * |                       |              |                       |
 * |                       |              |                       |
 * |  version              |              |                       |
 * +-----------------------+              +-----------------------+
 */
@Embedded
@PresentationClass(
        groups = {
                @PresentationClass.Group(name = PermissionCase.Presentation.Group.General, order = 1),
                @PresentationClass.Group(name = PermissionCase.Presentation.Group.Access, order = 2)}
)
public final class PermissionCase {

    protected String code;

    @PersistField(fieldType = FieldType.NAME)
    @PresentationField(order = 2)
    protected String name;

    @PersistField(required = true)
    @PresentationField(order = 6, group = "Access")
    protected ResourceAccess access = new ResourceAccess();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceAccess getAccess() {
        return access;
    }

    public void setAccess(ResourceAccess access) {
        this.access = access;
    }

    public static class Presentation {
        public static class Tab {
        }

        public static class Group {
            public static final String General = "General";
            public static final String Access = "Access";
        }
    }
}
