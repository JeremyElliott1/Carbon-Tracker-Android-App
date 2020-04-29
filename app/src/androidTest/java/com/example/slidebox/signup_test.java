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
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class signup_test {

    @Rule
    public ActivityTestRule<SignUp> mActivityTestRule = new ActivityTestRule<>(SignUp.class);

    @Test
    public void logInTest_1() {
        onView(withId(R.id.first_name)).check(matches(isDisplayed()));
        onView(withId(R.id.last_name)).check(matches(isDisplayed()));
        onView(withId(R.id.email_sign_up)).check(matches(isDisplayed()));
        onView(withId(R.id.password_sign_up)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_up)).check(matches(isDisplayed()));
        onView(withId(R.id.first_name)).perform(replaceText("lickyzero@126.com"), closeSoftKeyboard());
        onView(withId(R.id.last_name)).perform(replaceText("lickyzero@126.com"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_up)).perform(replaceText("lickyzero@126.com"), closeSoftKeyboard());
        onView(withId(R.id.password_sign_up)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.sign_up)).perform(click());
        onView(withText("Please enter your full details ")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
    public void logInTest_2() {
        onView(withId(R.id.first_name)).check(matches(isDisplayed()));
        onView(withId(R.id.last_name)).check(matches(isDisplayed()));
        onView(withId(R.id.email_sign_up)).check(matches(isDisplayed()));
        onView(withId(R.id.password_sign_up)).check(matches(isDisplayed()));
        onView(withId(R.id.sign_up)).check(matches(isDisplayed()));
        onView(withId(R.id.first_name)).perform(replaceText("lickyzero@126.com"), closeSoftKeyboard());
        onView(withId(R.id.last_name)).perform(replaceText("lickyzero@126.com"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_up)).perform(replaceText("lickyzero@126.com"), closeSoftKeyboard());
        onView(withId(R.id.password_sign_up)).perform(replaceText("dddddddd"), closeSoftKeyboard());
        onView(withId(R.id.sign_up)).perform(click());
        onView(withText("Please verify your email address to sign in")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
