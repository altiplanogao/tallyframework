package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.business.datadomain.tallybusiness.validation.BusinessPartnerValidator;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/4/14.
 * <p>
 * Business partner: a company, an organization, a division, a branch
 */
@PersistEntity(nameOverride = "bp",
        validators = {BusinessPartnerValidator.class}
)
public interface BusinessPartner extends Persistable {
    Long getId();

    BusinessPartner setId(Long id);

    String getAlias();

    BusinessPartner setAlias(String alias);

    String getDescription();

    BusinessPartner setDescription(String description);

    BusinessPartnerType getType();

    BusinessPartner setType(BusinessPartnerType type);

    BusinessUnit getHost();

    BusinessPartner setHost(BusinessUnit host);

    BusinessUnit getGuest();

    BusinessPartner setGuest(BusinessUnit guest);


    //Last update date
    //Last edit employee
}
