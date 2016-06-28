package com.taoswork.tallybook.business.dataservice.tallyadmin.genesis;

import com.taoswork.tallycheck.authority.solution.domain.ProtectionSpace;
import com.taoswork.tallycheck.authority.solution.domain.resource.Protection;
import com.taoswork.tallycheck.authority.solution.domain.user.GroupAuthority;
import com.taoswork.tallycheck.authority.solution.domain.user.UserAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class Genesis {
    public static final String GENESIS_PROTECTION_SPACE = "Genesis";

    public static final String GENESIS_ALIAS1 = "genesis1";
    public static final String GENESIS_ALIAS2 = "genesis1.2";

    public static final String GENESIS_TENANT_ID = "root.tenant";
    public static final String GENESIS_USER_ID = "god";

    public static final Class[] GENESIS_RESOURCES = new Class[]{
            ProtectionSpace.class,
            Protection.class,
            UserAuthority.class,
            GroupAuthority.class
    };

    private ProtectionSpace makeProtectionSpace(){
        ProtectionSpace protectionSpace = new ProtectionSpace();
        protectionSpace.setSpaceName(GENESIS_PROTECTION_SPACE);

        protectionSpace.addAliases(Genesis.class.getName(),
                new String[]{GENESIS_ALIAS1, GENESIS_ALIAS2});

        return protectionSpace;
    }

    private Protection[] makeSecuredResource(){
        List<Protection> srs = new ArrayList<Protection>();
        for(Class resClz : GENESIS_RESOURCES){
            srs.add(makeSecuredResource(resClz));
        }
        return srs.toArray(new Protection[]{});
    }

//    private UserAuthority makeRootPerson(){
//        UserAuthority pp = new UserAuthority();
//        pp.setProtectionSpace(GENESIS_PROTECTION_SPACE);
//        pp.setNamespace(GENESIS_TENANT_ID);
//        pp.setOwnerId(GENESIS_USER_ID);
//
//        //ProtectionSpace.class
//        {
//            Permission rperm = new Permission();
//            rperm.setResource(ProtectionSpace.class.getName());
//            rperm.setAccess(ResourceAccess.createByAccess(Access.Read));
//        }
//        //Protection.class
//        {
//            Permission rperm = new Permission();
//            rperm.setResource(Protection.class.getName());
//            rperm.setAccess(ResourceAccess.createByAccess(Access.Read));
//        }
//        //SKAuthority.class
//        {
//            Permission rperm = new Permission();
//            rperm.setResource(UserAuthority.class.getName());
//            rperm.setAccess(ResourceAccess.createByAccess(Access.Read));
//        }
//        //GroupAuthority.class
//        {
//            Permission rperm = new Permission();
//            rperm.setResource(GroupAuthority.class.getName());
//            rperm.setAccess(ResourceAccess.createByAccess(Access.Read));
//        }
//        return pp;
//    }

    private Protection makeSecuredResource(Class resourceClz){
        Protection sr = new Protection();
        sr.setProtectionSpace(GENESIS_PROTECTION_SPACE);
        sr.setNamespace(GENESIS_TENANT_ID);
        sr.setResource(resourceClz.getName());

        return sr;
    }
//
//    public void makeGenesisSetting(Datastore datastore){
//        datastore.save(makeProtectionSpace());
//        datastore.save(makeSecuredResource());
//        datastore.save(makeRootPerson());
//    }
}
