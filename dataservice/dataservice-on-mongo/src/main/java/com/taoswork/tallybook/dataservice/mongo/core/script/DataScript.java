package com.taoswork.tallybook.dataservice.mongo.core.script;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/3/29.
 */
@Entity("datascript")
public class DataScript {
    @Indexed(unique = true)
    protected String task;

    protected String file;

    protected boolean imported;

    public DataScript() {
    }

    public static List<DataScript> parseDataScripts(String importFiles){
        String[] scriptUnits = importFiles.split(",");
        List<DataScript> dss = new ArrayList<DataScript>();
        for (String scriptUnit : scriptUnits){
            String[] ss = scriptUnit.split(":");
            dss.add(new DataScript(ss[0], ss[1], false));
        }
        return dss;
    }

    public DataScript(String task, String file, boolean imported) {
        this.task = task;
        this.file = file;
        this.imported = imported;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }
}
