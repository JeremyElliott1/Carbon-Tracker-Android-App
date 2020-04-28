package com.example.slidebox;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class setting_test {

    @Rule
    public ActivityTestRule<SettingsOptions> mActivityTestRule = new ActivityTestRule<>(SettingsOptions.class);

    @Test
    public void about_test() {
        ViewInteraction textView = onView(withId(R.id.button_About));
        textView.perform(click());
        ViewInteraction text= onView(withId(R.id.imageView));
        text.check(matches(isDisplayed()));
        ViewInteraction text2= onView(withId(R.id. textView2));
        text2.check(matches(isDisplayed()));
    }

    @Test
    public void Tos_test() {
        ViewInteraction textView = onView(withId(R.id.button_Tos));
        textView.perform(click());
        ViewInteraction text= onView(withId(R.id.imageView));
        text.check(matches(isDisplayed()));
        ViewInteraction text2= onView(withId(R.id. textView2));
        text2.check(matches(isDisplayed()));
    }

    @Test
    public void privacy_policy_test() {
        ViewInteraction textView = onView(withId(R.id.button_privacy_policy));
        textView.perform(click());
        ViewInteraction text= onView(withId(R.id.imageView));
        text.check(matches(isDisplayed()));
        ViewInteraction text2= onView(withId(R.id. textView2));
        text2.check(matches(isDisplayed()));
    }

    @Test
    public void help_test() {
        ViewInteraction textView = onView(withId(R.id.button_Help));
        textView.perform(click());
        onView(withId(R.id.button_help_settings)).check(matches(isDisplayed()));
        onView(withId(R.id.button_help_settings)).perform(click());
        onView(withId(R.id.textView10)).check(matches(withText("In settings you can change the the theme of the app, mute the app or notifications and view our policies.")));

        onView(withId(R.id.button_help_about)).check(matches(isDisplayed()));
        onView(withId(R.id.button_help_about)).perform(click());
        onView(withId(R.id.textView10)).check(matches(withText("This app is for reuseable, recycleable and environmental protection.")));

        onView(withId(R.id.button_help_shop)).check(matches(isDisplayed()));
        onView(withId(R.id.button_help_shop)).perform(click());
        onView(withId(R.id.textView10)).check(matches(withText("Users can use point to exchange certain items.")));

        onView(withId(R.id.button_help_leaderboard)).check(matches(isDisplayed()));
        onView(withId(R.id.button_help_leaderboard)).perform(click());
        onView(withId(R.id.textView10)).check(matches(withText("Leaderboard shows users' points for you to compare.")));

        onView(withId(R.id.button_help_recyclable)).check(matches(isDisplayed()));
        onView(withId(R.id.button_help_recyclable)).perform(click());
        onView(withId(R.id.textView10)).check(matches(withText("Input items you recycle to gain points.")));

        onView(withId(R.id.button_help_reusable)).check(matches(isDisplayed()));
        onView(withId(R.id.button_help_reusable)).perform(click());
        onView(withId(R.id.textView10)).check(matches(withText("Input reusable items you use to gain points.")));

        onView(withId(R.id.button_help_travel)).check(matches(isDisplayed()));
        onView(withId(R.id.button_help_travel)).perform(click());
        onView(withId(R.id.textView10)).check(matches(withText("A journey tracker to monitor your travel, gain points by reducing the time and mode of transport.")));

        onView(withId(R.id.button_help_home)).check(matches(isDisplayed()));
        onView(withId(R.id.button_help_home)).perform(click());
        onView(withId(R.id.textView10)).check(matches(withText("Home page displays your current points, and daily goals in order to gain points.")));

        onView(withId(R.id.button_help_settings)).check(matches(isDisplayed()));
        onView(withId(R.id.button_help_settings)).perform(click());
        onView(withId(R.id.textView10)).check(matches(withText("In settings you can change the the theme of the app, mute the app or notifications and view our policies.")));

        onView(withId(R.id.button_help_profile)).check(matches(isDisplayed()));
        onView(withId(R.id.button_help_profile)).perform(click());
        onView(withId(R.id.textView10)).check(matches(withText("View your profile here and change your display picture.")));
    }
}
