package com.taoswork.tallybook.admincore.menu;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.general.solution.menu.Menu;
import com.taoswork.tallybook.general.solution.menu.MenuEntry;
import com.taoswork.tallybook.general.solution.menu.MenuEntryGroup;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public interface AdminMenuService {
    public static final String SERVICE_NAME = "AdminMenuService";
    Menu buildMenu(AdminEmployee adminEmployee);

    MenuEntry findMenuEntryByUrl(String url);

    MenuEntryGroup findMenuEntryGroupByEntryKey(String entryKey);
}
