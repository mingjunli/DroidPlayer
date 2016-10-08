package com.droidplayer.lib.player;

import android.net.Uri;

/**
 * Created by mingjun on 16/9/29.
 */

public interface IMediaPlayer {

    void setDataSource(Uri uri) throws IllegalArgumentException, IllegalStateException;

    void prepare() throws IllegalStateException;

    void start() throws IllegalStateException;

    void pause() throws IllegalStateException;

    void stop() throws IllegalStateException;

    void seekTo(long var1) throws IllegalStateException;

    long getCurrentPosition();

    long getBufferedPosition();

    int getPlaybackState();

    long getDuration();

    void release();

    void reset();

    boolean isPlaying();

    void attachPlayerView(IPlayerView playerView);

    void addListener(IPlayerListener listener);
    void removeListener(IPlayerListener listener);
}
