package com.taoswork.tallybook.testframework.domain.business;

import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.testframework.domain.business.dataprotect.CompanyValidator;

/**
 * Created by Gao Yuan on 2015/9/21.
 */
@PersistFriendly(validators = {CompanyValidator.class})
public interface ICompany extends Persistable {
}
