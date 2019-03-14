package com.xm.mvptest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String key = "D2:29:0C:93:E7:70:26:D4:15:61:D6:6F:41:DB:4C:23";
        System.out.println(key.replaceAll(":","").toLowerCase());
        assertEquals(4, 2 + 2);
    }
}