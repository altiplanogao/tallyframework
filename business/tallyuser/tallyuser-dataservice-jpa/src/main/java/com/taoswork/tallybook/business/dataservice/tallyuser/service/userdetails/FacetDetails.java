package com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails;

import com.taoswork.tallybook.business.datadomain.tallyuser.AccountStatus;
import com.taoswork.tallybook.business.datadomain.tallyuser.FacetType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/5/14.
 */
public abstract class FacetDetails extends User {
    protected final PersonDetails personDetails;
    protected final FacetType facetType;
    protected final Long facetId;

    public FacetDetails(PersonDetails personDetails,
                        FacetType facetType, Long facetId,
                        String facetName, String password, AccountStatus accountStatus, Collection<? extends GrantedAuthority> authorities) {
        super(facetName, password,
                accountStatus.isEnabled(),
                accountStatus.checkNowIfExpired(),
                true, !accountStatus.isLocked(),
                authorities);
        this.personDetails = personDetails;
        this.facetType = facetType;
        this.facetId = facetId;
    }

    public PersonDetails getPersonDetails() {
        return personDetails;
    }

    public FacetType getFacetType() {
        return facetType;
    }

    public Long getFacetId() {
        return facetId;
    }
}
