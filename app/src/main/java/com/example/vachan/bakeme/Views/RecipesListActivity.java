package com.example.vachan.bakeme.Views;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.espresso.idling.CountingIdlingResource;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/* Base URL : https://d17h27t6h515a5.cloudfront.net */

public class RecipesListActivity extends AppCompatActivity implements RecipeListAdapter.ListActivityInterface {

    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    public static final String RECIPE_KEY = "Recipe";
    public static final String INGREDIENT_KEY = "ingredients";
    public static final String NAME_KEY = "name";
    public static final String SHARED_PREFERENCES_KEY = "prefs";
    public static final String RESOURCE_NAME = "Network_Call";
    public static final String ACTION = "android.appwidget.action.APPWIDGET_UPDATE";


    private NetworkAPIClient client;
    private ArrayList<Recipe> recipesList;
    private RecipeListAdapter myAdapter;

    @BindView(R.id.recipesListView)
    public RecyclerView recipesRecyclerView;

    private CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource(RESOURCE_NAME);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        ButterKnife.bind(this);

        recipesList = new ArrayList<>();

        myAdapter = new RecipeListAdapter(this, recipesList);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recipesRecyclerView.setAdapter(myAdapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        espressoTestIdlingResource.increment();

        client = retrofit.create(NetworkAPIClient.class);
        client.getRecipes().enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipesList.addAll(response.body());
                myAdapter.notifyDataSetChanged();
                espressoTestIdlingResource.decrement();
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                espressoTestIdlingResource.decrement();
            }
        });

    }

    public void sendInfo(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);

        SharedPreferences.Editor editor = this.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
        editor.putString(INGREDIENT_KEY, recipe.getName() + "\n\n" + recipe.getAllIngredients());
        editor.putString(NAME_KEY, recipe.getName());
        editor.apply();

        Intent broadCastIntent = new Intent(this, IngredientsWidget.class);
        broadCastIntent.setAction(ACTION);
        sendBroadcast(broadCastIntent);

        intent.putExtra(RECIPE_KEY, recipe);

        startActivity(intent);
    }

    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        return espressoTestIdlingResource;
    }
}
