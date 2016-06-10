package com.taoswork.tallybook.business.dataservice.tallyadmin.service.tallyadmin;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.dataservice.core.entity.IService;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
public interface AdminEmployeeService extends IService {
    public static final String SERVICE_NAME = PREFIX + "AdminEmployeeService";

    AdminEmployee readAdminEmployeeByPersonId(String personId);

    AdminEmployee saveAdminEmployee(AdminEmployee employee);
}
