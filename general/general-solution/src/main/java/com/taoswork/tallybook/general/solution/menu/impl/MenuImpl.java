package com.taoswork.tallybook.general.solution.menu.impl;


import com.taoswork.tallybook.general.solution.menu.Menu;
import com.taoswork.tallybook.general.solution.menu.MenuEntry;
import com.taoswork.tallybook.general.solution.menu.MenuEntryGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public class MenuImpl implements Menu {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuImpl.class);

    private List<MenuEntryGroup> entryGroups = new ArrayList<MenuEntryGroup>();

    private transient Map<String, MenuEntry> entryMapByUrl;
    private transient Map<String, MenuEntry> entryMapByKey;
    private transient Map<String, MenuEntryGroup> groupMapByKey;
    private transient Map<String, MenuEntryGroup> groupMapByEntryKey;

    @Override
    public List<MenuEntryGroup> getEntryGroups() {
        return entryGroups;
    }

    @Override
    public Menu setEntryGroups(List<MenuEntryGroup> entryGroups) {
        this.entryGroups = entryGroups;
        return this;
    }

    @Override
    public Menu add(MenuEntryGroup entryGroup) {
        this.entryGroups.add(entryGroup);
        return this;
    }

    @Override
    public MenuEntryGroup getFirstGroup() {
        if (entryGroups != null && entryGroups.size() > 0) {
            return entryGroups.get(0);
        }
        return null;
    }

    @Override
    public MenuEntryGroup findGroupByKey(final String key) {
        ensureQuickMapping();
        return groupMapByKey.getOrDefault(key, null);
    }

    @Override
    public MenuEntryGroup findGroupByEntryKey(final String entryKey) {
        ensureQuickMapping();
        return groupMapByEntryKey.getOrDefault(entryKey, null);
    }

    @Override
    public MenuEntry findEntryByKey(final String entryKey) {
        ensureQuickMapping();
        return entryMapByKey.getOrDefault(entryKey, null);
    }

    @Override
    public MenuEntry findEntryByUrl(final String url) {
        ensureQuickMapping();
        return entryMapByUrl.getOrDefault(url, null);
    }

    protected void ensureQuickMapping(){
        if(entryMapByUrl != null){
            return;
        }
        entryMapByUrl = new HashMap<String, MenuEntry>();
        entryMapByKey = new HashMap<String, MenuEntry>();
        groupMapByKey = new HashMap<String, MenuEntryGroup>();
        groupMapByEntryKey = new HashMap<String, MenuEntryGroup>();

        for (MenuEntryGroup group : entryGroups){
            if(groupMapByKey.containsKey(group.getKey())){
                LOGGER.error("MenuGroup with key '{}' already exist", group.getKey());
            }
            groupMapByKey.put(group.getKey(), group);
            for(MenuEntry entry : group.getEntries()){
                if(entryMapByKey.containsKey(entry.getKey())){
                    LOGGER.error("MenuEntry with key '{}' already exist", entry.getKey());
                }
                entryMapByKey.put(entry.getKey(), entry);

                if(entryMapByUrl.containsKey(entry.getUrl())){
                    if(entryMapByUrl.containsKey(entry.getUrl())){
                        LOGGER.error("MenuEntry with url '{}' already exist", entry.getUrl());
                    }
                }
                entryMapByUrl.put(entry.getUrl(), entry);

                groupMapByEntryKey.put(entry.getKey(), group);
            }
        }

    }

    @Override
    public Menu instance() {
        return this;
    }
}
