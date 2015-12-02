package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;
import com.taoswork.tallybook.general.authority.core.basic.Access;

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

    private static void makeOverallActions(Access access, Collection<String> actions) {
        //CRUD
        if (access.hasGeneral(Access.CREATE)) actions.add(EntityActionNames.CREATE);
        if (access.hasGeneral(Access.READ)) actions.add(EntityActionNames.READ);
        if (access.hasGeneral(Access.UPDATE)) actions.add(EntityActionNames.UPDATE);
        if (access.hasGeneral(Access.DELETE)) actions.add(EntityActionNames.DELETE);
        if (access.hasGeneral(Access.QUERY)) actions.add(EntityActionNames.QUERY);
    }

    private static void makeReadingActions(Access access, Collection<String> actions) {
        //Edit, Delete
        if (access.hasGeneral(Access.UPDATE)) actions.add(EntityActionNames.UPDATE);
        if (access.hasGeneral(Access.DELETE)) actions.add(EntityActionNames.DELETE);
    }

    private static void makeEditingActions(Access access, Collection<String> actions) {
        //Save, Delete
        if (access.hasGeneral(Access.UPDATE)) actions.add(EntityActionNames.SAVE);
        if (access.hasGeneral(Access.DELETE)) actions.add(EntityActionNames.DELETE);
    }

    private static void makeEditAheadReadingActions(Access access, Collection<String> actions) {
        //Save, Delete
        if (access.hasGeneral(Access.UPDATE)) {
            makeEditingActions(access, actions);
        } else {
            makeReadingActions(access, actions);
        }
    }

    private static void makeAddingActions(Access access, Collection<String> actions) {
        //Save
        if (access.hasGeneral(Access.CREATE)) actions.add(EntityActionNames.SAVE);
    }

    public static Collection<String> makeActions(Access access, CurrentStatus status) {
        Collection<String> actions = new ArrayList<String>();
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
