package com.taoswork.tallybook.business.datadomain.tallyuser.impl;

import com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/4/21.
 */

@Entity
@Table(name = "TB_PERSON_CERTIFICATION")
@NamedQueries({
        @NamedQuery(name = "PersonCertification.ReadByPersonCode",
                query = "SELECT personCertification FROM com.taoswork.tallybook.business.datadomain.tallyuser.PersonCertification personCertification" +
                        " WHERE personCertification.userCode = :userCode")
})
public class PersonCertificationImpl
        implements PersonCertification {
    @Id
    @Column(name = "ID")
    protected Long id;


    @Column(name = "USER_CODE", nullable = false)
    @PersistField(required = true)
    protected String userCode;

    @Column(name = "PWD", nullable = false)
    @PersistField(required = true)
    protected String password;

    @Column(name = "UPDATE_DATE", nullable = true)
    protected Long lastUpdateDate = 0L;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public PersonCertification setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public PersonCertification setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getUserCode() {
        return userCode;
    }

    @Override
    public PersonCertification setUserCode(String userId) {
        this.userCode = userId;
        return this;
    }

    @Override
    public Long getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(Long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /*
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="admin_user_key_gen")
    @TableGenerator(name = "admin_user_key_gen",
            table="sequence_generator",
            pkColumnName="gen_name",
            valueColumnName="gen_value",
            pkColumnValue="AdminPersonCertificationImpl",
            allocationSize=1
    )
    @Column(name = "ID")
    protected Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public PersonCertification setId(Long id) {
        this.id = id;
        return this;
    }
 */

}
