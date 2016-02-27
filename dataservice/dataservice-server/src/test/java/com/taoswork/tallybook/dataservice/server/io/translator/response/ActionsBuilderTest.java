package com.taoswork.tallybook.dataservice.server.io.translator.response;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.datadomain.base.restful.EntityAction;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/9/6.
 */
public class ActionsBuilderTest {
    @Test
    public void testOverallAction() {
        ActionsBuilder.CurrentStatus status = ActionsBuilder.CurrentStatus.Nothing;

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq, status),
                EntityAction.CREATE,
                EntityAction.READ,
                EntityAction.UPDATE,
                EntityAction.DELETE,
                EntityAction.QUERY);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Create), status),
                EntityAction.READ,
                EntityAction.UPDATE,
                EntityAction.DELETE,
                EntityAction.QUERY);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Read), status),
                EntityAction.CREATE,
                EntityAction.UPDATE,
                EntityAction.DELETE,
                EntityAction.QUERY);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Update), status),
                EntityAction.CREATE,
                EntityAction.READ,
                EntityAction.DELETE,
                EntityAction.QUERY);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Delete), status),
                EntityAction.CREATE,
                EntityAction.READ,
                EntityAction.UPDATE,
                EntityAction.QUERY);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Query), status),
                EntityAction.CREATE,
                EntityAction.READ,
                EntityAction.UPDATE,
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.None, status));
    }

    @Test
    public void testReadingAction() {
        ActionsBuilder.CurrentStatus status = ActionsBuilder.CurrentStatus.Reading;

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq, status),
                EntityAction.UPDATE,
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Create), status),
                EntityAction.UPDATE,
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Read), status),
                EntityAction.UPDATE,
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Update), status),
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Delete), status),
                EntityAction.UPDATE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Query), status),
                EntityAction.UPDATE,
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.None, status));
    }

    @Test
    public void testEditingAction() {
        ActionsBuilder.CurrentStatus status = ActionsBuilder.CurrentStatus.Editing;

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq, status),
                EntityAction.SAVE,
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Create), status),
                EntityAction.SAVE,
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Read), status),
                EntityAction.SAVE,
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Update), status),
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Delete), status),
                EntityAction.SAVE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Query), status),
                EntityAction.SAVE,
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.None, status));
    }

    @Test
    public void testEditAheadReadingAction() {
        ActionsBuilder.CurrentStatus status = ActionsBuilder.CurrentStatus.EditAheadReading;

        assertCollectionMatching(   //Editing
                ActionsBuilder.makeActions(Access.Crudq, status),
                EntityAction.SAVE,
                EntityAction.DELETE);

        assertCollectionMatching(   //Editing
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Create), status),
                EntityAction.SAVE,
                EntityAction.DELETE);

        assertCollectionMatching(   //Editing
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Read), status),
                EntityAction.SAVE,
                EntityAction.DELETE);

        assertCollectionMatching(   //Reading
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Update), status),
                EntityAction.DELETE);

        assertCollectionMatching(   //Editing
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Delete), status),
                EntityAction.SAVE);

        assertCollectionMatching(   //Editing
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Query), status),
                EntityAction.SAVE,
                EntityAction.DELETE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.None, status));
    }

    @Test
    public void testAddingAction() {
        ActionsBuilder.CurrentStatus status = ActionsBuilder.CurrentStatus.Adding;

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq, status),
                EntityAction.SAVE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Create), status));

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Read), status),
                EntityAction.SAVE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Update), status),
                EntityAction.SAVE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Delete), status),
                EntityAction.SAVE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Query), status),
                EntityAction.SAVE);

        assertCollectionMatching(
                ActionsBuilder.makeActions(Access.None, status));
    }

    private void assertCollectionMatching(Collection<EntityAction> target, EntityAction... actions) {
        Set<EntityAction> actionSet = new HashSet<EntityAction>();
        for (EntityAction a : actions) {
            actionSet.add(a);
        }
        Assert.assertEquals(actionSet.size(), target.size());
        for (EntityAction t : actionSet) {
            Assert.assertTrue(target.contains(t));
        }
    }
}
