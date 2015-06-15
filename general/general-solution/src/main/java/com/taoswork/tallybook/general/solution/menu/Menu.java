package com.taoswork.tallybook.general.solution.menu;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public interface Menu {

    List<MenuEntryGroup> getEntryGroups();

    Menu setEntryGroups(List<MenuEntryGroup> entryGroups);

    Menu add(MenuEntryGroup entryGroup);

    MenuEntryGroup getFirstGroup();

    MenuEntryGroup findGroupByKey(String key);

    MenuEntryGroup findGroupByEntryKey(String entryKey);

    MenuEntry findEntryByKey(String entryKey);

    MenuEntry findEntryByUrl(String url);

    Menu instance();
}
