package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonCertificationImpl;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonFacetCertificationImpl;
import com.taoswork.tallybook.business.datadomain.tallyuser.impl.PersonImpl;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
public final class TallyUserDataDomain {
    public static Class[] persistableEntities() {
        return new Class[]{
                PersonImpl.class,
                PersonCertificationImpl.class,
                PersonFacetCertificationImpl.class
        };
    }

}
