package com.taoswork.tallybook.authority.solution.domain.user;

import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Gao Yuan on 2015/4/19.
 */
@Entity
@PresentationClass()
public class GroupAuthority
        extends BaseAuthority {

    @PersistField(fieldType = FieldType.NAME, required = true)
    @PresentationField(order = 2)
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
