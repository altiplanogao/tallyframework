package com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails;

import com.taoswork.tallybook.business.datadomain.tallyuser.FacetType;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/21.
 */
public class PersonDetails extends User {

    private static class FacetId {
        public FacetType type;
        public Long id;

        FacetId(FacetType type, Long id) {
            this.type = type;
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FacetId facetId = (FacetId) o;

            if (type != facetId.type) return false;
            return !(id != null ? !id.equals(facetId.id) : facetId.id != null);

        }

        @Override
        public int hashCode() {
            int result = type != null ? type.hashCode() : 0;
            result = 31 * result + (id != null ? id.hashCode() : 0);
            return result;
        }
    }

    protected Person person;
    protected final Map<FacetId, FacetDetails> facetDetailsMap = new HashMap<FacetId, FacetDetails>();

    public PersonDetails(Person person, String password, Collection<? extends GrantedAuthority> authorities) {
        super(person.getName(), password,
                true, true, true, true,
                authorities);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void addFacetDetails(FacetDetails facetDetails) {
        FacetId fid = new FacetId(facetDetails.getFacetType(), facetDetails.getFacetId());
        facetDetailsMap.put(fid, facetDetails);
    }

    public <T extends FacetDetails> T getFacetDetails(FacetType type, Long id) {
        FacetId fid = new FacetId(type, id);
        FacetDetails details = facetDetailsMap.get(fid);
        return (T) details;
    }
}
