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
public class LogInTest {

    @Rule
    public ActivityTestRule<LogIn> mActivityTestRule = new ActivityTestRule<>(LogIn.class);

    @Test
    public void logInTest() {
        ViewInteraction editText = onView(withId(R.id.user_email));
        editText.check(matches(isDisplayed()));
        ViewInteraction editText2 = onView(
               withId(R.id.user_password));
        editText2.check(matches(isDisplayed()));
        ViewInteraction button = onView(withId(R.id.login));
        button.check(matches(isDisplayed()));
        ViewInteraction appCompatEditText = onView(withId(R.id.user_email));
        appCompatEditText.perform(replaceText("lickyzero@126.com"), closeSoftKeyboard());
        ViewInteraction appCompatEditText2 = onView(withId(R.id.user_password));
        appCompatEditText2.perform(replaceText("12345678"), closeSoftKeyboard());
        ViewInteraction appCompatButton = onView(withId(R.id.login));
        appCompatButton.perform(click());
        ViewInteraction actionMenuItemView = onView(withId(R.id.logo_home));
       actionMenuItemView.check(matches(isDisplayed()));

    }
}
