package com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails.impl;

import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.tallyuser.PersonService;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails.PersonDetails;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails.PersonDetailsService;
import com.taoswork.tallybook.dataservice.annotations.EntityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Gao Yuan on 2015/4/21.
 */
@EntityService(PersonDetailsService.COMPONENT_NAME)
@Service(PersonDetailsService.COMPONENT_NAME)
public class PersonDetailsServiceImpl implements PersonDetailsService {
//    private static final String ADMIN_USER_NAME_ON_EMPTY_DB = "admin";
//    private static final String ADMIN_PASSWORD_ON_EMPTY_DB = "admin";

    @Resource(name = PersonService.SERVICE_NAME)
    private PersonService personService;

    //
//    private Boolean userDbHasData = null;
    @Override
    public PersonDetails loadPersonByUsername(String username) throws UsernameNotFoundException {
//        if(null == userDbHasData){
//            if(personDao.isThereAnyData()){
//                userDbHasData = true;
//            } else {
//                PersonDetails manualUserDetail =
//                        new PersonDetails(-1L, "",  ADMIN_USER_NAME_ON_EMPTY_DB, ADMIN_PASSWORD_ON_EMPTY_DB,
//                                true, true, true, true, new ArrayList<GrantedAuthority>() );
//                return manualUserDetail;
//            }
//        }

        Person person = personService.readPersonByAnyIdentity(username);
        if (person == null || person.getId() == null) {
            return null;
        }
        PersonCertification userCert = personService.readPersonCertificationByUUID(person.getUuid());
        ;
        PersonDetails userDetails = new PersonDetails(person, userCert.getPassword(), new ArrayList<GrantedAuthority>());
        return userDetails;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadPersonByUsername(username);
    }

    @Override
    public PersonDetails getPersistentPersonDetails() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (null != ctx) {
            Authentication auth = ctx.getAuthentication();
            if (null != auth && !auth.getName().equals(ANONYMOUS_USER_NAME)) {
                PersonDetails userDetails = (PersonDetails) auth.getPrincipal();
                return userDetails;
            }
        }
        return null;
    }

}
