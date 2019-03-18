package com.example.fruitsdiary

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.fruitsdiary.util.StringUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class StringUtilsTest {
    @Test
    fun getCorrectFruitSpellingTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("bananas", StringUtils.getCorrectFruitSpelling(appContext, 2, "banana"))
        assertEquals("banana", StringUtils.getCorrectFruitSpelling(appContext, 1, "banana"))
        assertEquals("cherries", StringUtils.getCorrectFruitSpelling(appContext, 2, "cherry"))
    }
}
