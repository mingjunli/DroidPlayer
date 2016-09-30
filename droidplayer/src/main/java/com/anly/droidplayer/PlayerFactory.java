package com.anly.droidplayer;

import android.content.Context;

import com.anly.droidplayer.player.DroidSimpleExoPlayer;

/**
 * Created by mingjun on 16/9/30.
 */

public class PlayerFactory {

    private PlayerFactory() {}

    public static DroidSimpleExoPlayer newExoPlayerInstance(Context context) {
        return new DroidSimpleExoPlayer(context);
    }
}
