package com.taoswork.tallybook.business.datadomain.tallybusiness.subject;

import com.taoswork.tallybook.business.datadomain.tallybusiness.validation.BpValidator;
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
        validators = {BpValidator.class}
)
public class Bp extends AbstractDocument {

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
    @PresentationEnum(enumeration = BpType.class)
    protected BpType type;

    @Reference(lazy = true)
    @PersistField(required = true, fieldType = FieldType.FOREIGN_KEY)
    protected Bu host;

    @Reference(lazy = true)
    @PersistField(required = true, fieldType = FieldType.FOREIGN_KEY)
    @PresentationField()
    protected Bu guest;

    public String getAlias() {
        return alias;
    }

    public Bp setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Bp setDescription(String description) {
        this.description = description;
        return this;
    }

    public BpType getType() {
        return type;
    }

    public Bp setType(BpType type) {
        this.type = type;
        return this;
    }

    public Bu getHost() {
        return host;
    }

    public Bp setHost(Bu host) {
        this.host = host;
        return this;
    }

    public Bu getGuest() {
        return guest;
    }

    public Bp setGuest(Bu guest) {
        this.guest = guest;
        return this;
    }
}
