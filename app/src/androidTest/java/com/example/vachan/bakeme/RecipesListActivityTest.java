package com.example.vachan.bakeme;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vachan.bakeme.Adapters.RecipeListAdapter;
import com.example.vachan.bakeme.Views.RecipesListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipesListActivityTest {

    @Rule
    public ActivityTestRule<RecipesListActivity> mActivityTestRule = new ActivityTestRule<>(RecipesListActivity.class);

    @Test
    public void clickOnItemAndOpenDetails(){
       onView(ViewMatchers.withId(R.id.recipesListView)).perform(RecyclerViewActions.<RecipeListAdapter.MyViewHolder>actionOnItemAtPosition(0, click()));
       onView(ViewMatchers.withId(R.id.textView)).check(matches(withText("Graham cracker")));
    }
}
