package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.BasicFieldInfoBase;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateCellModel;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateModel;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class DateFieldInfo extends BasicFieldInfoBase {
    private final static String DATE = "date";
    private final static String DATETIME = "datetime";
    private final static String DATATIME_WITH_TIMEZONE = "datetimez";

    private final static String CELL_DATE = "date";
    private final static String CELL_TIME = "time";
    private final static String CELL_DATE_AND_TIME = "datetime";

    private final String model;
    private final String cellModel;

    public DateFieldInfo(String name, String friendlyName, DateModel dateModel, DateCellModel dateCellModel) {
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
        switch (dateCellModel) {
            case Date:
                cellModel = CELL_DATE;
                break;
            case Time:
                cellModel = CELL_TIME;
                break;
            case DateAndTime:
                cellModel = CELL_DATE_AND_TIME;
                break;
            default:
                throw new IllegalStateException("Un expected Date Cell model");
        }
    }

    public String getModel() {
        return model;
    }

    public String getCellModel() {
        return cellModel;
    }
}
