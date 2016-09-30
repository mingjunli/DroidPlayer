package com.anly.droidplayer.view;

import android.view.TextureView;

import com.anly.droidplayer.player.IMediaPlayer;

/**
 * Created by mingjun on 16/9/29.
 */

public interface IPlayerView {

    void setPlayer(IMediaPlayer player);

    TextureView getSurface();
}
