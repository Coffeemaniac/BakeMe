package com.example.vachan.bakeme.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {

/*
    descriptionTv = view.findViewById(R.id.descriptionTv);
    playerView = view.findViewById(R.id.video_view);
    next = view.findViewById(R.id.next);
    prev = view.findViewById(R.id.prev);
                                                  */

    @BindView(R.id.video_view)
    public PlayerView playerView;
    @BindView(R.id.descriptionTv)
    public TextView descriptionTv;
    @BindView(R.id.next)
    public Button next;
    @BindView(R.id.prev)
    public Button prev;

    private ExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;


    private Steps step;

    private ParentActivityInterface mCallback;

    public interface ParentActivityInterface {
        public void onNextButtonClicked(int id);

        public void onPrevButtonClicked(int id);
    }

    public void setStep(Steps step) {
        this.step = step;
    }

    public StepDetailsFragment() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_details, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        mCallback = (ParentActivityInterface) getContext();


        currentWindow = 0;
        playbackPosition = 0;

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onNextButtonClicked(step.getId());
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onPrevButtonClicked(step.getId());
            }
        });

        descriptionTv.setText(step.getStepInfo());

        if(step.getVideoURL().length() == 0){
            playerView.setVisibility(View.GONE);
        }else {
            initializePlayer();
        }
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
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
                new DefaultHttpDataSourceFactory("BakeMe-App")).
                createMediaSource(uri);
    }

    public void onPause(){
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
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
