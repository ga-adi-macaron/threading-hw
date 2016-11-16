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
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
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
public class FarmListActivityTest2 {

    @Rule
    public ActivityTestRule<FarmListActivity> mActivityTestRule = new ActivityTestRule<>(FarmListActivity.class);

    @Test
    public void farmListActivityTest2() {
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
        appCompatEditText2.perform(replaceText("Scott Lindley"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.user_state_edit),
                        withParent(allOf(withId(R.id.activity_sign_up),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("New York"), closeSoftKeyboard());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.button_text), withText("Okay"),
                        withParent(allOf(withId(R.id.sign_up_button),
                                withParent(withId(R.id.activity_sign_up)))),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.search_button), withContentDescription("Search"),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.search_button), withContentDescription("Search"),
                        withParent(allOf(withId(R.id.search_bar),
                                withParent(withId(R.id.search)))),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("new york"), closeSoftKeyboard());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withId(R.id.search_src_text), withText("new york"),
                        withParent(allOf(withId(R.id.search_plate),
                                withParent(withId(R.id.search_edit_frame)))),
                        isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.farm_name), withText("Bumble Bee Lands"),
                        isDisplayed()));
        textView4.check(matches(withText("Bumble Bee Lands")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerview),
                        withParent(withId(R.id.content_farm_list)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.add_to_cart_button), isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.favorite_button), withContentDescription("Like"),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.favorite_button), withContentDescription("Like"), isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction relativeLayout = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.likes_recycler),
                                5),
                        0),
                        isDisplayed()));
        relativeLayout.check(matches(isDisplayed()));

        ViewInteraction relativeLayout2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.likes_recycler),
                                5),
                        0),
                        isDisplayed()));
        relativeLayout2.check(matches(isDisplayed()));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.user_name), withText("Scott Lindley"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView6.check(matches(withText("Scott Lindley")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.user_state), withText("New York"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        textView7.check(matches(withText("New York")));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.quantity), withText("1"),
                        childAtPosition(
                                allOf(withId(R.id.quantity_layout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                3)),
                                1),
                        isDisplayed()));
        textView8.check(matches(withText("1")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.total), withText("$11.16"),
                        isDisplayed()));
        textView9.check(matches(withText("$11.16")));

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.quantity_up),
                        withParent(withId(R.id.quantity_layout)),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.quantity), withText("2"),
                        childAtPosition(
                                allOf(withId(R.id.quantity_layout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                3)),
                                1),
                        isDisplayed()));
        textView10.check(matches(withText("2")));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.total), withText("$22.32"),
                        isDisplayed()));
        textView11.check(matches(withText("$22.32")));

        ViewInteraction cardView = onView(
                allOf(withId(R.id.place_order_button), isDisplayed()));
        cardView.perform(click());

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.order_price), withText("$22.32"),
                        childAtPosition(
                                allOf(withId(R.id.card_layout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView12.check(matches(withText("$22.32")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.order_text), withText("Ordered Just Now!"),
                        childAtPosition(
                                allOf(withId(R.id.card_layout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                                0)),
                                1),
                        isDisplayed()));
        textView13.check(matches(withText("Ordered Just Now!")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.nav_user_name), withText("Scott Lindley"),
                        isDisplayed()));
        textView14.check(matches(withText("Scott Lindley")));

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.nav_user_state), withText("New York"),
                        isDisplayed()));
        textView15.check(matches(withText("New York")));

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Settings"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.edit_profile_text), withText("Edit Profile"),
                        isDisplayed()));
        textView16.check(matches(withText("Edit Profile")));

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.edit_profile_text), withText("Edit Profile"),
                        withParent(allOf(withId(R.id.edit_profile_card_button),
                                withParent(withId(R.id.content_settings)))),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.user_name_edit),
                        withParent(withId(R.id.content_settings)),
                        isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.user_name_edit),
                        withParent(withId(R.id.content_settings)),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("John Cena"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.user_state_edit),
                        withParent(withId(R.id.content_settings)),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("Massachusetts"), closeSoftKeyboard());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.edit_profile_text), withText("Okay"),
                        withParent(allOf(withId(R.id.edit_profile_card_button),
                                withParent(withId(R.id.content_settings)))),
                        isDisplayed()));
        appCompatTextView4.perform(click());

        pressBack();

        ViewInteraction textView17 = onView(
                allOf(withId(R.id.user_name), withText("John Cena"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView17.check(matches(withText("John Cena")));

        ViewInteraction textView18 = onView(
                allOf(withId(R.id.user_state), withText("Massachusetts"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        textView18.check(matches(withText("Massachusetts")));

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(R.id.design_menu_item_text), withText("Liked Farms"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction textView19 = onView(
                allOf(withId(R.id.like_farm_name), withText("Bumble Bee Lands"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        textView19.check(matches(withText("Bumble Bee Lands")));

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
