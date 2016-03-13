package com.taoswork.tallybook.module.elevator.entity;

import com.taoswork.tallybook.module.elevator.entity.facets.ElevatorFill;
import com.taoswork.tallybook.module.elevator.entity.facets.EscalatorFill;
import com.taoswork.tallybook.module.elevator.entity.sheetfills.AnnualMaintenanceFill;
import com.taoswork.tallybook.module.elevator.entity.sheetfills.MonthMaintenanceFill;
import com.taoswork.tallybook.module.elevator.entity.sheetfills.RegularMaintenanceFill;
import com.taoswork.tallybook.module.elevator.entity.sheetfills.SemiAnnualMaintenanceFill;

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
