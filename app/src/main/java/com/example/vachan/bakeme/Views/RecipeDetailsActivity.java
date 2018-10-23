package com.example.vachan.bakeme.Views;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.vachan.bakeme.Fragments.RecipeDetailsFragment;
import com.example.vachan.bakeme.Fragments.StepDetailsFragment;
import com.example.vachan.bakeme.Model.Recipe;
import com.example.vachan.bakeme.Model.Steps;
import com.example.vachan.bakeme.R;
import com.example.vachan.bakeme.Adapters.StepsAdapter;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.ActivityInterface,
        StepsAdapter.DetailsActivityInterface, StepDetailsFragment.ParentActivityInterface {

    public static final String RECIPE_KEY = "Recipe";

    public static final String STEP_KEY = "steps";
    public static  final String STEP_NUMBER_KEY = "step_number";

    private ArrayList<Steps> stepsList;
    private StepsAdapter mStepsAdapter;
    private boolean mTwoPane;

    private Steps step;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        recipe = bd.getParcelable(RECIPE_KEY);

        getSupportActionBar().setTitle(recipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(savedInstanceState != null){
            step = savedInstanceState.getParcelable("STEP");
            mTwoPane = savedInstanceState.getBoolean("TWO_PANE");
        }else{
            RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
            fragmentManager.beginTransaction().add(R.id.recipeFragmentContainer, recipeDetailsFragment)
                    .commit();
        }



        if(findViewById(R.id.fragment_container) != null){
            mTwoPane = true;
            if(savedInstanceState == null){
                step = recipe.getSteps().get(0);
                StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                fragmentManager.beginTransaction().add(R.id.fragment_container, stepDetailsFragment).commit();
            }
        }else {
            mTwoPane = false;
        }

        Log.v("mTwoPane", "Value: " + mTwoPane);

    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void doSomething(int position){
        step = recipe.getSteps().get(position);
        if(mTwoPane){
            StepDetailsFragment newStepFragment = new StepDetailsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, newStepFragment).commit();
        }else{
            Intent intent = new Intent(this, StepsDetailsActivity.class);
            intent.putParcelableArrayListExtra(STEP_KEY, recipe.getSteps());
            intent.putExtra(STEP_NUMBER_KEY, position);
            startActivity(intent);
        }
    }

    public void onNextButtonClicked(int id){
    }

    public void onPrevButtonClicked(int id){

    }

    public Steps getSteps(){
        return step;
    }

    public boolean getTwoPane(){
        return mTwoPane;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("STEP", step);
        outState.putBoolean("TWO_PANE", mTwoPane);
    }


}
