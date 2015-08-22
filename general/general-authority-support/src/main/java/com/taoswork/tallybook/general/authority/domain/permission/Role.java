package com.taoswork.tallybook.general.authority.domain.permission;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/4/19.
 */
public interface Role extends Serializable {

    Long getId();

    void setId(Long id);

    String getScreenName();

    void setScreenName(String screenName);

    String getInsideName();

    void setInsideName(String insideName);

    Set<Permission> getAllPermissions();

    void setAllPermissions(Set<Permission> allPermissions);

    Long getOrganizationId();

    void setOrganizationId(Long organizationId);
}