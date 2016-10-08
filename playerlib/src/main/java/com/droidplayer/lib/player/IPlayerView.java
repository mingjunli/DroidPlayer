package com.droidplayer.lib.player;

import android.view.TextureView;

import com.droidplayer.lib.scale.ScalableTextureView;

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
