package com.taoswork.tallybook.business.datadomain.tallybusiness.work.time;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Gao Yuan on 2016/3/14.
 */
@Embedded
public class Time {
    public TimeModel model;
    public String parameter;

    public TimeModel getModel() {
        return model;
    }

    public void setModel(TimeModel model) {
        this.model = model;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
