package com.example.vachan.bakeme;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.vachan.bakeme.Model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class StepsDetailsActivity extends AppCompatActivity {

    private TextView descriptionTv;
    private PlayerView playerView;
    private ExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;
    private Steps step;
    private String title;
    private int total_steps;
    private String titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);

        descriptionTv = findViewById(R.id.descriptionTv);
        playerView = findViewById(R.id.video_view);
        currentWindow = 0;
        playbackPosition = 0;

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();
        step = bd.getParcelable("Step");
        total_steps = bd.getInt("total_steps") - 1;
        descriptionTv.setText(step.getDescription());

        titleText = "Step " + step.getId() + " of the " + total_steps + " steps";
        getSupportActionBar().setTitle(titleText);




        Log.v("URL", "The video URL is" + step.getVideoURL());

        if(step.getVideoURL().length() == 0){
            playerView.setVisibility(View.GONE);
        }else {
            initializePlayer();
        }
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(true);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(step.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    protected  void onPause(){
        super.onPause();
        releasePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
