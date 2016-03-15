package com.taoswork.tallybook.business.datadomain.tallybusiness.work;

import com.taoswork.tallybook.business.datadomain.tallybusiness.module.path.SheetPath;
import com.taoswork.tallybook.business.datadomain.tallybusiness.work.time.Time;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Gao Yuan on 2016/3/14.
 */
@Embedded
public class WorkPlanUnit {
    public String module;

    public String sheet;

    protected Time time;

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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
