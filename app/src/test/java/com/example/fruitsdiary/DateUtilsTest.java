package com.example.fruitsdiary;

import com.example.fruitsdiary.util.DateUtils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateUtilsTest {
    @Test
    public void convertServerDateToAppDateTest() {
        assertEquals("January 12, 2019", DateUtils.convertServerDateToAppDate("2019-01-12"));
    }
}