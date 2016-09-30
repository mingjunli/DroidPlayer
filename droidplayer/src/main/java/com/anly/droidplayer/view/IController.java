package com.anly.droidplayer.view;

import android.view.View;

import com.anly.droidplayer.player.IMediaPlayer;

/**
 * Created by mingjun on 16/9/28.
 */

public interface IController {

    /**
     * Listener to be notified about changes of the visibility of the UI control.
     */
    public interface VisibilityListener {
        /**
         * Called when the visibility changes.
         *
         * @param visibility The new visibility. Either {@link View#VISIBLE} or {@link View#GONE}.
         */
        void onVisibilityChange(int visibility);
    }

    void setPlayer(IMediaPlayer player);
}
