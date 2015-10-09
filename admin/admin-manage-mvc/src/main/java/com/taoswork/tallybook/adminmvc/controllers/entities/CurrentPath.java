package com.taoswork.tallybook.adminmvc.controllers.entities;

import com.taoswork.tallybook.general.solution.menu.IMenuEntry;
import com.taoswork.tallybook.general.solution.menu.MenuEntry;
import com.taoswork.tallybook.general.solution.menu.MenuPath;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/6.
 */
public class CurrentPath {
    private MenuPath path;
    private Collection<IMenuEntry> menuEntries;
    private String url;

    public String getUrl() {
        return url;
    }

    public CurrentPath setUrl(String url) {
        this.url = url;
        return this;
    }

    public MenuPath getPath() {
        return path;
    }

    public Collection<IMenuEntry> getMenuEntries() {
        return menuEntries;
    }

    public CurrentPath setMenuEntries(MenuPath path, Collection<IMenuEntry> menuEntries) {
        this.path = path;
        this.menuEntries = menuEntries;
        return this;
    }

    public void pushEntry(String name, String url) {
        if (StringUtils.isNotEmpty(name)) {
            IMenuEntry menuEntry = new MenuEntry();
            menuEntry.setName(name).setUrl(url);
            this.menuEntries.add(menuEntry);
        }
    }

    public boolean isMatchPath(String... keys){
        String[] paths = this.getPath().getPath().toArray(new String[]{});
        if(keys.length > paths.length){
            return false;
        }

        int len = keys.length;
        for(int i = 0 ; i < len ; ++i){
            if(!keys[i].equals(paths[i]))
                return false;
        }
        return true;
    }
}
