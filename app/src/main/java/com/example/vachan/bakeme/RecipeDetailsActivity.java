package com.example.vachan.bakeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vachan.bakeme.Model.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        Recipe recipe = bd.getParcelable("Recipe");
        textView.setText(recipe.getAllIngredients());
    }
}
