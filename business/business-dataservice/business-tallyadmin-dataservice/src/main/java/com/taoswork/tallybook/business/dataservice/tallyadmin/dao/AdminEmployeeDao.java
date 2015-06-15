package com.taoswork.tallybook.business.dataservice.tallyadmin.dao;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.general.dataservice.support.entity.IDao;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public interface AdminEmployeeDao extends IDao{
    public static final String COMPONENT_NAME = PREFIX + "AdminEmployeeDao";

    AdminEmployee readAdminEmployeeByPersonId(Long id);

    AdminEmployee save(AdminEmployee employee);
}
