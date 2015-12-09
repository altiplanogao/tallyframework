package com.taoswork.tallybook.dynamic.datameta.testdata.clazzes;

import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.PresentationCollection;
import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.StringEntry;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class FieldsZoo {
    public int _int;
    public Integer _Integer;
    public String _String;

    @PresentationCollection(primitiveDelegate = StringEntry.class)
    public Set _Set;
    @PresentationCollection(primitiveDelegate = StringEntry.class)
    public Set<Integer> _IntegerSet;
    @PresentationCollection(primitiveDelegate = StringEntry.class)
    public HashSet<Integer> _IntegerHashSet;

    @PresentationCollection(primitiveDelegate = StringEntry.class)
    public List _List;
    @PresentationCollection(primitiveDelegate = StringEntry.class)
    public List<Integer> _IntegerList;
    @PresentationCollection(primitiveDelegate = StringEntry.class)
    public ArrayList<Integer> _IntegerArrayList;

    @PresentationCollection(primitiveDelegate = StringEntry.class)
    public Queue _Queue;
    @PresentationCollection(primitiveDelegate = StringEntry.class)
    public Queue<Integer> _IntegerQueue;
    @PresentationCollection(primitiveDelegate = StringEntry.class)
    public Queue<Queue<Integer>> _IntegerQueueQueue;

    public Map _Map;
    public Map<String, String> _StringStringMap;
    public HashMap<String, String> _StringStringHashMap;

    public Dictionary _Dict;
    public Dictionary<List, Map> _Dict_L_M;
    public Dictionary<List<Integer>, Map<Integer, Queue<Integer>>> _Dict_LI_MI__QI;
}
