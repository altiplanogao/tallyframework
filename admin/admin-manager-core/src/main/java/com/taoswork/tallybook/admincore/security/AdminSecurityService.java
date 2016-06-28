package com.taoswork.tallybook.admincore.security;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallycheck.datadomain.tallyuser.Person;

/**
 * Created by Gao Yuan on 2015/5/14.
 */
public interface AdminSecurityService {
    public static final String COMPONENT_NAME="AdminSecurityService";

    Person getPersistentPerson();

    AdminEmployee getPersistentAdminEmployee();
}
