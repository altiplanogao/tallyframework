package com.taoswork.tallybook.authority.solution.mockup.domain.resource;

import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.KAccessibleScope;

import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/5.
 */
public interface ICoreAccessSensitiveRepo<T> {
    List<T> query(KAccessibleScope accessibleScope, IKProtection resourceProtection);
}
