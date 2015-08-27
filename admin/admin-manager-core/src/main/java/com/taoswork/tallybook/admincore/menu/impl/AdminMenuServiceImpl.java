package com.taoswork.tallybook.admincore.menu.impl;

import com.taoswork.tallybook.admincore.menu.AdminMenuService;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallybusiness.BusinessUnit;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.PermissionEntry;
import com.taoswork.tallybook.general.authority.domain.permission.Role;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.dataservice.management.manager.DataServiceManager;
import com.taoswork.tallybook.general.extension.annotations.FrameworkService;
import com.taoswork.tallybook.general.solution.menu.Menu;
import com.taoswork.tallybook.general.solution.menu.MenuEntry;
import com.taoswork.tallybook.general.solution.menu.MenuEntryGroup;
import com.taoswork.tallybook.general.solution.menu.impl.MenuEntryGroupImpl;
import com.taoswork.tallybook.general.solution.menu.impl.MenuEntryImpl;
import com.taoswork.tallybook.general.solution.menu.impl.MenuImpl;
import com.taoswork.tallybook.general.solution.weak.WeakData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
@FrameworkService
@Service(AdminMenuService.SERVICE_NAME)
public class AdminMenuServiceImpl implements AdminMenuService {
    @Resource(name = DataServiceManager.COMPONENT_NAME)
    private DataServiceManager dataServiceManager;

    public static final String MG_USER_KEY = "TBMG.User";
    public static final String MG_USER_ICON = "glyphicon-user";

    public static final String ME_PERSON_KEY = "TBME.Person";
    public static final String ME_PERSON_ICON = "glyphicon-user";

    public static final String ME_ORG_KEY = "TBME.Organization";
    public static final String ME_ORG_ICON = "glyphicon-user";

    public static final String MG_SECURITY_KEY = "TBMG.Security";
    public static final String MG_SECURITY_ICON = "glyphicon-user";

    public static final String ME_RES_CRITERIA_KEY = "TBME.ResourceCriteria";
    public static final String ME_RES_CRITERIA_ICON = "glyphicon-user";

    public static final String ME_PERM_ENTRY_KEY = "TBME.PermEntry";
    public static final String ME_PERM_ENTRY_ICON = "glyphicon-user";

    public static final String ME_PERM_KEY = "TBME.Perm";
    public static final String ME_PERM_ICON = "glyphicon-user";

    public static final String ME_ROLE_KEY = "TBME.Role";
    public static final String ME_ROLE_ICON = "glyphicon-user";

    protected WeakData<Menu> menu = new WeakData<Menu>(){
        @Override
        protected Menu createData() {
            return new MenuImpl()
                    .add(new MenuEntryGroupImpl(MG_USER_ICON, MG_USER_KEY)
                                    .addEntry(new MenuEntryImpl(ME_PERSON_ICON, ME_PERSON_KEY, getFriendlyName(Person.class), Person.class))
                                    .addEntry(new MenuEntryImpl(ME_ORG_ICON, ME_ORG_KEY, getFriendlyName(BusinessUnit.class), BusinessUnit.class))
                    )
                    .add(new MenuEntryGroupImpl(MG_SECURITY_ICON, MG_SECURITY_KEY)
                                    .addEntry(new MenuEntryImpl(ME_RES_CRITERIA_ICON, ME_RES_CRITERIA_KEY, getFriendlyName(SecuredResourceFilter.class), SecuredResourceFilter.class))
                                    .addEntry(new MenuEntryImpl(ME_PERM_ENTRY_ICON, ME_PERM_ENTRY_KEY, getFriendlyName(PermissionEntry.class), PermissionEntry.class))
                                    .addEntry(new MenuEntryImpl(ME_PERM_ICON, ME_PERM_KEY, getFriendlyName(Permission.class), Permission.class))
                                    .addEntry(new MenuEntryImpl(ME_ROLE_ICON, ME_ROLE_KEY, getFriendlyName(Role.class), Role.class))
                    )
                    .instance();
        }
    };

    private String getFriendlyName(Class entityClz){
        return dataServiceManager.getEntityResourceName(entityClz.getName());
    }

    public AdminMenuServiceImpl() {

    }

    @Override
    public Menu buildMenu(AdminEmployee adminEmployee) {
        return menu.get();
    }

    @Override
    public MenuEntry findMenuEntryByUrl(String url){
        String realUrl = "/" + url;
        return menu.get().findEntryByUrl(realUrl);
    }

    @Override
    public MenuEntryGroup findMenuEntryGroupByEntryKey(String entryKey){
        return menu.get().findGroupByEntryKey(entryKey);
    }
}
