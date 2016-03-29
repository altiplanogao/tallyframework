package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.datadomain.onmongo.PersistableDocument;

/**
 * Created by Gao Yuan on 2015/4/14.
 */
public interface PersonFacetCertification extends PersistableDocument {

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
