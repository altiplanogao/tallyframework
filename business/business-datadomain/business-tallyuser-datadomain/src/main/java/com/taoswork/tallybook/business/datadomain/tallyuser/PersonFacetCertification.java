package com.taoswork.tallybook.business.datadomain.tallyuser;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/4/14.
 */
public interface PersonFacetCertification extends Serializable{

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
