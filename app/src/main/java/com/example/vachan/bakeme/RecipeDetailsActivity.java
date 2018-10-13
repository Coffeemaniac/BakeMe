package com.example.vachan.bakeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vachan.bakeme.Model.Recipe;
import com.example.vachan.bakeme.Model.Steps;

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

        stepsList = new ArrayList<>();
        stepsList.addAll(recipe.getSteps());

        stepsRecyclerView = findViewById(R.id.stepsRecyclerView);
        mStepsAdapter = new StepsAdapter(this, stepsList);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        stepsRecyclerView.setAdapter(mStepsAdapter);

    }
}
