package com.taoswork.tallybook.general.solution.menu.impl;


import com.taoswork.tallybook.general.solution.menu.MenuEntry;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public class MenuEntryImpl implements MenuEntry {

    private String module;
    private String name;
    private String key;
    private String icon;
    private String url;
    private String entity;

    /**
     * Defines a permission entry with this value, the tallyuser owns the permission entry can access this menu entry
     * If the permissionKey is empty, tallyuser has any access (any except none) to this.entity can access this menu entry.
     */
    private String permissionKey;

    public MenuEntryImpl() {
        this("", "", "", "");
    }

    public MenuEntryImpl(String icon, String key, String url, Class entity){
        this(icon, key, url,entity.getName());
    }

    public MenuEntryImpl(String icon, String key, String url, String entity){
        if(!url.startsWith("/")){
            url = "/" + url;
        }
        this.icon = icon;
        this.key = key;
        this.url = url;
        this.entity = entity;
    }

    @Override
    public String getModule() {
        return module;
    }

    @Override
    public MenuEntryImpl setModule(String module) {
        this.module = module;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public MenuEntryImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public MenuEntryImpl setKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public MenuEntryImpl setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public MenuEntryImpl setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String getEntity() {
        return entity;
    }

    @Override
    public MenuEntryImpl setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public String getPermissionKey() {
        return permissionKey;
    }

    @Override
    public MenuEntryImpl setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
        return this;
    }
}
