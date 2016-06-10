package com.taoswork.tallybook.dataservice.server.io.translator.response;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.datadomain.base.restful.EntityAction;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/9/6.
 */
public class ActionsBuilder {
    public static enum CurrentStatus {
        Nothing,
        Reading,
        Editing,
        EditAheadReading,   //if has update privilege, then editing, else reading
        Adding
    }

    private static void makeOverallActions(Access access, Collection<EntityAction> actions) {
        //CRUD
        if (access.hasGeneral(Access.CREATE)) actions.add(EntityAction.CREATE);
        if (access.hasGeneral(Access.READ)) actions.add(EntityAction.READ);
        if (access.hasGeneral(Access.UPDATE)) actions.add(EntityAction.UPDATE);
        if (access.hasGeneral(Access.DELETE)) actions.add(EntityAction.DELETE);
        if (access.hasGeneral(Access.QUERY)) actions.add(EntityAction.QUERY);
    }

    private static void makeReadingActions(Access access, Collection<EntityAction> actions) {
        //Edit, Delete
        if (access.hasGeneral(Access.UPDATE)) actions.add(EntityAction.UPDATE);
        if (access.hasGeneral(Access.DELETE)) actions.add(EntityAction.DELETE);
    }

    private static void makeEditingActions(Access access, Collection<EntityAction> actions) {
        //Save, Delete
        if (access.hasGeneral(Access.UPDATE)) actions.add(EntityAction.SAVE);
        if (access.hasGeneral(Access.DELETE)) actions.add(EntityAction.DELETE);
    }

    private static void makeEditAheadReadingActions(Access access, Collection<EntityAction> actions) {
        //Save, Delete
        if (access.hasGeneral(Access.UPDATE)) {
            makeEditingActions(access, actions);
        } else {
            makeReadingActions(access, actions);
        }
    }

    private static void makeAddingActions(Access access, Collection<EntityAction> actions) {
        //Save
        if (access.hasGeneral(Access.CREATE)) actions.add(EntityAction.SAVE);
    }

    public static Collection<EntityAction> makeActions(Access access, CurrentStatus status) {
        Collection<EntityAction> actions = new ArrayList<EntityAction>();
        switch (status) {
            case Nothing:
                makeOverallActions(access, actions);
                break;
            case Reading:
                makeReadingActions(access, actions);
                break;
            case Editing:
                makeEditingActions(access, actions);
                break;
            case EditAheadReading:
                makeEditAheadReadingActions(access, actions);
                break;
            case Adding:
                makeAddingActions(access, actions);
                break;
            default:
                throw new RuntimeException("");
        }
        return actions;
    }
}
