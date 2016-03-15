package com.taoswork.tallybook.business.datadomain.tallybusiness.subject;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity("bu")
@PersistEntity(value = "bu")
public class Bu extends AbstractDocument {
    
    @PersistField(fieldType = FieldType.NAME, required = true)
    @PresentationField(order = 2)
    protected String name;

    @PersistField(fieldType = FieldType.HTML, length = Integer.MAX_VALUE - 1)
    @PresentationField(order = 4, visibility = Visibility.GRID_HIDE)
    protected String description;

    @CollectionField(mode = CollectionMode.Primitive)
    protected List<String> tags;

/*  Hide for prototype
    protected List<Employee> employees;
    protected List<Bp> businessPartners;
    protected List<Asset> assets;
    protected List<WorkPlan> workPlans;
    protected List<WorkSuite> workSuites;
    protected List<ModuleUsage> modules;
*/

    public String getName() {
        return name;
    }

    public Bu setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Bu setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
