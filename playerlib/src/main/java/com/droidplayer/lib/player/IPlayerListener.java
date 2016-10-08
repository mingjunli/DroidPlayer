package com.droidplayer.lib.player;

/**
 * Created by mingjun on 16/9/30.
 */

public interface IPlayerListener {

    void onPrepared();

    void onLoadingChanged(boolean loading);

    void onPlayerStateChanged(boolean playWhenReady, int playbackState);

    void onPlayerError(PlaybackException error);

    void onBufferUpdated(int percent);

    void onCompleted();
}
