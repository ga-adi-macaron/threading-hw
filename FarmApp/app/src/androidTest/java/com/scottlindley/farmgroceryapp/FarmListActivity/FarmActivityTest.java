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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FarmActivityTest {

    @Rule
    public ActivityTestRule<FarmListActivity> mActivityTestRule = new ActivityTestRule<>(FarmListActivity.class);

    @Test
    public void farmActivityTest() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.button_text), withText("sign up"),
                        withParent(allOf(withId(R.id.sign_up_button),
                                withParent(withId(R.id.activity_sign_up)))),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.user_name_edit),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.user_name_edit),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("Scott"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.user_state_edit),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("NY"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.button_text), withText("Okay"),
                        withParent(allOf(withId(R.id.sign_up_button),
                                withParent(withId(R.id.activity_sign_up)))),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerview),
                        withParent(withId(R.id.content_farm_list)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.favorite_button), withContentDescription("Like"),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withText("Sunny Side Farms"),
                        isDisplayed()));
        textView2.check(matches(withText("Sunny Side Farms")));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.favorite_button), withContentDescription("Like"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.user_name), withText("Scott"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Scott")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.user_state), withText("NY"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        textView4.check(matches(withText("NY")));

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
