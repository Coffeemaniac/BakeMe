package com.example.vachan.bakeme;

/* https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json */

import com.example.vachan.bakeme.Model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkAPIClient {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getRecipes ();
}
