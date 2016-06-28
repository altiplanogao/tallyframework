package com.taoswork.tallybook.business.datadomain.tallybusiness.work;

import com.taoswork.tallybook.business.datadomain.tallybusiness.subject.Employee;
import com.taoswork.tallycheck.datadomain.base.entity.PersistEntity;
import com.taoswork.tallycheck.datadomain.base.entity.PersistField;
import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.datadomain.base.presentation.typed.DateCellMode;
import com.taoswork.tallycheck.datadomain.base.presentation.typed.DateMode;
import com.taoswork.tallycheck.datadomain.base.presentation.typed.PresentationDate;
import com.taoswork.tallycheck.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity("feedback")
@PersistEntity(value = "feedback")
public class WorkFeedback extends AbstractDocument {

    @Reference(lazy = true)
    @PersistField(fieldType = FieldType.FOREIGN_KEY)
    protected WorkTicket workTicket;

    @Reference(lazy = true)
    @PersistField(fieldType = FieldType.FOREIGN_KEY)
    protected Employee writer;

    @PersistField(fieldType = FieldType.DATE, editable = false)
    @PresentationDate(cellMode = DateCellMode.DateAndTime, mode = DateMode.DateTime)
    protected Date date;

    @PersistField(fieldType = FieldType.HTML)
    protected String content;

    public WorkTicket getWorkTicket() {
        return workTicket;
    }

    public void setWorkTicket(WorkTicket workTicket) {
        this.workTicket = workTicket;
    }

    public Employee getWriter() {
        return writer;
    }

    public void setWriter(Employee writer) {
        this.writer = writer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
