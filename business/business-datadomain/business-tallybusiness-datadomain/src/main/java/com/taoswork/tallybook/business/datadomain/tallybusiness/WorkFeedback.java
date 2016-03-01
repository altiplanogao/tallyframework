package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity("feedback")
public class WorkFeedback extends AbstractDocument {

    @Reference(lazy = true)
    @PersistField(fieldType = FieldType.FOREIGN_KEY)
    protected WorkTicket workTicket;

    @Reference(lazy = true)
    @PersistField(fieldType = FieldType.FOREIGN_KEY)
    protected Employee writer;

    protected Date date;

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
