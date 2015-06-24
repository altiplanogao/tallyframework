package com.taoswork.tallybook.general.dataservice.management.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by Gao Yuan on 2015/6/14.
 */
public class TallyResource extends ResourceSupport {
    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    @JsonProperty
    private Object data;
}
