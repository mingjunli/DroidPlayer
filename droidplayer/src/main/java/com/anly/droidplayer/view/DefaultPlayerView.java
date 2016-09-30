package com.anly.droidplayer.view;

import android.content.Context;
import android.util.AttributeSet;

import com.anly.droidplayer.R;

/**
 * Created by mingjun on 16/9/29.
 */

public class DefaultPlayerView extends AbstractPlayerView {

    public DefaultPlayerView(Context context) {
        super(context);
    }

    public DefaultPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.default_player_view;
    }
}
