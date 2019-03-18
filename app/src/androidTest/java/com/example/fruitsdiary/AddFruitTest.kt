package com.example.fruitsdiary

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.fruitsdiary.usecase.addeditentry.AddEditEntryActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class AddFruitTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<AddEditEntryActivity>(AddEditEntryActivity::class.java)

    @Test
    fun addFruitTest() {
        onView(withId(R.id.add_fruit_fab)).perform(click())
        onView(withId(R.id.fruit_list_recyclerview))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()));
        onView(withId(R.id.add_fruits_action)).perform(click())
        onView(withId(R.id.done_add_fruit)).perform(click())
        onView(withRecyclerView(R.id.fruit_recyclerview)
                .atPositionOnView(0, R.id.fruit))
                .check(matches(withText("1 apple")));
    }

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    internal class RecyclerViewMatcher(private val recyclerViewId: Int) {

        fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {
            return object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {

                }

                override fun matchesSafely(view: View): Boolean {
                    var childView: View? = null
                    if (childView == null) {
                        val recyclerView: RecyclerView = view.rootView.findViewById(recyclerViewId);
                        if (recyclerView.id == recyclerViewId) {
                            childView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView;
                        } else {
                            return false;
                        }
                    }

                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        val targetView: View? = childView?.findViewById(targetViewId);
                        return view == targetView;
                    }

                }
            };
        }
    }
}
