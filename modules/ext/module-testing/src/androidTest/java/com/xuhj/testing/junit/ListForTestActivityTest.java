package com.xuhj.testing.junit;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.xuhj.testing.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Describe :  描述
 * Author   :  xuhj
 * Date     :  2016/11/14
 * Version  :  1.0
 */
@RunWith(AndroidJUnit4.class)
public class ListForTestActivityTest {

    @Rule
    public ActivityTestRule<JunitListActivity> mActivity = new ActivityTestRule<JunitListActivity>(JunitListActivity.class);

    @Test
    public void listView() throws Exception {
//        onView(withText("item:99")).check(ViewAssertions.matches(isDisplayed()));
//        onView(withId(R.id.listView)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.listView)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.listView)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.listView)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.listView)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.listView)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.listView)).perform(ViewActions.swipeUp());
//        onView(withId(R.id.listView)).perform(ViewActions.swipeUp());
        onView(allOf(withText("item:3"))).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), findItemWithListView("item:100")))
                .inAdapterView(withId(R.id.listView));

    }

    /**
     * 自定义Matcher，匹配ListView的每项数据
     *
     * @param content
     * @return
     */
    public static Matcher<Object> findItemWithListView(final String content) {
        return new BoundedMatcher<Object, String>(String.class) {
            @Override
            protected boolean matchesSafely(String item) {
                return item != null && content != null
                        && TextUtils.equals(item, content);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item has :" + content);
            }

        };
    }
}