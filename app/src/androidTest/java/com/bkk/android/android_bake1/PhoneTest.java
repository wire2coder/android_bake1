package com.bkk.android.android_bake1;


import static android.app.Instrumentation.ActivityResult;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
public class PhoneTest {

    // class variables
    private static String NUTELLA_PIE = "Nutella Pie";
    private static String BROWNIES = "Brownies";
    private static String YELLOW_CAKE = "Yellow Cake";
    private static String CHEESECAKE = "Cheesecake";


    // helper
    public void wait2Sec() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


//    @Before
//    public void stubAllExternalIntents() {
//        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
//        // every test run. In this case all external Intents will be blocked.
//        intending(not(isInternal())).respondWith(new ActivityResult(Activity.RESULT_OK, null));
//    }


    @Test
    public void checkForFourRecipes() {

//        onView(ViewMatchers.withId(R.id.recipe_recycler))
//                .perform(RecyclerViewActions.scrollToPosition(1));


        onView(withText(NUTELLA_PIE)).check(matches(isDisplayed()));
        onView(withText(BROWNIES)).check(matches(isDisplayed()));
        onView(withText(YELLOW_CAKE)).check(matches(isDisplayed()));
        onView(withText(CHEESECAKE)).check(matches(isDisplayed()));

    }

    @Test
    public void checkForVideoPlayerInPhone() {

        onView(ViewMatchers.withId(R.id.recipe_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        wait2Sec();

        onView(ViewMatchers.withId(R.id.rv_recipe_detail))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        wait2Sec();

        onView(withId(R.id.playerView)).check(matches(isDisplayed()));

    }


} // class
