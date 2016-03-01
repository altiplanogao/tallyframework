package com.taoswork.tallybook.authority.solution.domain.resource;

import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationEnum;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Embedded;

import java.util.UUID;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
@Embedded
@PresentationClass()
//@Table(name = "AUTH_SECURED_RESOURCE_FILTER")
public final class ProtectionCase {

    //auto generated, unique
    @PersistField(fieldType = FieldType.CODE)
    @PresentationField(visibility = Visibility.HIDDEN_ALL)
    private String uuid;

    @PersistField(fieldType = FieldType.NAME, required = true)
    @PresentationField(order = 2)
    public String name;

    @PersistField(fieldType = FieldType.HTML, required = true, length = 1024)
    @PresentationField(order = 3)
    public String description;

    @PersistField(fieldType = FieldType.ENUMERATION, required = true, length = 10)
    @PresentationField(order = 6)
    @PresentationEnum(enumeration = FilterType.class)
    public FilterType filter = FilterType.None;

    @PresentationField(order = 7)
    public String filterParameter;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void autoUuid(){
        if(StringUtils.isBlank(uuid)){
            uuid = UUID.randomUUID().toString();
        }
    }

    public String getName() {
        return name;
    }

    public ProtectionCase setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FilterType getFilter() {
        return filter;
    }

    public ProtectionCase setFilter(FilterType filter) {
        this.filter = filter;
        return this;
    }

    public String getFilterParameter() {
        return filterParameter;
    }

    public ProtectionCase setFilterParameter(String filterParameter) {
        this.filterParameter = filterParameter;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        if (null != filter) {
            sb.append(": [ " + filter + "(" + filterParameter + ")]");
        }
        return sb.toString();
    }

}
