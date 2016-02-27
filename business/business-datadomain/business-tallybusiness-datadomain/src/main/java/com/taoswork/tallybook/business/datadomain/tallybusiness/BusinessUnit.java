package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@PersistEntity(nameOverride = "bu")
public interface BusinessUnit extends Persistable {
    Long getId();

    BusinessUnit setId(Long id);

    String getName();

    BusinessUnit setName(String name);

    String getDescription();

    BusinessUnit setDescription(String description);

    List<String> getTags();

    void setTags(List<String> tags);
}
