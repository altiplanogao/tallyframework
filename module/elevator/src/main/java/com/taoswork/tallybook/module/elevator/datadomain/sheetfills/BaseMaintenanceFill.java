package com.taoswork.tallybook.module.elevator.datadomain.sheetfills;

import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import com.taoswork.tallybook.module.fill.SheetFill;

/**
 * Created by Gao Yuan on 2016/3/10.
 */
public abstract class BaseMaintenanceFill extends AbstractDocument
        implements SheetFill {
    private String ticketId;

    public boolean telephoneOk;
    public boolean lightOk;

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public boolean isTelephoneOk() {
        return telephoneOk;
    }

    public void setTelephoneOk(boolean telephoneOk) {
        this.telephoneOk = telephoneOk;
    }

    public boolean isLightOk() {
        return lightOk;
    }

    public void setLightOk(boolean lightOk) {
        this.lightOk = lightOk;
    }

    @Override
    public String getTicketId() {
        return ticketId;
    }
}
