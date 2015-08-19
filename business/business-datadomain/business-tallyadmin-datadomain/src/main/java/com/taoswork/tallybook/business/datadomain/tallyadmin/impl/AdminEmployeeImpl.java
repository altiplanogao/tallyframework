package com.taoswork.tallybook.business.datadomain.tallyadmin.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyadmin.TallyAdminDataDomain;
import com.taoswork.tallybook.general.authority.core.authentication.user.AccountStatus;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@Entity
@Table(name = "TB_ADMIN_EMPLOYEE")
@NamedQueries(
        @NamedQuery(name = "AdminEmployee.ReadEmployeeByPersonId",
                query = "SELECT employee FROM com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee employee" +
                        " WHERE employee.personId = :personId")
)
public class AdminEmployeeImpl implements AdminEmployee {

    protected static final String ID_GENERATOR_NAME = "AdminEmployeeImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
            name = ID_GENERATOR_NAME,
            table =  TallyAdminDataDomain.ID_GENERATOR_TABLE_NAME,
            initialValue = 1
    )
    @Column(name = "ID")
    protected Long id;

    @Column(name = "PERSON_ID", nullable = false, unique = true)
    protected Long personId;

    @Column(name = "TITLE")
    protected String title;

    @Embedded
    protected AccountStatus status;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getPersonId() {
        return personId;
    }

    @Override
    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public AccountStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
