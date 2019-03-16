package com.example.fruitsdiary;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.fruitsdiary.util.StringUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class StringUtilsTest {
    @Test
    public void getCorrectFruitSpellingTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("bananas", StringUtils.getCorrectFruitSpelling(appContext, 2, "banana"));
        assertEquals("banana", StringUtils.getCorrectFruitSpelling(appContext, 1, "banana"));
        assertEquals("cherries", StringUtils.getCorrectFruitSpelling(appContext, 2, "cherry"));
    }
}
