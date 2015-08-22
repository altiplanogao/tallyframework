package com.taoswork.tallybook.general.authority.core.permission.wirte;

import com.taoswork.tallybook.general.authority.core.permission.IPermissionEntry;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public interface IPermissionEntryWrite extends IPermissionEntry {

    IPermissionEntryWrite merge(IPermissionEntry that);
}
