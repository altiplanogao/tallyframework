package com.taoswork.tallybook.test.general.extension;

import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.collections.ListBuilder;
import com.taoswork.tallybook.general.extension.collections.ListUtility;
import com.taoswork.tallybook.general.extension.collections.MapBuilder;
import com.taoswork.tallybook.general.extension.utils.TPredicate;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
public class CollectionTest {

    protected Map<String, Integer> makeIntegerMap(int min, int max, int kvDiff){
        MapBuilder<String, Integer> mb = new MapBuilder<String, Integer>();

        for(int i = min ; i <= max ; ++i){
            mb.put("" + i , i + kvDiff);
        }
        return mb.result();
    }

    protected List<Integer> makeIntegerList(int min, int max){
        List<Integer> mb = new ArrayList<Integer>();

        for(int i = min ; i <= max ; ++i){
            mb.add(i);
        }
        return mb;
    }
    @Test
    public void testMapBuilder() {
        Map<String, Integer> map = MapBuilder.instance("1", 2)
                .put("2", 3)
                .put("3", 4).result();

        Assert.assertTrue(map.size() == 3);
        for(int i = 1 ; i< 4 ; ++i){
            Integer got = map.get("" + i);
            Integer expect = Integer.valueOf(i + 1);
            Assert.assertTrue(expect.equals(got));
        }

    }

    @Test
    public void testListBuilder(){
        List<String> list = new ListBuilder<String>()
                .put("1").put("2").put("3").result();
        Assert.assertTrue(list.size() == 3);
        for(int i = 0 ; i< 3 ; ++i){
            String got = list.get(i);
            String expect = "" + (i + 1);
            Assert.assertTrue(expect.equals(got));
        }
    }

    @Test
    public void testCollectionUtility() {
        Assert.assertTrue(CollectionUtility.isEmpty(makeIntegerList(1, 2)) == false);
        Assert.assertTrue(CollectionUtility.isEmpty(null) == true);
        Assert.assertTrue(CollectionUtility.isEmpty(new ArrayList()) == true);

        Assert.assertTrue(CollectionUtility.isEmpty(new HashSet()) == true);

        List<String> list = new ListBuilder<String>().put("11").put("22").put("33").result();
        String v = CollectionUtility.find(list, new TPredicate<String>() {
            @Override
            public boolean evaluate(String notNullObj) {
                return notNullObj.contains("2");
            }
        });
        String v2 = CollectionUtility.find(list, new TPredicate<String>() {
            @Override
            public boolean evaluate(String notNullObj) {
                return notNullObj.contains("4");
            }
        });
        Assert.assertTrue("22".equals(v));
        Assert.assertTrue(v2 == null);
    }

    @Test
    public void testListUtility(){
        List<Integer> list0 = makeIntegerList(1,0);
        List<Integer> list1 = makeIntegerList(1,1);
        List<Integer> list2 = makeIntegerList(1,2);

        Integer e = ListUtility.getTheSingleElement(list0);
        Assert.assertTrue(e == null);
        e = ListUtility.getTheSingleElement(list1);
        Assert.assertTrue(e.equals(1));
        e = ListUtility.getTheSingleElement(list2);
        Assert.assertTrue(e == null);
    }
}
