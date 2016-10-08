package com.anly.droidplayer.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.FrameLayout;

import com.anly.droidplayer.R;
import com.anly.droidplayer.view.AbstractVideoController;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Timeline;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by mingjun on 16/9/29.
 */

public abstract class AbstractPlayerView extends FrameLayout implements IPlayerView, IPlayerListener {

    private static final String TAG = "AbstractPlayerView";

    public static final int DEFAULT_SHOW_DURATION_MS = 3000;

    private TextureView mSurface;
    private AbstractVideoController mController;
    private IMediaPlayer mPlayer;

    private boolean isControllerShowing;

    private CopyOnWriteArraySet<IController> controllers = new CopyOnWriteArraySet<>();

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

    public void init() {
        LayoutInflater.from(getContext()).inflate(getLayoutResId(), this);
        mSurface = (TextureView) findViewById(R.id.surface);
        mController = (AbstractVideoController) findViewById(R.id.controller);
    }

    public abstract int getLayoutResId();

    @Override
    public void setPlayer(IMediaPlayer player) {
        if (mPlayer != null) {
            mPlayer.removeListener(this);
        }

        mPlayer = player;

        player.addListener(this);
        setController();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mPlayer.isPlaying() && ev.getActionMasked() == MotionEvent.ACTION_DOWN) {

            if (isControllerShowing) {
                hideControllers();
            }
            else {
                showControllers();
            }
        }
        return true;
    }

    public void setController() {
        addController(mController);

        mController.setPlayer(mPlayer);
    }

    @Override
    public TextureView getSurface() {
        return mSurface;
    }

    @Override
    public void addController(IController controller) {
        controllers.add(controller);
    }


    @Override
    public void showControllers() {
        showControllers(DEFAULT_SHOW_DURATION_MS);
    }

    public void showControllers(int delay) {
        for (IController controller : controllers) {
            controller.show();
        }

        isControllerShowing = true;
        hideDeferred(delay);
    }

    @Override
    public void hideControllers() {
        for (IController controller : controllers) {
            controller.hide();
        }

        isControllerShowing = false;
    }

    private final Runnable hideAction = new Runnable() {
        @Override
        public void run() {
            hideControllers();
        }
    };

    private void hideDeferred(int delay) {
        removeCallbacks(hideAction);
        if (delay > 0) {
            postDelayed(hideAction, delay);
        }
    }

    // IPlayerListener
    @Override
    public void onPrepared() {
        showControllers();
    }

    @Override
    public void onLoadingChanged(boolean loading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onBufferUpdated() {

    }

    @Override
    public void onCompleted() {
        showControllers(0);
    }
}
