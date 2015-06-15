package com.taoswork.tallybook.general.solution.menu;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public interface MenuEntryGroup {
    String getName();

    MenuEntryGroup setName(String name);

    String getKey();

    MenuEntryGroup setKey(String key);

    String getIcon();

    MenuEntryGroup setIcon(String icon);

    List<MenuEntry> getEntries();

    MenuEntryGroup setEntries(List<MenuEntry> entries);

    MenuEntryGroup addEntry(MenuEntry entry);

    MenuEntryGroup addEntryWithKey(Map<String, MenuEntry> entries, String entryKey);

    MenuEntryGroup addEntryWithKey(Collection<MenuEntry> entries, String entryKey);

    MenuEntry getFirstEntry();

    MenuEntry getDefaultEntry();

    MenuEntry findEntryByKey(String key);

    MenuEntry findEntryByUrl(String url);
}
