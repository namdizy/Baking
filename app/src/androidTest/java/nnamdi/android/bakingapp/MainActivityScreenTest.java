package nnamdi.android.bakingapp;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import nnamdi.android.bakingapp.ui.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import android.support.test.espresso.assertion.ViewAssertions;

/**
 * Created by Nnamdi on 2/21/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, true);


    @Test
    public void clickOnListViewItem_OpensStepsActivity(){


        onView(withRecyclerView(R.id.recycler_view_recipe).atPosition(0))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        onView(withRecyclerView(R.id.recycler_view_recipe).atPositionOnView(0, R.id.tv_recipe_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("Nutella Pie")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        onView(withRecyclerView(R.id.recycler_view_recipe).atPositionOnView(0, R.id.tv_recipe_servings))
                .check(ViewAssertions.matches(ViewMatchers.withText("Servings: 8")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));


        onView(withRecyclerView(R.id.recycler_view_recipe).atPosition(0))
                .perform(click());

    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

}
