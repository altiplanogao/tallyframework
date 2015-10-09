package com.taoswork.tallybook.general.solution.menu;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public interface IMenu {
    IMenuEntry getEntry(MenuPath path);

    IMenuEntry getFirstLeafEntry();

    Collection<IMenuEntry> getEntriesOnPath(MenuPath path);

    Collection<MenuPath> getPathOfUrl(String url);

    Collection<MenuPath> getPathOfEntity(String entity);

    Collection<MenuPath> getPathOfEntry(String key);

    MenuPath getSinglePathOfUrl(String url);

    MenuPath getSinglePathOfEntity(String entity);

    MenuPath getSinglePathOfEntry(String key);

    Collection<IMenuEntry> getTopEntries();
}
