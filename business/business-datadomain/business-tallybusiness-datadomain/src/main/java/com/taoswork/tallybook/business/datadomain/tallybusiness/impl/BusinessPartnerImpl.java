package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessPartner;
import com.taoswork.tallybook.business.datadomain.tallybusiness.Organization;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class BusinessPartnerImpl implements BusinessPartner {
    protected Long id;
    protected Organization host;
    protected Organization guest;

    protected boolean blacklist;
    protected boolean whitelist;
}
