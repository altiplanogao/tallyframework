package com.taoswork.tallybook.business.datadomain.tallybusiness.work;

import com.taoswork.tallycheck.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public enum WorkTicketStatus  implements IFriendlyEnum<String> {
    New("new", "new"),
    Working("working","working"),
    Closed("closed","closed"),
    Finished("finished","finished");

    private final String type;
    private final String friendlyType;

    private static final Map<String, WorkTicketStatus> typeToEnum = new HashMap<String, WorkTicketStatus>();

    static {
        for (WorkTicketStatus _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    WorkTicketStatus(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static WorkTicketStatus fromType(String character) {
        return typeToEnum.get(character);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getFriendlyType() {
        return friendlyType;
    }
}
