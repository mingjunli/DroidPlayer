package com.anly.droidplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

import com.anly.droidplayer.R;
import com.anly.droidplayer.player.IMediaPlayer;
import com.anly.droidplayer.player.IPlayerListener;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Timeline;

/**
 * Created by mingjun on 16/9/29.
 */

public class DefaultPlayerView extends AbstractPlayerView {

    private static final String TAG = "DefaultPlayerView";

    private ProgressBar mLoadingView;

    public DefaultPlayerView(Context context) {
        super(context);
    }

    public DefaultPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.default_player_view;
    }

    @Override
    public void init() {
        super.init();

        mLoadingView = (ProgressBar) findViewById(R.id.loading);
    }

    @Override
    public void onPrepared() {
        Log.d(TAG, "onPrepared");
        mLoadingView.setVisibility(VISIBLE);
    }

    @Override
    public void onLoadingChanged(boolean loading) {
        Log.d(TAG, "onLoadingChanged:" + loading);
        mLoadingView.setVisibility(loading ? VISIBLE : GONE);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playWhenReady) {
            // show player
            mLoadingView.setVisibility(GONE);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onBufferUpdated() {

    }

    @Override
    public void onCompleted() {

    }
}
