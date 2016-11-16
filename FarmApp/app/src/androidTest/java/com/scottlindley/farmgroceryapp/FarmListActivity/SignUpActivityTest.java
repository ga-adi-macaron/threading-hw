package com.scottlindley.farmgroceryapp.FarmListActivity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.scottlindley.farmgroceryapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<FarmListActivity> mActivityTestRule = new ActivityTestRule<>(FarmListActivity.class);

    @Test
    public void signUpActivityTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.button_text), withText("sign up"),
                        isDisplayed()));
        textView.check(matches(withText("sign up")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.sign_up_text), withText("Ready to start shopping?\nSign up to begin!"),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.button_text), withText("sign up"),
                        withParent(allOf(withId(R.id.sign_up_button),
                                withParent(withId(R.id.activity_sign_up)))),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.button_text), withText("Okay"),
                        isDisplayed()));
        textView3.check(matches(withText("Okay")));

    }

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
