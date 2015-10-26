package com.taoswork.tallybook.general.authority.domain.resource.impl;

import com.taoswork.tallybook.dynamic.datadomain.converters.BooleanToStringConverter;
import com.taoswork.tallybook.general.authority.GeneralAuthoritySupportRoot;
import com.taoswork.tallybook.general.authority.domain.resource.ResourceProtectionMode;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.authority.domain.resource.converter.ProtectionModeToStringConverter;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationBoolean;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationEnum;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@PresentationClass(instantiable =false)
public abstract class SecuredResourceBaseImpl<RF extends SecuredResourceSpecial>
        implements SecuredResource<RF> {

    protected static final String ID_GENERATOR_NAME = "SecuredResourceBaseImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = GeneralAuthoritySupportRoot.ID_GENERATOR_TABLE_NAME,
        initialValue = 0)
    @Column(name = "ID")
    @PresentationField(group = "General", order = 1, fieldType = FieldType.ID, visibility = Visibility.HIDDEN_ALL)
    protected Long id;

    @Column(name = "FRIENDLY_NAME", length = 100, nullable = false)
    @PresentationField(order = 1, fieldType = FieldType.NAME)
    protected String friendlyName;

    //A Data line without resourceEntity, means its a main source line, only its version column is useful
    @Column(name = "TYPE", nullable = false)
    @PresentationField(order = 2, fieldType = FieldType.STRING)
    protected String resourceEntity;

    @Column(name = "CATEGORY")
    @PresentationField(order = 3, fieldType = FieldType.STRING)
    protected String category;

    @Column(name = "PROT_MOD", nullable = false, length = 4
        ,columnDefinition = "VARCHAR(4) DEFAULT '" + ResourceProtectionMode.DEFAULT_CHAR + "'"
    )
    @PresentationField(order = 4, fieldType = FieldType.ENUMERATION)
    @PresentationEnum(enumeration = ResourceProtectionMode.class)
    @Convert(converter = ProtectionModeToStringConverter.class)
    protected ResourceProtectionMode protectionMode;

    @Column(name = "MASTER_CTRL", nullable = false, length = 2,
        columnDefinition = "VARCHAR(2) DEFAULT 'Y'")
    @Convert(converter = BooleanToStringConverter.class)
    @PresentationField(order = 5, fieldType = FieldType.BOOLEAN)
    @PresentationBoolean(model = BooleanModel.YesNo)
    protected Boolean masterControlled = Boolean.TRUE;

    @Version
    @Column(name="OPTLOCK")
    protected Integer version = 0;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @Override
    public String getResourceEntity() {
        return resourceEntity;
    }

    @Override
    public void setResourceEntity(String resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean isMasterControlled() {
        return masterControlled;
    }

    @Override
    public void setMasterControlled(boolean masterControlled) {
        this.masterControlled = masterControlled;
    }

    @Override
    public ResourceProtectionMode getProtectionMode() {
        return protectionMode;
    }

    @Override
    public void setProtectionMode(ResourceProtectionMode protectionMode) {
        this.protectionMode = protectionMode;
    }


    @Override
    public int getVersion() {
        return version;
    }
}
