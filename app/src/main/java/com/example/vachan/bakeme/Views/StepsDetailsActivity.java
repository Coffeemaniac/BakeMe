package com.example.vachan.bakeme.Views;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);

        steps = new ArrayList<>();

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        steps = bd.getParcelableArrayList(STEP_KEY);
        current_step_number = bd.getInt(STEP_NUMBER_KEY);
        total_steps = steps.size() - 1;

        titleText = "Step " + current_step_number + " of the " + total_steps + " steps";
        getSupportActionBar().setTitle(titleText);

        StepDetailsFragment stepFragment = new StepDetailsFragment();
        stepFragment.setStep(steps.get(current_step_number));

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, stepFragment)
                .commit();
    }


    @Override
    public void onNextButtonClicked(int id) {
        int local_id = id + 1;
        if(local_id <= steps.size()-1) {
            titleText = "Step " + local_id + " of the " + total_steps + " steps";
            getSupportActionBar().setTitle(titleText);
            StepDetailsFragment nextStepFragment = new StepDetailsFragment();
            nextStepFragment.setStep(steps.get(local_id));
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
        if(local_id >= 0) {
            titleText = "Step " + local_id + " of the " + total_steps + " steps";
            getSupportActionBar().setTitle(titleText);
            StepDetailsFragment nextStepFragment = new StepDetailsFragment();
            nextStepFragment.setStep(steps.get(local_id));
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, nextStepFragment)
                    .commit();
        }else{
            Toast.makeText(this, R.string.prevButtonText, Toast.LENGTH_SHORT).show();
        }
    }

}
