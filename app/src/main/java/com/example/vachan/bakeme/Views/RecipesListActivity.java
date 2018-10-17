package com.example.vachan.bakeme.Views;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.vachan.bakeme.IngredientsWidget;
import com.example.vachan.bakeme.Model.Recipe;
import com.example.vachan.bakeme.Network.NetworkAPIClient;
import com.example.vachan.bakeme.R;
import com.example.vachan.bakeme.Adapters.RecipeListAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/* Base URL : https://d17h27t6h515a5.cloudfront.net */

public class RecipesListActivity extends AppCompatActivity implements RecipeListAdapter.ListActivityInterface {

    private NetworkAPIClient client;
    private ArrayList<Recipe> recipesList;
    private RecyclerView recipesRecyclerView;
    private RecipeListAdapter myAdapter;

    //private CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource("Network_Call");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        recipesList = new ArrayList<>();

        recipesRecyclerView = findViewById(R.id.recipesListView);
        myAdapter = new RecipeListAdapter(this, recipesList);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recipesRecyclerView.setAdapter(myAdapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        client = retrofit.create(NetworkAPIClient.class);
        client.getRecipes().enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipesList.addAll(response.body());
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.v("failed", "why is this shit failing?");
            }
        });

    }

    public void sendInfo(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);

        SharedPreferences.Editor editor = this.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit();
        editor.putString("ingredients", recipe.getName() + "\n\n  " + recipe.getAllIngredients());
        editor.putString("name", recipe.getName());
        editor.apply();

        Intent broadCastIntent = new Intent(this, IngredientsWidget.class);
        broadCastIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        sendBroadcast(broadCastIntent);

        intent.putExtra("Recipe", recipe);

        startActivity(intent);
    }
}
