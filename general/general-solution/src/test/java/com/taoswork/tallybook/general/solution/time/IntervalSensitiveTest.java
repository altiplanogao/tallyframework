package com.taoswork.tallybook.general.solution.time;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/6/18.
 */
public class IntervalSensitiveTest {
    @Test
    public void neverExpire() throws Exception{
        IntervalSensitive intervalSensitive = new IntervalSensitive(IntervalSensitive.NEVER_EXPIRE);
        Assert.assertEquals(intervalSensitive.getLastTouch(), 0L);
        Assert.assertFalse(intervalSensitive.checkExpired());
        Assert.assertEquals(intervalSensitive.getLastTouch(), 0L);
        Thread.sleep(10);
        Assert.assertFalse(intervalSensitive.checkExpired());
        Thread.sleep(100);
        Assert.assertFalse(intervalSensitive.checkExpired());
    }

    @Test
    public void alwaysExpire() throws Exception{
        IntervalSensitive intervalSensitive = new IntervalSensitive(IntervalSensitive.ALWAYS_EXPIRE);
        Assert.assertEquals(intervalSensitive.getLastTouch(), 0L);
        Assert.assertTrue(intervalSensitive.checkExpired());
        Assert.assertEquals(intervalSensitive.getLastTouch(), 0L);
        Thread.sleep(10);
        Assert.assertTrue(intervalSensitive.checkExpired());
        Thread.sleep(100);
        Assert.assertTrue(intervalSensitive.checkExpired());
    }

    @Test
    public void sometimeExpire() throws Exception{
        IntervalSensitive intervalSensitive = new IntervalSensitive(1000);
        Assert.assertEquals(intervalSensitive.getLastTouch(), 0L);
        Assert.assertTrue(intervalSensitive.checkExpired());
        Assert.assertNotEquals(intervalSensitive.getLastTouch(), 0L);
//        long lastTouch = intervalSensitive.getLastTouch();

        final long lagTolerance = 10;

        {
            long expectedInterval = 10;
            long lastTouch = intervalSensitive.touch();
            Thread.sleep(expectedInterval);
            Assert.assertFalse(intervalSensitive.checkExpired());
            //get now
            long currentAccess = intervalSensitive.getLastTouch();
            //check interval
            long interval = currentAccess - lastTouch;
            Assert.assertNotEquals(interval, 0);
            Assert.assertTrue(interval >= expectedInterval);
            Assert.assertTrue(interval < (expectedInterval + lagTolerance));
        }

        {
            long expectedInterval = 100;
            long lastTouch = intervalSensitive.touch();
            Thread.sleep(expectedInterval);
            Assert.assertFalse(intervalSensitive.checkExpired());
            //get now
            long currentAccess = intervalSensitive.getLastTouch();
            //check interval
            long interval = currentAccess - lastTouch;
            Assert.assertTrue(interval >= expectedInterval);
            Assert.assertTrue(interval < (expectedInterval + lagTolerance));
        }

        {
            long expectedInterval = 1000;
            long lastTouch = intervalSensitive.touch();
            Thread.sleep(expectedInterval);
            Assert.assertTrue(intervalSensitive.checkExpired());
            //get now
            long currentAccess = intervalSensitive.getLastTouch();
            //check interval
            long interval = currentAccess - lastTouch;
            Assert.assertNotEquals(interval, 0);
            Assert.assertTrue(interval >= expectedInterval);
            Assert.assertTrue(interval < (expectedInterval + lagTolerance));
        }

        {
            long expectedInterval = 1100;
            long lastTouch = intervalSensitive.touch();
            Thread.sleep(expectedInterval);
            Assert.assertTrue(intervalSensitive.checkExpired());
            //get now
            long currentAccess = intervalSensitive.getLastTouch();
            //check interval
            long interval = currentAccess - lastTouch;
            Assert.assertTrue(interval >= expectedInterval);
            Assert.assertTrue(interval < (expectedInterval + lagTolerance));
        }
    }

}
