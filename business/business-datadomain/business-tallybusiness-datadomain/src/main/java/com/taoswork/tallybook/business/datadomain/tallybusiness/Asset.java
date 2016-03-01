package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/14.
 * <p>
 * Asset: owned by business partner, may have work plan on that.
 */
@Entity("asset")
@PersistEntity("asset")
public class Asset extends AbstractDocument {

    @PersistField(fieldType = FieldType.NAME, required = true)
    @PresentationField(order = 2)
    protected String name;

    @PresentationField(order = 4, visibility = Visibility.GRID_HIDE)
    protected String description;

    protected Privacy privacy;
//
//    @Reference
//    @CollectionField(mode = CollectionMode.Entity)
//    protected List<WorkPlan> workPlans;

    @Reference
    //inverse controlled fields:
    @PersistField(fieldType = FieldType.ADDITIONAL_FOREIGN_KEY)
    protected BusinessUnit owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<WorkPlan> getWorkPlans() {
//        return workPlans;
//    }
//
//    public void setWorkPlans(List<WorkPlan> workPlans) {
//        this.workPlans = workPlans;
//    }
//
    public BusinessUnit getOwner() {
        return owner;
    }

    public void setOwner(BusinessUnit owner) {
        this.owner = owner;
    }
}
