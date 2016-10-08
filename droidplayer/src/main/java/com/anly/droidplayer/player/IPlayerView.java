package com.anly.droidplayer.player;

import android.view.TextureView;

import com.anly.droidplayer.player.IMediaPlayer;

/**
 * Created by mingjun on 16/9/29.
 */

public interface IPlayerView {

    void setPlayer(IMediaPlayer player);

    void addController(IController controller);

    void showControllers();
    void hideControllers();

    TextureView getSurface();

}
