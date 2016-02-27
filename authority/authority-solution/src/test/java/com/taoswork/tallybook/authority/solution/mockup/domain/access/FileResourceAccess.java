package com.taoswork.tallybook.authority.solution.mockup.domain.access;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.solution.domain.ResourceAccess;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class FileResourceAccess extends ResourceAccess {
    public static final int OPEN = 0x01;
    public static final int WRITE = 0x02;
    public static final int EXECUTE = 0x04;
    public static final int ALL = 0x07;

    public static final Access open = new Access(Access.NONE, OPEN);
    public static final Access write = new Access(Access.NONE, WRITE);
    public static final Access execute = new Access(Access.NONE, EXECUTE);
    public static final Access all = new Access(Access.NONE, ALL);

    public boolean canOpen() {
        return super.getCanOperate(OPEN);
    }

    public FileResourceAccess setCanOpen(boolean value) {
        super.setCanOperate(OPEN, value);
        return this;
    }

    public boolean canWrite() {
        return super.getCanOperate(WRITE);
    }

    public FileResourceAccess setCanWrite(boolean value) {
        super.setCanOperate(WRITE, value);
        return this;
    }

    public boolean canExecute() {
        return super.getCanOperate(EXECUTE);
    }

    public FileResourceAccess setCanExecute(boolean value) {
        super.setCanOperate(EXECUTE, value);
        return this;
    }
}

