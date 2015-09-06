package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;
import com.taoswork.tallybook.general.authority.core.basic.Access;
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
    public void testOverallAction(){
        ActionsBuilder.CurrentStatus status = ActionsBuilder.CurrentStatus.Nothing;

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq, status),
            EntityActionNames.ADD,
            EntityActionNames.READ,
            EntityActionNames.UPDATE,
            EntityActionNames.DELETE,
            EntityActionNames.SEARCH);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Create), status),
            EntityActionNames.READ,
            EntityActionNames.UPDATE,
            EntityActionNames.DELETE,
            EntityActionNames.SEARCH);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Read), status),
            EntityActionNames.ADD,
            EntityActionNames.UPDATE,
            EntityActionNames.DELETE,
            EntityActionNames.SEARCH);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Update), status),
            EntityActionNames.ADD,
            EntityActionNames.READ,
            EntityActionNames.DELETE,
            EntityActionNames.SEARCH);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Delete), status),
            EntityActionNames.ADD,
            EntityActionNames.READ,
            EntityActionNames.UPDATE,
            EntityActionNames.SEARCH);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Query), status),
            EntityActionNames.ADD,
            EntityActionNames.READ,
            EntityActionNames.UPDATE,
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.None, status));
    }

    @Test
    public void testReadingAction(){
        ActionsBuilder.CurrentStatus status = ActionsBuilder.CurrentStatus.Reading;

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq, status),
            EntityActionNames.UPDATE,
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Create), status),
            EntityActionNames.UPDATE,
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Read), status),
            EntityActionNames.UPDATE,
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Update), status),
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Delete), status),
            EntityActionNames.UPDATE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Query), status),
            EntityActionNames.UPDATE,
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.None, status));
    }

    @Test
    public void testEditingAction(){
        ActionsBuilder.CurrentStatus status = ActionsBuilder.CurrentStatus.Editing;

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq, status),
            EntityActionNames.SAVE,
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Create), status),
            EntityActionNames.SAVE,
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Read), status),
            EntityActionNames.SAVE,
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Update), status),
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Delete), status),
            EntityActionNames.SAVE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Query), status),
            EntityActionNames.SAVE,
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.None, status));
    }

    @Test
    public void testEditAheadReadingAction(){
        ActionsBuilder.CurrentStatus status = ActionsBuilder.CurrentStatus.EditAheadReading;

        assertCollectionMatching(   //Editing
            ActionsBuilder.makeActions(Access.Crudq, status),
            EntityActionNames.SAVE,
            EntityActionNames.DELETE);

        assertCollectionMatching(   //Editing
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Create), status),
            EntityActionNames.SAVE,
            EntityActionNames.DELETE);

        assertCollectionMatching(   //Editing
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Read), status),
            EntityActionNames.SAVE,
            EntityActionNames.DELETE);

        assertCollectionMatching(   //Reading
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Update), status),
            EntityActionNames.DELETE);

        assertCollectionMatching(   //Editing
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Delete), status),
            EntityActionNames.SAVE);

        assertCollectionMatching(   //Editing
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Query), status),
            EntityActionNames.SAVE,
            EntityActionNames.DELETE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.None, status));
    }
    @Test
    public void testAddingAction(){
        ActionsBuilder.CurrentStatus status = ActionsBuilder.CurrentStatus.Adding;

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq, status),
            EntityActionNames.SAVE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Create), status));

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Read), status),
            EntityActionNames.SAVE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Update), status),
            EntityActionNames.SAVE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Delete), status),
            EntityActionNames.SAVE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.Crudq.exclude(Access.Query), status),
            EntityActionNames.SAVE);

        assertCollectionMatching(
            ActionsBuilder.makeActions(Access.None, status));
    }

    private void assertCollectionMatching(Collection<String> target, String... actions){
        Set<String> actionSet = new HashSet<String>();
        for(String a : actions){
            actionSet.add(a);
        }
        Assert.assertEquals(actionSet.size(), target.size());
        for(String t : actionSet){
            Assert.assertTrue(target.contains(t));
        }
    }
}
