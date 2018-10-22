package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsDetailFragment extends Fragment {

    public static final String PLAY_BACK_POSITION = "playBackPosition";
    public static final String PLAY_WHEN_READY = "playWhenReady";
    public static final String CURRENT_WINDOW = "currentWindow";
    public static final String VIDEO_URL = "videoURL";
    private static final String LOG_TAG = StepsDetailFragment.class.getName();
    public String description;
    public String videoUrl;
    public SimpleExoPlayer player;
    public long playbackPosition = 0;
    public int currentWindow = 0;
    public boolean playWhenReady = true;
    @BindView(R.id.exoPlayer_view)
    PlayerView playerView;
    @BindView(R.id.step_detail_view)
    TextView stepDetailView;

    public StepsDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAY_BACK_POSITION);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
            videoUrl = savedInstanceState.getString(VIDEO_URL);
        }

        View rootView = inflater.inflate(R.layout.steps_detail_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;

    }

    /*   @Override
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setRetainInstance(true);
       }

       @Override
       public void onViewStateRestored(Bundle savedInstanceState) {
           super.onViewStateRestored(savedInstanceState);
       }
   */
    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23)
            Log.d(LOG_TAG, "Description: " + description);
        Log.d(LOG_TAG, "VideoUrl: " + videoUrl);
        startPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT <= 23 || player == null) {
            startPlayer();
            onConfigurationChange();
        }

    }

    private void onConfigurationChange() {

        int orientation = getActivity().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            stepDetailView.setText(description);
        } else
            playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) playerView.getLayoutParams();
        params.height = params.MATCH_PARENT;
        params.width = params.MATCH_PARENT;
        playerView.setLayoutParams(params);


    }

    private void startPlayer() {

        // Create an instance of the ExoPlayer.

        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        DefaultLoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), trackSelector, loadControl);

        playerView.setPlayer(player);

        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
        //DefaultHttpDataSourceFactory factory1 = new DefaultHttpDataSourceFactory(userAgent,null,DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,true);
        DefaultDataSourceFactory dataSource = new DefaultDataSourceFactory(getContext(), userAgent);

        Uri uri = Uri.parse(videoUrl);
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSource)
                .createMediaSource(uri);
        player.prepare(mediaSource);
        player.setPlayWhenReady(playWhenReady);


    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            releasePlayer();
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAY_BACK_POSITION, playbackPosition);
        outState.putInt(CURRENT_WINDOW, currentWindow);
        outState.putBoolean(PLAY_WHEN_READY, playWhenReady);
        outState.putString(VIDEO_URL, videoUrl);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoURL(String url) {
        videoUrl = url;
    }
}
