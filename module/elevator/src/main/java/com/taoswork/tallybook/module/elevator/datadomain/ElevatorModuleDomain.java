package com.taoswork.tallybook.module.elevator.datadomain;

import com.taoswork.tallybook.module.elevator.datadomain.facets.ElevatorFill;
import com.taoswork.tallybook.module.elevator.datadomain.facets.EscalatorFill;
import com.taoswork.tallybook.module.elevator.datadomain.sheetfills.AnnualMaintenanceFill;
import com.taoswork.tallybook.module.elevator.datadomain.sheetfills.MonthMaintenanceFill;
import com.taoswork.tallybook.module.elevator.datadomain.sheetfills.RegularMaintenanceFill;
import com.taoswork.tallybook.module.elevator.datadomain.sheetfills.SemiAnnualMaintenanceFill;

/**
 * Created by Gao Yuan on 2016/3/10.
 */
public class ElevatorModuleDomain {
    public static Class<?>[] persistableEntities() {
        return new Class<?>[]{
                ElevatorFill.class,
                EscalatorFill.class,
                AnnualMaintenanceFill.class,
                MonthMaintenanceFill.class,
                RegularMaintenanceFill.class,
                SemiAnnualMaintenanceFill.class,
        };
    }
}
