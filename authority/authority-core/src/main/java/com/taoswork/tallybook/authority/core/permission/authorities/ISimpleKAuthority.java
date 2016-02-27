package com.taoswork.tallybook.authority.core.permission.authorities;

import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.IKPermission;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface ISimpleKAuthority extends IKAuthority {

    ISimpleKAuthority addPermission(IKPermission permissions);

    ISimpleKAuthority addPermissions(IKPermission... permissions);

    ISimpleKAuthority merge(ISimpleKAuthority that);

    ISimpleKAuthority clone();
}
