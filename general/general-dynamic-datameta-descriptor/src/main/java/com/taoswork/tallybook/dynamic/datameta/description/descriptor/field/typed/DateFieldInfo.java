package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.BasicFieldInfoBase;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateCellMode;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateMode;

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

    public DateFieldInfo(String name, String friendlyName, boolean editable, DateMode dateMode, DateCellMode dateCellMode) {
        super(name, friendlyName, editable);
        switch (dateMode) {
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
                throw new IllegalStateException("Un expected Date mode");
        }
        switch (dateCellMode) {
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
                throw new IllegalStateException("Un expected Date Cell mode");
        }
    }

    public String getModel() {
        return model;
    }

    public String getCellModel() {
        return cellModel;
    }
}
