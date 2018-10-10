package com.example.vachan.bakeme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vachan.bakeme.Model.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        final Recipe recipe = bd.getParcelable("Recipe");
        textView.setText(recipe.getAllIngredients());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button.getText().equals("Display Steps")){
                    textView.setText(recipe.getAllSteps());
                    button.setText("Show recipe");
                }
                else {
                    textView.setText(recipe.getAllIngredients());
                    button.setText("Display Steps");
                }
            }
        });

    }
}
