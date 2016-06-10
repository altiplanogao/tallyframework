package com.taoswork.tallybook.dataservice.mongo.core.script;

import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.taoswork.tallybook.general.solution.exception.UnexpectedException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.BasicBSONList;
import org.bson.types.ObjectId;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.query.Query;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/3/29.
 */
public class MongoDataImporter {
    private DataScript getManagedImported(AdvancedDatastore datastore, String task) {
        Query<DataScript> q = datastore.createQuery(DataScript.class).field("task").equal(task).limit(1);
        return q.get();
    }
    
    public boolean checkLoaded(AdvancedDatastore datastore, String task){
        DataScript dataScript = getManagedImported(datastore, task);
        if(dataScript != null)
            return dataScript.imported;
        return false;
    }

    public void load(AdvancedDatastore datastore, String task, String file){
        if(checkLoaded(datastore, task))
            return;
        boolean loadSuccess = false;
        try {
            doLoad(datastore, task, file);
            loadSuccess =true;
        }finally {
            DataScript dataScript = getManagedImported(datastore, task);
            if(dataScript == null){
                dataScript = new DataScript(task, file, loadSuccess);
            }else {
                dataScript.setImported(loadSuccess);
            }
            datastore.save(dataScript);
        }
    }

    private void doLoad(AdvancedDatastore datastore, String task, String file) {
        try {
            InputStream res = MongoDataImporter.class.getResource(file).openStream();
            String theString = IOUtils.toString(res, CharEncoding.UTF_8);
            Object obj = com.mongodb.util.JSON.parse(theString);
            BasicBSONList dblist = (BasicBSONList)obj;
            for(Object collectionSet : dblist){
                DBObject dbo = (DBObject)collectionSet;
                String collection = (String)dbo.get("collection");
                Class collectionClz = Class.forName(collection);
                BasicBSONList entries = (BasicBSONList)dbo.get("entries");
                List<DBObject> list = new ArrayList<DBObject>();
                for (Object entrySet : entries){
                    DBObject entry = (DBObject)entrySet;
                    String id = (String)entry.get("_id");
                    if(StringUtils.isNotEmpty(id)){
                        ObjectId oid = new ObjectId(id);
                        entry.put("_id", oid);
                    }
                    list.add(entry);
                }
                datastore.getCollection(collectionClz).insert(list);
            }
        } catch (IOException e) {
            throw new UnexpectedException(e);
        } catch (ClassNotFoundException e) {
            throw new UnexpectedException(e);
        } catch (MongoException e) {
            throw new UnexpectedException(e);
        }
    }
}
