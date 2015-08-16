package com.taoswork.tallybook.adminmvc.controllers.entities;

import com.taoswork.tallybook.general.solution.menu.MenuEntry;
import com.taoswork.tallybook.general.solution.menu.MenuEntryGroup;

/**
 * Created by Gao Yuan on 2015/6/6.
 */
public class CurrentPath {
//    private Menu menu;
    private MenuEntryGroup menuGroup;
    private MenuEntry menuEntry;
    private String url;

//    public Menu getMenu() {
//        return menu;
//    }
//
//    public CurrentPath setMenu(Menu menu) {
//        this.menu = menu;
//        return this;
//    }

    public MenuEntryGroup getMenuGroup() {
        return menuGroup;
    }

    public CurrentPath setMenuGroup(MenuEntryGroup menuGroup) {
        this.menuGroup = menuGroup;
        return this;
    }

    public MenuEntry getMenuEntry() {
        return menuEntry;
    }

    public CurrentPath setMenuEntry(MenuEntry menuEntry) {
        this.menuEntry = menuEntry;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public CurrentPath setUrl(String url) {
        this.url = url;
        return this;
    }

    public boolean isMenuGroupMatch(MenuEntryGroup group) {
        if (this.menuGroup == null) {
            return false;
        } else if (group == null) {
            return false;
        } else {
            return menuGroup.getKey().equals(group.getKey());
        }
    }

    public boolean isMenuEntryMatch(MenuEntry entry) {
        if (this.menuEntry == null) {
            return false;
        } else if (entry == null) {
            return false;
        } else {
            return menuEntry.getKey().equals(entry.getKey());
        }
    }
}
