package com.example.vachan.bakeme.Views;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.vachan.bakeme.Fragments.StepDetailsFragment;
import com.example.vachan.bakeme.Model.Steps;
import com.example.vachan.bakeme.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

public class StepsDetailsActivity extends AppCompatActivity implements StepDetailsFragment.ParentActivityInterface {

    private ArrayList<Steps> steps;
    private int current_step_number;

    private String title;
    private int total_steps;
    private String titleText;
    private FragmentManager fragmentManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);

        steps = new ArrayList<>();

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        steps = bd.getParcelableArrayList("steps");
        current_step_number = bd.getInt("step_number");
        total_steps = steps.size();

        titleText = "Step " + current_step_number + " of the " + total_steps + " steps";
        getSupportActionBar().setTitle(titleText);

        StepDetailsFragment stepFragment = new StepDetailsFragment();
        stepFragment.setStep(steps.get(current_step_number));

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, stepFragment)
                .commit();


    }

    public void onNextButtonClicked(int id){
            StepDetailsFragment nextStepFragment = new StepDetailsFragment();
            nextStepFragment.setStep(steps.get(id+1));

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, nextStepFragment)
                .commit();
    }


}
