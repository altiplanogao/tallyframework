package com.taoswork.tallybook.dynamic.datameta.testdata.clazzes;

import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

/**
 * Created by Gao Yuan on 2015/9/21.
 */
@PersistFriendly(validators = {CompanyValidator.class})
public interface ICompany {
}
