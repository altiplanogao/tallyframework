package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.authority.solution.domain.user.GroupAuthority;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2016/2/29.
 */
@Entity("role")
public class Role extends GroupAuthority {
}
