package com.taoswork.tallybook.business.datadomain.tallyuser.impl;

import com.taoswork.tallybook.business.datadomain.tallyuser.FacetType;
import com.taoswork.tallybook.business.datadomain.tallyuser.PersonFacetCertification;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/4/21.
 */

@Entity
@Table(name = "TB_PERSON_FACET_CERTIFICATION")
@NamedQueries({
        @NamedQuery(name = "PersonFacetCertification.ReadByPersonCode",
                query = "SELECT personCertification FROM com.taoswork.tallybook.business.datadomain.tallyuser.PersonFacetCertification personCertification" +
                        " WHERE personCertification.userCode = :userCode")
})
public class PersonFacetCertificationImpl
        implements PersonFacetCertification {
    @Id
    @Column(name = "ID")
    protected Long id;

    @Column(name = "FACET_TYPE", nullable = false)
    @PersistField(required = true)
    protected FacetType facetType;

    @Column(name = "FACET_ID", nullable = false)
    @PersistField(required = true)
    protected Long facetId;

    @Column(name = "USER_CODE", nullable = false)
    @PersistField(required = true)
    protected String userCode;

    @Column(name = "CHK_PWD", nullable = false)
    @PersistField(required = true)
    protected boolean checkPwd;

    @Column(name = "PWD", nullable = false)
    @PersistField(required = true)
    protected String password;

    @Column(name = "UPDATE_DATE", nullable = true)
    @PersistField(required = false)
    protected Long lastUpdateDate = 0L;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public FacetType getFacetType() {
        return facetType;
    }

    @Override
    public void setFacetType(FacetType facetType) {
        this.facetType = facetType;
    }

    @Override
    public Long getFacetId() {
        return facetId;
    }

    @Override
    public void setFacetId(Long facetId) {
        this.facetId = facetId;
    }

    @Override
    public String getUserCode() {
        return userCode;
    }

    @Override
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Override
    public boolean isCheckPwd() {
        return checkPwd;
    }

    @Override
    public void setCheckPwd(boolean checkPwd) {
        this.checkPwd = checkPwd;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Long getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public void setLastUpdateDate(Long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
