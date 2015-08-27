package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessPartner;
import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessUnit;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class BusinessPartnerImpl implements BusinessPartner {
    protected Long id;
    protected BusinessUnit host;
    protected BusinessUnit guest;

    protected boolean blacklist;
    protected boolean whitelist;
}
