package com.taoswork.tallybook.authority.solution.engine;

import com.taoswork.tallybook.authority.core.permission.authorities.SimpleKAuthority;

/**
 * Created by Gao Yuan on 2016/2/28.
 */
public class SKAuthority extends SimpleKAuthority {
    private final String namespace;
    private final String owner;

    public SKAuthority(String namespace, String owner) {
        this.namespace = namespace;
        this.owner = owner;
    }
}
