package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@PersistFriendly(nameOverride = "bu")
public interface BusinessUnit extends Persistable {
    Long getId();

    BusinessUnit setId(Long id);

    String getName();

    BusinessUnit setName(String name);

    String getDescription();

    BusinessUnit setDescription(String description);
}
