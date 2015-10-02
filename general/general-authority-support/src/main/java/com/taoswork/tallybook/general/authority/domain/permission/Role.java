package com.taoswork.tallybook.general.authority.domain.permission;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.util.Set;

/**
 * Created by Gao Yuan on 2015/4/19.
 */
public interface Role<P extends Permission>
    extends Persistable {

    Long getId();

    void setId(Long id);

    String getScreenName();

    void setScreenName(String screenName);

    String getInsideName();

    void setInsideName(String insideName);

    Set<P> getAllPermissions();

    void setAllPermissions(Set<P> allPermissions);

    Long getOrganizationId();

    void setOrganizationId(Long organizationId);
}