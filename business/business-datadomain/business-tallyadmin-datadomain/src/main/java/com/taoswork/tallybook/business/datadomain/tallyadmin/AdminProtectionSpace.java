package com.taoswork.tallybook.business.datadomain.tallyadmin;

import com.taoswork.tallybook.authority.solution.domain.ProtectionSpace;
import com.taoswork.tallybook.authority.solution.domain.resource.Protection;

/**
 * Created by Gao Yuan on 2016/3/1.
 */
public class AdminProtectionSpace extends ProtectionSpace {
    public static final String COMMON_SPACE_NAME = "admin-protection-space";

    public AdminProtectionSpace() {
        this.setSpaceName(COMMON_SPACE_NAME);
    }
}