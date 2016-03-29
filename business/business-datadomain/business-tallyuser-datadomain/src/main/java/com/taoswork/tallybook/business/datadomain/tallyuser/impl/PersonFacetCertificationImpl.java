package com.taoswork.tallybook.business.datadomain.tallyuser.impl;

import com.taoswork.tallybook.business.datadomain.tallyuser.FacetType;
import com.taoswork.tallybook.business.datadomain.tallyuser.PersonFacetCertification;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;


/**
 * Created by Gao Yuan on 2015/4/21.
 */

@Entity("pfcert")
@PersistEntity("pfcert")
//
//@NamedQueries({
//        @NamedQuery(name = "PersonFacetCertification.ReadByPersonCode",
//                query = "SELECT personCertification FROM com.taoswork.tallybook.business.datadomain.tallyuser.PersonFacetCertification personCertification" +
//                        " WHERE personCertification.userCode = :userCode")
//})
public class PersonFacetCertificationImpl
        extends AbstractDocument
        implements PersonFacetCertification {

    @PersistField(required = true)
    protected FacetType facetType;

    @PersistField(required = true)
    protected Long facetId;

    @PersistField(required = true)
    protected String userCode;

    @PersistField(required = true)
    protected boolean checkPwd;

    @PersistField(required = true)
    protected String password;

    @PersistField(required = false)
    protected Long lastUpdateDate = 0L;

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
