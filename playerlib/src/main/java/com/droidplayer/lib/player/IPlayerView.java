package com.droidplayer.lib.player;

import android.view.TextureView;

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
