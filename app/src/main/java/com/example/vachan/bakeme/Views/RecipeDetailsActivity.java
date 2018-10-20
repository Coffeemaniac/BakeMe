package com.example.vachan.bakeme.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.vachan.bakeme.Model.Recipe;
import com.example.vachan.bakeme.Model.Steps;
import com.example.vachan.bakeme.R;
import com.example.vachan.bakeme.Adapters.StepsAdapter;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "Recipe";

    @BindView(R.id.textView)
    public TextView textView;
    @BindView(R.id.stepsRecyclerView)
    public RecyclerView stepsRecyclerView;

    private ArrayList<Steps> stepsList;
    private StepsAdapter mStepsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        final Recipe recipe = bd.getParcelable(RECIPE_KEY);
        textView.setText(recipe.getAllIngredients());

        getSupportActionBar().setTitle(recipe.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        stepsList = new ArrayList<>();
        stepsList.addAll(recipe.getSteps());

        mStepsAdapter = new StepsAdapter(this, stepsList);

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        stepsRecyclerView.setAdapter(mStepsAdapter);
    }
}
