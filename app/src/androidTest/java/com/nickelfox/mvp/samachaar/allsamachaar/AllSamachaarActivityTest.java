package com.nickelfox.mvp.samachaar.allsamachaar;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.nickelfox.mvp.samachaar.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AllSamachaarActivityTest {

    @Rule
    public ActivityTestRule<AllSamachaarActivity> mActivityTestRule = new ActivityTestRule<>(AllSamachaarActivity.class);

    @Test
    public void allSamachaarActivityTest() {
        ViewInteraction cardView = onView(
                allOf(withId(R.id.samachaar_card),
                        childAtPosition(
                                allOf(withId(R.id.science_recyclerView),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        cardView.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.samachaar_Image),
                        childAtPosition(
                                allOf(withId(R.id.samachaar_linear_layout),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.samachaar_title), withText("PMLA Case: Delhi HC seeks Robert Vadra's response on ED's plea - Doordarshan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.samachaar_card),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
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
