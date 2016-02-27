package com.taoswork.tallybook.authority.core.permission.wirte;

import com.taoswork.tallybook.authority.core.permission.IKPermission;
import com.taoswork.tallybook.authority.core.permission.IKPermissionCase;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public interface IKPermissionW extends IKPermission {

    IKPermissionW merge(IKPermission that);

    /**
     * Add an IKPermissionCase, in another words: add new case access method
     *
     * @return
     */
    IKPermission addCase(IKPermissionCase _case);

    /**
     * Add an IKPermissionCase, in another words: add new case access method
     *
     * @return
     */
    IKPermission addCases(IKPermissionCase... cases);
}
