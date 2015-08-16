package com.taoswork.tallybook.business.datadomain.tallymanagement.impl;

import com.taoswork.tallybook.business.datadomain.tallymanagement.ModuleRegistry;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Gao Yuan on 2015/6/6.
 */
@Entity
@Table(name = "TB_MODULE_REGISTRY")
public class ModuleRegistryImpl implements ModuleRegistry {
    @Id
    @Column(name = "ID")
    @PresentationField(group = "General", order = 1, fieldType = FieldType.ID, visibility = Visibility.HIDDEN_ALL)
    protected Long id;

    @Column(name = "NAME", nullable = false)
    @PresentationField(group = "General", order = 2, fieldType = FieldType.NAME)
    protected String name;
}
