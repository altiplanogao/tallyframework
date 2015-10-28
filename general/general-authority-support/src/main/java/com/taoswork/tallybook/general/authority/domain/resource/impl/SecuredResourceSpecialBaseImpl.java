package com.taoswork.tallybook.general.authority.domain.resource.impl;

import com.taoswork.tallybook.general.authority.GeneralAuthoritySupportRoot;
import com.taoswork.tallybook.general.authority.domain.resource.FilterType;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationEnum;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@PresentationClass(instantiable =false)
//@Table(name = "AUTH_SECURED_RESOURCE_FILTER")
public abstract class SecuredResourceSpecialBaseImpl<R extends SecuredResource>
    implements SecuredResourceSpecial<R> {

    protected static final String ID_GENERATOR_NAME = "SecuredResourceSpecialBaseImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = GeneralAuthoritySupportRoot.ID_GENERATOR_TABLE_NAME,
        initialValue = 0)
    @Column(name = "ID")
    @PresentationField(group = "General", order = 1, fieldType = FieldType.ID, visibility = Visibility.HIDDEN_ALL)
    public Long id;

    //IResourceFilter.getCode()
    @Column(name = "NAME", nullable = false)
    @PresentationField(order = 2, fieldType = FieldType.NAME)
    public String name;

    @Column(name = "FILTER", nullable = false, length = 10
        ,columnDefinition = "VARCHAR(10) DEFAULT '" + FilterType.DEFAULT_VAL + "'"
    )
    @PresentationField(order = 6, fieldType = FieldType.ENUMERATION)
    @PresentationEnum(enumeration = FilterType.class)
    public FilterType filter = FilterType.None;

    @Column(name = "FILTER_PARAM")
    @PresentationField(order = 7)
    public String filterParameter;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SecuredResourceSpecial setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public FilterType getFilter() {
        return filter;
    }

    @Override
    public SecuredResourceSpecial setFilter(FilterType filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public String getFilterParameter() {
        return filterParameter;
    }

    @Override
    public SecuredResourceSpecial setFilterParameter(String filterParameter) {
        this.filterParameter = filterParameter;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + "[" + getSecuredResource().getResourceEntity());
        if(null != filter){
            sb.append(", " + filter + "(" + filterParameter + ")");
        }
        sb.append("]");
        return sb.toString();
    }
}
