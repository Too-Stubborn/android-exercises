package com.xuhj.testing;


import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainListActivityTest {

//    @Rule
//    public ActivityTestRule<MainListActivity> mActivityTestRule = new ActivityTestRule<>(MainListActivity.class);
//
//    @Test
//    public void mainListActivityTest() {
//        ViewInteraction textView = onView(
//                allOf(withId(android.R.id.text1), withText("com.xuhj.test.ui.test.JunitLoginActivity"),
//                        childAtPosition(
//                                allOf(withId(android.R.id.list),
//                                        withParent(withId(android.R.id.content))),
//                                9),
//                        isDisplayed()));
//        textView.perform(click());
//
//        ViewInteraction appCompatAutoCompleteTextView = onView(
//                withId(R.id.email));
//        appCompatAutoCompleteTextView.perform(scrollTo(), click());
//
//        ViewInteraction appCompatAutoCompleteTextView2 = onView(
//                withId(R.id.email));
//        appCompatAutoCompleteTextView2.perform(typeText("123456@gmail.com"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText = onView(
//                withId(R.id.password));
//        appCompatEditText.perform(typeText("asd123456"), closeSoftKeyboard());
//
//        ViewInteraction checkableImageButton = onView(
//                withId(R.id.text_input_password_toggle));
//        checkableImageButton.perform(click());
//
//        ViewInteraction checkableImageButton2 = onView(
//                withId(R.id.text_input_password_toggle));
//        checkableImageButton2.perform(click());
//
//        ViewInteraction appCompatButton = onView(
//                allOf(withId(R.id.email_sign_in_button), withText("Sign in or register"),
//                        withParent(allOf(withId(R.id.email_login_form),
//                                withParent(withId(R.id.login_form))))));
//        appCompatButton.perform(scrollTo(), click());
//
//    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
