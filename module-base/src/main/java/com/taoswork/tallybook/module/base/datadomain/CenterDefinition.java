package com.taoswork.tallybook.module.base.datadomain;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.CappedAt;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2016/3/14.
 */

@Entity(value = "center", cap = @CappedAt(count = 1L))
@PersistEntity("center")
public class CenterDefinition extends AbstractDocument {
    protected String serviceUrl;
    protected String publicKey;

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

//    @Override
//    public String serviceUrl() {
//        return serviceUrl;
//    }
//
//    @Override
//    public String publicKey() {
//        return publicKey;
//    }
}
