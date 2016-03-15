package com.taoswork.tallybook.module.elevator.def;

import com.taoswork.tallybook.module.IModule;
import com.taoswork.tallybook.module.elevator.dataservice.conf.ModuleConfiguration;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2016/3/14.
 */
public class ModuleBroadcaster {
    @Resource(name = ModuleConfiguration.MODULE_BEAN)
    protected IModule module;

//    public
}
