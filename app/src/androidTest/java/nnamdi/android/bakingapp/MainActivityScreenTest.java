package nnamdi.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import nnamdi.android.bakingapp.ui.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.Matchers.anything;

/**
 * Created by Nnamdi on 2/21/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, true);


    @Test
    public void clickOnListViewItem_OpensStepsActivity(){

        onView(ViewMatchers.withId(R.id.recycler_view_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }
}
