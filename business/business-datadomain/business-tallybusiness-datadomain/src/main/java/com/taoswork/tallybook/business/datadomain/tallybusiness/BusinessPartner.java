package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.business.datadomain.tallybusiness.validation.BusinessPartnerValidator;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationEnum;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by Gao Yuan on 2015/4/14.
 * <p>
 * Business partner: a company, an organization, a division, a branch
 */
@Entity("bp")
@PersistEntity(value = "bp",
        validators = {BusinessPartnerValidator.class}
)
public class BusinessPartner extends AbstractDocument {

    @Property("alias")
    @PersistField(fieldType = FieldType.NAME, required = true)
    @PresentationField(order = 2)
    protected String alias;

    @Property("desc")
    @PersistField(fieldType = FieldType.HTML, length = Integer.MAX_VALUE - 1)
    @PresentationField(order = 4, visibility = Visibility.GRID_HIDE)
    protected String description;

    @PersistField(fieldType = FieldType.ENUMERATION)
    @PresentationField(group = "General", order = 3)
    @PresentationEnum(enumeration = BusinessPartnerType.class)
    protected BusinessPartnerType type;

    @Reference(lazy = true)
    @PersistField(required = true, fieldType = FieldType.FOREIGN_KEY)
    protected BusinessUnit host;

    @Reference(lazy = true)
    @PersistField(required = true, fieldType = FieldType.FOREIGN_KEY)
    @PresentationField()
    protected BusinessUnit guest;

    public String getAlias() {
        return alias;
    }

    public BusinessPartner setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BusinessPartner setDescription(String description) {
        this.description = description;
        return this;
    }

    public BusinessPartnerType getType() {
        return type;
    }

    public BusinessPartner setType(BusinessPartnerType type) {
        this.type = type;
        return this;
    }

    public BusinessUnit getHost() {
        return host;
    }

    public BusinessPartner setHost(BusinessUnit host) {
        this.host = host;
        return this;
    }

    public BusinessUnit getGuest() {
        return guest;
    }

    public BusinessPartner setGuest(BusinessUnit guest) {
        this.guest = guest;
        return this;
    }
}
