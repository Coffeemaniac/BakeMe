package com.example.vachan.bakeme.Views;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vachan.bakeme.Fragments.StepDetailsFragment;
import com.example.vachan.bakeme.Model.Steps;
import com.example.vachan.bakeme.R;

import java.util.ArrayList;

public class StepsDetailsActivity extends AppCompatActivity implements StepDetailsFragment.ParentActivityInterface {

    private ArrayList<Steps> steps;
    private int current_step_number;

    private String title;
    private int total_steps;
    private String titleText;
    private FragmentManager fragmentManager;

    public static final String STEP_KEY = "steps";
    public static  final String STEP_NUMBER_KEY = "step_number";

    private final String SIMPLE_FRAGMENT_TAG = "myfragmenttag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        steps = new ArrayList<>();

        if(savedInstanceState != null){
            steps = savedInstanceState.getParcelableArrayList(STEP_KEY);
            current_step_number = savedInstanceState.getInt("CURRENT_STEP_NUMBER");
        }else{
            Intent intent = getIntent();
            final Bundle bd = intent.getExtras();
            steps = bd.getParcelableArrayList(STEP_KEY);
            current_step_number = bd.getInt(STEP_NUMBER_KEY);
            StepDetailsFragment stepFragment = new StepDetailsFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, stepFragment)
                    .commit();
        }

        total_steps = steps.size() - 1;
        titleText = "Step " + current_step_number + " of the " + total_steps + " steps";
        getSupportActionBar().setTitle(titleText);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STEP_KEY, steps);
        outState.putInt("CURRENT_STEP_NUMBER", current_step_number);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onNextButtonClicked(int id) {
        int local_id = id + 1;
        current_step_number = local_id;
        if(local_id <= steps.size()-1) {
            titleText = "Step " + local_id + " of the " + total_steps + " steps";
            getSupportActionBar().setTitle(titleText);
            StepDetailsFragment nextStepFragment = new StepDetailsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, nextStepFragment)
                    .commit();
        }else{
            Toast.makeText(this, R.string.nextButtonText, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPrevButtonClicked(int id){
        int local_id = id - 1;
        current_step_number = local_id;
        if(local_id >= 0) {
            titleText = "Step " + local_id + " of the " + total_steps + " steps";
            getSupportActionBar().setTitle(titleText);
            StepDetailsFragment nextStepFragment = new StepDetailsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, nextStepFragment)
                    .commit();
        }else{
            Toast.makeText(this, R.string.prevButtonText, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Steps getSteps(){
        return steps.get(current_step_number);
    }

    public boolean getTwoPane(){
        return false;
    }
}
