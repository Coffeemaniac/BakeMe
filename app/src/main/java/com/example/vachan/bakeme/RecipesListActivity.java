package com.example.vachan.bakeme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.vachan.bakeme.Model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* Base URL : https://d17h27t6h515a5.cloudfront.net */

public class RecipesListActivity extends AppCompatActivity {

    private NetworkAPIClient client;
    private ArrayList<Recipe> recipesList;
    private RecyclerView recipesRecyclerView;
    private RecipeListAdapter myAdapter;
    
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
}
