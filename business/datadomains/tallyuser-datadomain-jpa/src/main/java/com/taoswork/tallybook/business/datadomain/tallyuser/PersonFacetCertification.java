package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/4/14.
 */
public interface PersonFacetCertification extends Persistable {

    Long getId();

    void setId(Long id);

    FacetType getFacetType();

    void setFacetType(FacetType facetType);

    Long getFacetId();

    void setFacetId(Long facetId);

    String getUserCode();

    void setUserCode(String userCode);

    boolean isCheckPwd();

    void setCheckPwd(boolean checkPwd);

    String getPassword();

    void setPassword(String password);

    Long getLastUpdateDate();

    void setLastUpdateDate(Long lastUpdateDate);
}
