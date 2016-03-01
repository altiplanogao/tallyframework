package com.taoswork.tallybook.business.datadomain.tallybusiness;


import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity("workplan")
public class WorkPlan extends AbstractDocument {
    protected String name;
    protected String description;

    @Reference
    @PersistField(fieldType = FieldType.FOREIGN_KEY)
    protected BusinessUnit host;

    @Reference
    @PersistField(fieldType = FieldType.FOREIGN_KEY)
    protected Asset asset;

    protected String moduleType;
    /**
     * there should be serviceType defined in WorkSuite,
     * The WorkSuite can serve the WorkPlan if they have the same serviceType and moduleType.
     */
    protected String serviceType;

    @Reference(lazy = true)
    @CollectionField(mode = CollectionMode.Entity)
    protected List<WorkTicket> tickets;

    //an cached index?
    @Reference(lazy = true)
    @PersistField(fieldType = FieldType.FOREIGN_KEY)
    protected WorkTicket nextTicket;


}
