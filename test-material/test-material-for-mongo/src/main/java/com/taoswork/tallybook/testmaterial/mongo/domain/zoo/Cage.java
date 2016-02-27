package com.taoswork.tallybook.testmaterial.mongo.domain.zoo;

import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

@Entity
public class Cage extends AbstractDocument {

    private String sn;

    private Date createDate;

    public String getSn() {
        return sn;
    }

    public Cage setSn(String sn) {
        this.sn = sn;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Cage setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }
}
