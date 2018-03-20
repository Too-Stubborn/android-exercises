package com.xuhj.testing.net;

import android.os.IBinder;
import android.support.test.espresso.Root;
import android.support.test.runner.AndroidJUnit4;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.runner.RunWith;

/**
 * Describe :  描述
 * Author   :  xuhj
 * Date     :  2016/11/11
 * Version  :  1.0
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

//    @Rule
//    public ActivityTestRule<JunitLoginActivity> mActivity =
//            new ActivityTestRule<JunitLoginActivity>(JunitLoginActivity.class);
//
//    @Test
//    public void login() throws Exception {
//
//        onView(allOf(withId(R.id.email)))
//                .perform(typeText("1604983712@qq.com"), closeSoftKeyboard())
//                .check(ViewAssertions.matches(withText("1604983712@qq.com")));
//
//        onView(withId(R.id.password))
//                .perform(typeText("asd123"), closeSoftKeyboard());
//
//        onView(withId(R.id.email_sign_in_button))
//                .perform(ViewActions.click());
//
//        onView(withText("success")).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
//
//    }

    public class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    //means this window isn't contained by any other windows.
                    return true;
                }
            }
            return false;
        }
    }

}
