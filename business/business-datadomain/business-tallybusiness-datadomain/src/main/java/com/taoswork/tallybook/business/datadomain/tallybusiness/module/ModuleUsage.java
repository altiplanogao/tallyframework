package com.taoswork.tallybook.business.datadomain.tallybusiness.module;

import com.taoswork.tallybook.business.datadomain.tallybusiness.subject.Bu;
import com.taoswork.tallybook.business.datadomain.tallymanagement.ModuleEntry;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationExternalForeignKey;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity("moduleusage")
@PersistEntity("moduleusage")
public class ModuleUsage extends AbstractDocument {

    @PersistField(required = true, fieldType = FieldType.EXTERNAL_FOREIGN_KEY)
    @PresentationExternalForeignKey(targetType = ModuleEntry.class, dataField = "module")
    protected ObjectId moduleId;
    @Transient
    protected transient ModuleEntry module;

    @Reference
    protected Bu bu;

    protected boolean hide = false;

    protected Date availableFrom;
    protected Date availableTo;

    public ObjectId getModuleId() {
        return moduleId;
    }

    public void setModuleId(ObjectId moduleId) {
        this.moduleId = moduleId;
    }

    public ModuleEntry getModule() {
        return module;
    }

    public void setModule(ModuleEntry module) {
        this.module = module;
    }

    public Bu getBu() {
        return bu;
    }

    public void setBu(Bu bu) {
        this.bu = bu;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Date getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Date availableTo) {
        this.availableTo = availableTo;
    }
}
