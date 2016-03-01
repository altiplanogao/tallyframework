package com.taoswork.tallybook.authority.solution;

import com.taoswork.tallybook.authority.solution.domain.ProtectionSpace;
import com.taoswork.tallybook.authority.solution.domain.resource.Protection;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class AuthoritySolutionDomain {
    public static Class[] domainEntities(){
        return new Class<?>[]{
                ProtectionSpace.class,  //feature
                Protection.class,  //feature + tenant
//                UserAuthority.class,//feature + tenant + userid
//                GroupAuthority.class,
        };
    }
}
