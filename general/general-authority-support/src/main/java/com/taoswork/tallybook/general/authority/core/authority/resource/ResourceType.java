package com.taoswork.tallybook.general.authority.core.authority.resource;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */

public interface ResourceType  extends Serializable{

    Long getId();

    void setId(Long id);

    String getType();

    void setType(String type);

    String getFriendlyName();

    void setFriendlyName(String friendlyName);

    List<ResourceCriteria> getCriterias();

    void setCriterias(List<ResourceCriteria> criterias);
}
