package com.anly.droidplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.widget.FrameLayout;

import com.anly.droidplayer.R;
import com.anly.droidplayer.player.IMediaPlayer;
import com.anly.droidplayer.player.IPlayerListener;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by mingjun on 16/9/29.
 */

public abstract class AbstractPlayerView extends FrameLayout implements IPlayerView, IPlayerListener {

    private TextureView mSurface;
    private AbstractVideoController mController;
    private IMediaPlayer mPlayer;

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
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {

            for (IController controller : controllers) {
                if (controller.isVisible()) {
                    controller.hide();
                } else {
                    controller.show();
                }
            }
        }
        return true;
    }
    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        mController.show();
        return true;
    }

    public void setController() {
        controllers.add(mController);
        mController.setPlayer(mPlayer);
    }

    @Override
    public TextureView getSurface() {
        return mSurface;
    }
}
