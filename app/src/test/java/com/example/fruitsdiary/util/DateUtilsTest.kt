package com.example.fruitsdiary.util

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class DateUtilsTest {
    @Test fun convertServerDateToAppDateTest(){
        assertEquals("January 12, 2019", DateUtils.convertServerDateToAppDate("2019-01-12"))
    }
}