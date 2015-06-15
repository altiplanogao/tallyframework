package com.taoswork.tallybook.general.solution.menu.impl;


import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.TPredicate;
import com.taoswork.tallybook.general.solution.menu.MenuEntry;
import com.taoswork.tallybook.general.solution.menu.MenuEntryGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public class MenuEntryGroupImpl implements MenuEntryGroup {
    private String icon;
    private String name;
    private String key;
    private List<MenuEntry> entries = new ArrayList<MenuEntry>();

    public MenuEntryGroupImpl(){
        this("", "");
    }

    public MenuEntryGroupImpl(String icon, String key){
        this.icon = icon;
        this.key = key;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public MenuEntryGroupImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public MenuEntryGroupImpl setKey(String key) {
        this.key = key;
        return this;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public MenuEntryGroupImpl setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public List<MenuEntry> getEntries() {
        return entries;
    }

    @Override
    public MenuEntryGroupImpl setEntries(List<MenuEntry> entries) {
        this.entries = entries;
        return this;
    }

    @Override
    public MenuEntryGroup addEntry(MenuEntry entry){
        if(null != entry){
            this.entries.add(entry);
        }
        return this;
    }

    @Override
    public MenuEntryGroup addEntryWithKey(Map<String, MenuEntry> entries, final String entryKey){
        MenuEntry entry = entries.getOrDefault(entryKey, null);
        if(null != entry){
            this.entries.add(entry);
        }
        return this;
    }

    @Override
    public MenuEntryGroup addEntryWithKey(Collection<MenuEntry> entries, final String entryKey) {
        this.entries.add(CollectionUtility.find(this.entries, new TPredicate<MenuEntry>() {
            @Override
            public boolean evaluate(MenuEntry o) {
                return o.getKey().equals(entryKey);
            }
        }));
        return this;
    }

    @Override
    public MenuEntry getFirstEntry(){
        if(null == entries || entries.isEmpty()){
            return null;
        }
        return entries.get(0);
    }

    @Override
    public MenuEntry getDefaultEntry(){
        return getFirstEntry();
    }

    @Override
    public MenuEntry findEntryByKey(final String key) {
        return CollectionUtility.find(this.entries, new TPredicate<MenuEntry>() {
            @Override
            public boolean evaluate(MenuEntry o) {
                return key.equals(((MenuEntry) o).getKey());
            }
        });
    }

    @Override
    public MenuEntry findEntryByUrl(final String url) {
        return CollectionUtility.find(this.entries, new TPredicate<MenuEntry>() {
            @Override
            public boolean evaluate(MenuEntry o) {
                return url.equals(((MenuEntry) o).getUrl());
            }
        });
    }

}
