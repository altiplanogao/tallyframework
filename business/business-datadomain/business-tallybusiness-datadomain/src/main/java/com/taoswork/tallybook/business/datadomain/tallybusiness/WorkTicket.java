package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity("workticket")
public class WorkTicket extends AbstractDocument {
    @Reference(lazy = true)
    @PersistField(fieldType = FieldType.FOREIGN_KEY)
    protected Employee worker;

    @Reference(lazy = true)
    @PersistField(fieldType = FieldType.FOREIGN_KEY)
    protected Employee supervisior;

    @Reference(lazy = true)
    @PersistField(fieldType = FieldType.FOREIGN_KEY)
    protected WorkPlan workPlan;

    protected Date startWorkingTime;
    protected Date endWorkingTime;

    @Reference
    @CollectionField(mode = CollectionMode.Entity)
    protected List<WorkFeedback> feedbacks;

    protected WorkTicketStatus status;
}
