package com.taoswork.tallybook.dynamic.dataservice.server.request;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class PersistencePackageRequest {
    protected String ceilingEntityClassname;

    public String getCeilingEntityClassname() {
        return ceilingEntityClassname;
    }

    public void setCeilingEntityClassname(String ceilingEntityClassname) {
        this.ceilingEntityClassname = ceilingEntityClassname;
    }
}
