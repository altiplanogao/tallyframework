package com.taoswork.tallybook.testmaterial.general.domain;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.StringEntry;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class FieldsZoo {
    public int _int;
    public Integer _Integer;
    public String _String;

    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
    public Set _Set;
    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
    public Set<Integer> _IntegerSet;
    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
    public HashSet<Integer> _IntegerHashSet;

    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
    public List _List;
    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
    public List<Integer> _IntegerList;
    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
    public ArrayList<Integer> _IntegerArrayList;

    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
    public Queue _Queue;
    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
    public Queue<Integer> _IntegerQueue;
    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
    public Queue<Queue<Integer>> _IntegerQueueQueue;

    public Map _Map;
    public Map<String, String> _StringStringMap;
    public HashMap<String, String> _StringStringHashMap;

    public Dictionary _Dict;
    public Dictionary<List, Map> _Dict_L_M;
    public Dictionary<List<Integer>, Map<Integer, Queue<Integer>>> _Dict_LI_MI__QI;
}
