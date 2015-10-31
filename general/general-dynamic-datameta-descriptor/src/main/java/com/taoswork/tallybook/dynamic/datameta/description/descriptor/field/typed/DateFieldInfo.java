package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.BasicFieldInfoBase;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateModel;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class DateFieldInfo extends BasicFieldInfoBase {
    private final static String DATE = "date";
    private final static String DATETIME = "datetime";
    private final static String DATATIME_WITH_TIMEZONE = "datetimez";

    private final String model;

    public DateFieldInfo(String name, String friendlyName, DateModel dateModel) {
        super(name, friendlyName);
        switch (dateModel) {
            case Date:
                model = DATE;
                break;
            case DateTime:
                model = DATETIME;
                break;
            case DateTimeWithZone:
                model = DATATIME_WITH_TIMEZONE;
                break;
            default:
                throw new IllegalStateException("Un expected Date model");
        }
    }

    public String getModel() {
        return model;
    }
}
