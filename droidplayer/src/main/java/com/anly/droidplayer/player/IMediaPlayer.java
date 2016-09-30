package com.anly.droidplayer.player;

import android.net.Uri;
import android.view.TextureView;

import com.anly.droidplayer.view.IPlayerView;

import java.io.IOException;

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

    long getDuration();

    void release();

    void reset();

    void setPlayerView(IPlayerView playerView);
}
