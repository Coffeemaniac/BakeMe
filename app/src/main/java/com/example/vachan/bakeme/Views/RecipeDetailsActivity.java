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

public class RecipeDetailsActivity extends AppCompatActivity {

    private TextView textView;
    private ArrayList<Steps> stepsList;
    private RecyclerView stepsRecyclerView;
    private StepsAdapter mStepsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        final Recipe recipe = bd.getParcelable("Recipe");
        textView.setText(recipe.getAllIngredients());

        getSupportActionBar().setTitle(recipe.getName());

        stepsList = new ArrayList<>();
        stepsList.addAll(recipe.getSteps());

        stepsRecyclerView = findViewById(R.id.stepsRecyclerView);
        mStepsAdapter = new StepsAdapter(this, stepsList);

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        stepsRecyclerView.setAdapter(mStepsAdapter);

    }
}