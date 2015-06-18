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
        Assert.assertEquals(intervalSensitive.getLastAccess(), 0L);
        Assert.assertFalse(intervalSensitive.isIntervalExpired());
        Assert.assertEquals(intervalSensitive.getLastAccess(), 0L);
        Thread.sleep(10);
        Assert.assertFalse(intervalSensitive.isIntervalExpired());
        Thread.sleep(100);
        Assert.assertFalse(intervalSensitive.isIntervalExpired());
    }

    @Test
    public void alwaysExpire() throws Exception{
        IntervalSensitive intervalSensitive = new IntervalSensitive(IntervalSensitive.ALWAYS_EXPIRE);
        Assert.assertEquals(intervalSensitive.getLastAccess(), 0L);
        Assert.assertTrue(intervalSensitive.isIntervalExpired());
        Assert.assertEquals(intervalSensitive.getLastAccess(), 0L);
        Thread.sleep(10);
        Assert.assertTrue(intervalSensitive.isIntervalExpired());
        Thread.sleep(100);
        Assert.assertTrue(intervalSensitive.isIntervalExpired());
    }

    @Test
    public void sometimeExpire() throws Exception{
        IntervalSensitive intervalSensitive = new IntervalSensitive(1000);
        Assert.assertEquals(intervalSensitive.getLastAccess(), 0L);
        Assert.assertTrue(intervalSensitive.isIntervalExpired());
        Assert.assertNotEquals(intervalSensitive.getLastAccess(), 0L);
        long lastAccess = intervalSensitive.getLastAccess();

        final long lagTolerance = 10;

        {
            long expectedInterval = 10;
            Thread.sleep(expectedInterval);
            Assert.assertFalse(intervalSensitive.isIntervalExpired());
            //get now
            long currentAccess = intervalSensitive.getLastAccess();
            //check interval
            long interval = currentAccess - lastAccess;
            Assert.assertNotEquals(interval, 0);
            Assert.assertTrue(interval < (expectedInterval + lagTolerance)); //

            //update last
            lastAccess = currentAccess;
        }

        {
            long expectedInterval = 100;
            Thread.sleep(expectedInterval);
            Assert.assertFalse(intervalSensitive.isIntervalExpired());
            //get now
            long currentAccess = intervalSensitive.getLastAccess();
            //check interval
            long interval = currentAccess - lastAccess;
            Assert.assertNotEquals(interval, 0);
            Assert.assertTrue(interval < (expectedInterval + lagTolerance)); //

            //update last
            lastAccess = currentAccess;
        }

        {
            long expectedInterval = 1000;
            Thread.sleep(expectedInterval);
            Assert.assertTrue(intervalSensitive.isIntervalExpired());
            //get now
            long currentAccess = intervalSensitive.getLastAccess();
            //check interval
            long interval = currentAccess - lastAccess;
            Assert.assertNotEquals(interval, 0);
            Assert.assertTrue(interval < (expectedInterval + lagTolerance)); //

            //update last
            lastAccess = currentAccess;
        }

        {
            long expectedInterval = 1100;
            Thread.sleep(expectedInterval);
            Assert.assertTrue(intervalSensitive.isIntervalExpired());
            //get now
            long currentAccess = intervalSensitive.getLastAccess();
            //check interval
            long interval = currentAccess - lastAccess;
            Assert.assertNotEquals(interval, 0);
            Assert.assertTrue(interval < (expectedInterval + lagTolerance)); //

            //update last
            lastAccess = currentAccess;
        }
    }

}
