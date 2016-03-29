package com.taoswork.tallybook.business.dataservice.tallyuser.dao;

import com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification;
import com.taoswork.tallybook.dataservice.core.entity.IDao;

/**
 * Created by Gao Yuan on 2015/5/3.
 */
public interface PersonCertificationDao extends IDao {
    public static final String COMPONENT_NAME = PREFIX + "PersonCertificationDao";

    PersonCertification readPersonCertificationById(Long id);

    PersonCertification readPersonCertificationByPersonCode(String userCode);
}
