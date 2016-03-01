package com.taoswork.tallybook.business.datadomain.tallymanagement.impl;

import com.taoswork.tallybook.business.datadomain.tallymanagement.ModuleRegistry;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2015/6/6.
 */
@Entity("modulereg")
@PersistEntity("modulereg")
public class ModuleRegistryImpl extends AbstractDocument implements ModuleRegistry {

    @PersistField(fieldType = FieldType.NAME, required = true)
    @PresentationField(group = "General", order = 2)
    protected String name;
}
