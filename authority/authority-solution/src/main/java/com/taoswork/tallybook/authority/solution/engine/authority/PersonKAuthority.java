package com.taoswork.tallybook.authority.solution.engine.authority;

import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.authorities.SimpleKAuthority;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
public class PersonKAuthority extends SimpleKAuthority implements IKAuthority {
    private final String tenantId;
    private final String userId;

    public PersonKAuthority(String tenantId, String userId) {
        this.tenantId = tenantId;
        this.userId = userId;
    }
}
