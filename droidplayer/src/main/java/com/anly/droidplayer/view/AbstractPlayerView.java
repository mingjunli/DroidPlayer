package com.anly.droidplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.FrameLayout;

import com.anly.droidplayer.R;
import com.anly.droidplayer.player.IMediaPlayer;

/**
 * Created by mingjun on 16/9/29.
 */

public abstract class AbstractPlayerView extends FrameLayout implements IPlayerView {

    private TextureView mSurface;
    private AbstractController mController;
    private IMediaPlayer mPlayer;

    public AbstractPlayerView(Context context) {
        super(context);
        init();
    }

    public AbstractPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbstractPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(getLayoutResId(), this);
        mSurface = (TextureView) findViewById(R.id.surface);
        mController = (AbstractController) findViewById(R.id.controller);
    }

    public abstract int getLayoutResId();

    @Override
    public void setPlayer(IMediaPlayer player) {
        mPlayer = player;
        setUseController(useController);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (useController && ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            if (mController.isVisible()) {
                mController.hide();
            } else {
                mController.show();
            }
        }
        return true;
    }
    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        if (!useController) {
            return false;
        }
        mController.show();
        return true;
    }

    private boolean useController = true;
    public void setUseController(boolean useController) {
        this.useController = useController;
        if (useController) {
            mController.setPlayer(mPlayer);
        } else {
            mController.hide();
            mController.setPlayer(null);
        }
    }

    @Override
    public TextureView getSurface() {
        return mSurface;
    }
}
