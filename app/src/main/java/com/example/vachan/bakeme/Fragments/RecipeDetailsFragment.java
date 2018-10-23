package com.example.vachan.bakeme.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vachan.bakeme.Adapters.StepsAdapter;
import com.example.vachan.bakeme.Model.Recipe;
import com.example.vachan.bakeme.Model.Steps;
import com.example.vachan.bakeme.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    @BindView(R.id.textView)
    public TextView textView;
    @BindView(R.id.stepsRecyclerView)
    public RecyclerView stepsRecyclerView;

    private ArrayList<Steps> stepsList;
    private StepsAdapter mStepsAdapter;
    private Recipe recipe;

    public interface ActivityInterface {
        public Recipe getRecipe();
    }

    private ActivityInterface mCallback;

    public RecipeDetailsFragment(){

    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mCallback = (ActivityInterface) getContext();

        if(recipe == null){
            recipe = mCallback.getRecipe();
        }

        textView.setText(recipe.getAllIngredients());


        stepsList = new ArrayList<>();
        stepsList.addAll(recipe.getSteps());

        mStepsAdapter = new StepsAdapter(getContext(), stepsList);

        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        stepsRecyclerView.setAdapter(mStepsAdapter);

    }




}
