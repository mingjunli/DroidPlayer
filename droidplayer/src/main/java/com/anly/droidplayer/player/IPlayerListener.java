package com.anly.droidplayer.player;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Timeline;

/**
 * Created by mingjun on 16/9/30.
 */

public interface IPlayerListener {

    void onLoadingChanged(boolean loading);

    void onPlayerStateChanged(boolean playWhenReady, int playbackState);

    void onTimelineChanged(Timeline timeline, Object manifest);

    void onPlayerError(ExoPlaybackException error);

    void onPositionDiscontinuity();

    void onBufferUpdated();
}
