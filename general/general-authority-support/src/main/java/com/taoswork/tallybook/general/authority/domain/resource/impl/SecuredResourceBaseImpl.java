package com.taoswork.tallybook.general.authority.domain.resource.impl;

import com.taoswork.tallybook.dynamic.datadomain.converters.BooleanToStringConverter;
import com.taoswork.tallybook.general.authority.GeneralAuthoritySupportRoot;
import com.taoswork.tallybook.general.authority.domain.resource.ResourceProtectionMode;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.authority.domain.resource.converter.ProtectionModeToStringConverter;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationBoolean;
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
    @PersistField(fieldType = FieldType.ID)
    @PresentationField(group = "General", order = 1, visibility = Visibility.HIDDEN_ALL)
    protected Long id;

    @Column(name = "NAME", length = 100, nullable = false)
    @PersistField(fieldType = FieldType.NAME)
    @PresentationField(order = 1)
    protected String name;

    @Column(name = "DESCRIPTION")
    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(order = 2, visibility = Visibility.GRID_HIDE)
    protected String description;

    //A Data line without resourceEntity, means its a main source line, only its version column is useful
    @Column(name = "TYPE", nullable = false)
    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(order = 3)
    protected String resourceEntity;

    @Column(name = "CATEGORY")
    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(order = 4)
    protected String category;

    @Column(name = "PROT_MOD", nullable = false, length = 4
        ,columnDefinition = "VARCHAR(4) DEFAULT '" + ResourceProtectionMode.DEFAULT_CHAR + "'"
    )
    @PersistField(fieldType = FieldType.ENUMERATION)
    @PresentationField(order = 5)
    @PresentationEnum(enumeration = ResourceProtectionMode.class)
    @Convert(converter = ProtectionModeToStringConverter.class)
    protected ResourceProtectionMode protectionMode;

    @Column(name = "MASTER_CTRL", nullable = false, length = 2)
    @Convert(converter = BooleanToStringConverter.class)
    @PersistField(fieldType = FieldType.BOOLEAN)
    @PresentationField(order = 6)
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
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
    public void setMasterControlled(Boolean masterControlled) {
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

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int getVersion() {
        return version;
    }
}
