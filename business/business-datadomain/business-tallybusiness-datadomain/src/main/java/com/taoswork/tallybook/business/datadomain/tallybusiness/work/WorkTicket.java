package com.taoswork.tallybook.business.datadomain.tallybusiness.work;

import com.taoswork.tallybook.business.datadomain.tallybusiness.subject.Employee;
import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
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
@PersistEntity("workticket")
public class WorkTicket extends AbstractDocument {
    public String module;

    public String sheet;

    public String sheetFillId;

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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getSheetFillId() {
        return sheetFillId;
    }

    public void setSheetFillId(String sheetFillId) {
        this.sheetFillId = sheetFillId;
    }

    public Employee getWorker() {
        return worker;
    }

    public void setWorker(Employee worker) {
        this.worker = worker;
    }

    public Employee getSupervisior() {
        return supervisior;
    }

    public void setSupervisior(Employee supervisior) {
        this.supervisior = supervisior;
    }

    public WorkPlan getWorkPlan() {
        return workPlan;
    }

    public void setWorkPlan(WorkPlan workPlan) {
        this.workPlan = workPlan;
    }

    public Date getStartWorkingTime() {
        return startWorkingTime;
    }

    public void setStartWorkingTime(Date startWorkingTime) {
        this.startWorkingTime = startWorkingTime;
    }

    public Date getEndWorkingTime() {
        return endWorkingTime;
    }

    public void setEndWorkingTime(Date endWorkingTime) {
        this.endWorkingTime = endWorkingTime;
    }

    public List<WorkFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<WorkFeedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public WorkTicketStatus getStatus() {
        return status;
    }

    public void setStatus(WorkTicketStatus status) {
        this.status = status;
    }
}
