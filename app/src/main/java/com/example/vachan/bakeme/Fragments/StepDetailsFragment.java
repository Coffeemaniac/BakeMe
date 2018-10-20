package com.example.vachan.bakeme.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {

    @BindView(R.id.video_view)
    public PlayerView playerView;
    @BindView(R.id.descriptionTv)
    public TextView descriptionTv;
    @BindView(R.id.next)
    public Button next;
    @BindView(R.id.prev)
    public Button prev;
    @BindView(R.id.stepImage)
    public ImageView stepImage;

    private ExoPlayer player;
    private long playbackPosition;
    private boolean playWhenReady;
    private Uri uri;
    private MediaSource mediaSource;


    private Steps step;

    private ParentActivityInterface mCallback;

    public interface ParentActivityInterface {
        void onNextButtonClicked(int id);
        void onPrevButtonClicked(int id);
        Steps getSteps();
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

        Log.v("called", "onViewCreated is being called in fragment");

        ButterKnife.bind(this, view);

        mCallback = (ParentActivityInterface) getContext();

        if(savedInstanceState != null){
            playWhenReady = savedInstanceState.getBoolean("PLAYER_STATE");
            playbackPosition = savedInstanceState.getLong("POSITION");
            Log.v("SavedInstance", "Playbackposition: " + playbackPosition);
            step = savedInstanceState.getParcelable("STEP");
        }else{
            playWhenReady = true;
            playbackPosition = 0;
            step = mCallback.getSteps();
        }



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

        /* Error handing for JSON responses */

        if(step.getVideoURL().isEmpty()){
            playerView.setVisibility(View.GONE);
            stepImage.setVisibility(View.VISIBLE);
            if(step.getThumbnailURL().isEmpty() || step.getThumbnailURL().contains(".mp4")){
                Picasso.with(getContext())
                        .load("https://i.imgur.com/Ifn5z2q.png")
                        .error(R.drawable.error_image)
                        .into(stepImage);
            }else{
                Picasso.with(getContext())
                        .load(step.getThumbnailURL())
                        .error(R.drawable.error_image)
                        .into(stepImage);
            }
        }else{
            uri = Uri.parse(step.getVideoURL());
            mediaSource = buildMediaSource(uri);
            initializePlayer();
        }
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        player.prepare(mediaSource, true, false);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(playbackPosition);
        playerView.setPlayer(player);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("BakeMe-App")).
                createMediaSource(uri);
    }


    public void onPause(){
        super.onPause();
        if(player != null) {
            playbackPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
        }
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        Log.v("Values1", "playbackPosition: " + playbackPosition );
        outState.putParcelable("STEP", step);
        outState.putLong("POSITION", playbackPosition);
        outState.putBoolean("PLAYER_STATE", playWhenReady);
        super.onSaveInstanceState(outState);

    }
}
