package com.example.vachan.bakeme.Model;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class Results {

    private String recipesList;

    public Results(String recipesList) {
        this.recipesList = recipesList;
    }

    public String getRecipesList() {
        return recipesList;
    }
}
