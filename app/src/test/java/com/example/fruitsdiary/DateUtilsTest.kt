package com.example.fruitsdiary

import com.example.fruitsdiary.util.DateUtils

import org.junit.Test

import org.junit.Assert.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
internal class DateUtilsTest {
    @Test fun convertServerDateToAppDateTest(){
        assertEquals("January 12, 2019", DateUtils.convertServerDateToAppDate("2019-01-12"))
    }
}