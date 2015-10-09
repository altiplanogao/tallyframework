package com.taoswork.tallybook.admincore.menu;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.general.solution.menu.IMenu;
import com.taoswork.tallybook.general.solution.menu.IMenuEntry;
import com.taoswork.tallybook.general.solution.menu.MenuPath;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public interface AdminMenuService {
    public static final String SERVICE_NAME = "AdminMenuService";
    IMenu buildMenu(AdminEmployee adminEmployee);

    //sss
    Collection<IMenuEntry> getEntriesOnPath(MenuPath path);

    MenuPath findMenuPathByUrl(String url);

    MenuPath findMenuPathByEntryKey(String entryKey);
}
