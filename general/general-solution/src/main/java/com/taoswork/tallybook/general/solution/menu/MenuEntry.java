package com.taoswork.tallybook.general.solution.menu;


/**
 * Created by Gao Yuan on 2015/4/28.
 */
public interface MenuEntry{


    String getModule();

    MenuEntry setModule(String module);

    String getName();

    MenuEntry setName(String name);

    String getKey();

    MenuEntry setKey(String key);

    String getIcon();

    MenuEntry setIcon(String icon);

    String getUrl();

    MenuEntry setUrl(String url);

    String getEntity();

    MenuEntry setEntity(String entity);

    String getPermissionKey();

    MenuEntry setPermissionKey(String permissionKey);
}
