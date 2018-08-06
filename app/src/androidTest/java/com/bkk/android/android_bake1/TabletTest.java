package com.bkk.android.android_bake1;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class TabletTest {

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
    public void checkForVideoPlayerInTablet() {

        wait2Sec();

        onView(ViewMatchers.withId(R.id.recipe_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        wait2Sec();

        onView(withId(R.id.playerView)).check(matches(isDisplayed()));

    }

} // class
