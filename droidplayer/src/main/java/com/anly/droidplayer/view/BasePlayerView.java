package com.anly.droidplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.widget.FrameLayout;

import com.anly.droidplayer.R;
import com.anly.droidplayer.player.AbstractPlayer;
import com.anly.droidplayer.player.DroidSimpleExoPlayer;
import com.anly.droidplayer.player.IMediaPlayer;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * Created by mingjun on 16/9/29.
 */

public abstract class BasePlayerView extends FrameLayout implements IPlayerView {

    private TextureView mSurface;
    private IMediaPlayer player;

    public BasePlayerView(Context context) {
        super(context);
        init();
    }

    public BasePlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BasePlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(getLayoutResId(), this);
        mSurface = (TextureView) findViewById(R.id.surface);
    }

    public abstract int getLayoutResId();

    @Override
    public void setPlayer(IMediaPlayer player) {
        this.player = player;
    }

    @Override
    public TextureView getSurface() {
        return mSurface;
    }
}
