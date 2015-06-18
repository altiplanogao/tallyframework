package com.taoswork.tallybook.dynamic.dataservice.dynamic.data4test.domain;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class FieldsZoo {
    public int _int;
    public Integer _Integer;
    public String _String;

    public int[] _intArray;
    public Integer[] _IntegerArray;
    public String[] _StringArray;

    public Set _Set;
    public Set<Integer> _IntegerSet;
    public HashSet<Integer> _IntegerHashSet;

    public List _List;
    public List<Integer> _IntegerList;
    public ArrayList<Integer> _IntegerArrayList;

    public Map _Map;
    public Map<String, String> _StringStringMap;
    public HashMap<String, String> _StringStringHashMap;

    public Queue _Queue;
    public Queue<Integer> _IntegerQueue;
    public Queue<Queue<Integer>> _IntegerQueueQueue;

    public Dictionary _Dict;
    public Dictionary<List, Map> _Dict_L_M;
    public Dictionary<List<Integer>, Map<Integer, Queue<Integer>>> _Dict_LI_MI__QI;
}
