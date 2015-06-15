package com.taoswork.tallybook.business.datadomain.tallyadmin;

import com.taoswork.tallybook.general.authority.domain.authentication.user.AccountStatus;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public interface AdminEmployee extends Serializable {
    Long getId();

    void setId(Long id);

    Long getPersonId();

    void setPersonId(Long personId);

    String getTitle();

    void setTitle(String title);

    AccountStatus getStatus();

    void setStatus(AccountStatus status);
}
