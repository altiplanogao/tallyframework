package com.taoswork.tallybook.general.authority.mockup.accessor;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.domain.access.ResourceAccess;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class MenuResourceAccess extends ResourceAccess {
    public static final int VISIBLE = 0x01;
    public static final int ALL = 0x01;

    public static final Access visible = new Access(Access.NONE, VISIBLE);
    public static final Access all = new Access(Access.NONE, ALL);

    public boolean isVisible() {
        return super.getCanOperate(VISIBLE);
    }

    public MenuResourceAccess setVisible(boolean value) {
        super.setCanOperate(VISIBLE, value);
        return this;
    }
}
