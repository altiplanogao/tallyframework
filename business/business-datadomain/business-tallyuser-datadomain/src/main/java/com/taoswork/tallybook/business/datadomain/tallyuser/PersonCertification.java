package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/4/14.
 */
public interface PersonCertification extends Persistable {
    String getPassword();

    PersonCertification setPassword(String password);

    String getUserCode();

    PersonCertification setUserCode(String userId);

    Long getId();

    PersonCertification setId(Long id);

    Long getLastUpdateDate();

    void setLastUpdateDate(Long lastUpdateDate);
}
