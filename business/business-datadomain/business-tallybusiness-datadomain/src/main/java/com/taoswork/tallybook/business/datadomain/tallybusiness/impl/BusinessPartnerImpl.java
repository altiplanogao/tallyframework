package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.*;
import com.taoswork.tallybook.business.datadomain.tallybusiness.convert.BusinessPartnerTypeToStringConverter;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationEnum;

import javax.persistence.*;

@Entity
@Table(name = "TB_BP")
public class BusinessPartnerImpl implements BusinessPartner {

    protected static final String ID_GENERATOR_NAME = "BusinessPartnerImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = TallyBusinessDataDomain.ID_GENERATOR_TABLE_NAME,
        initialValue = 1)
    @Column(name = "ID")
    @PresentationField(order = 1, fieldType = FieldType.ID, visibility = Visibility.HIDDEN_ALL)
    protected Long id;

    @Column(name = "ALIAS", nullable = false)
    @PresentationField(order = 2, fieldType = FieldType.NAME)
    protected String alias;

    @Column(name = "DESCRIP", length = Integer.MAX_VALUE - 1)
    @Lob
    @PresentationField(order = 4, fieldType = FieldType.HTML, visibility = Visibility.GRID_HIDE)
    protected String description;

    @Column(name = "BP_TYP", nullable = false, length = 1
        ,columnDefinition = "VARCHAR(1) DEFAULT '" + BusinessPartnerType.DEFAULT_CHAR + "'"
    )
    @PresentationField(group = "General", order = 3, fieldType = FieldType.ENUMERATION)
    @PresentationEnum(enumeration = BusinessPartnerType.class)
    @Convert(converter = BusinessPartnerTypeToStringConverter.class)
    protected BusinessPartnerType type;

    @FieldRelation(RelationType.OneWay_ManyToOne)
    @ManyToOne(targetEntity = BusinessUnitImpl.class)
    @JoinColumn(name = "host_id",  nullable = false, updatable = false)
    @PresentationField(required = true, fieldType = FieldType.FOREIGN_KEY)
    protected BusinessUnit host;

    @FieldRelation(RelationType.OneWay_ManyToOne)
    @ManyToOne(targetEntity = BusinessUnitImpl.class)
    @JoinColumn(name = "guest_id",  nullable = false, updatable = true)
    @PresentationField(required = true, fieldType = FieldType.FOREIGN_KEY)
    protected BusinessUnit guest;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public BusinessPartner setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public BusinessPartner setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public BusinessPartner setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public BusinessPartnerType getType() {
        return type;
    }

    @Override
    public BusinessPartner setType(BusinessPartnerType type) {
        this.type = type;
        return this;
    }

    @Override
    public BusinessUnit getHost() {
        return host;
    }

    @Override
    public BusinessPartner setHost(BusinessUnit host) {
        this.host = host;
        return this;
    }

    @Override
    public BusinessUnit getGuest() {
        return guest;
    }

    @Override
    public BusinessPartner setGuest(BusinessUnit guest) {
        this.guest = guest;
        return this;
    }
}
