package com.anly.droidplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.anly.droidplayer.R;
import com.anly.droidplayer.Utils;
import com.anly.droidplayer.player.IPlayerListener;
import com.anly.droidplayer.player.IMediaPlayer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;

/**
 * Created by mingjun on 16/9/29.
 */

public abstract class AbstractController extends FrameLayout implements IController {

    private static final int PROGRESS_BAR_MAX = 1000;
    private static final String TAG = "AbstractController";

    private ImageView mPlayBtn;
    private TextView mCurrentTime;
    private TextView mTotalTime;
    private SeekBar mProgress;

    private IMediaPlayer mPlayer;
    private ControllerListener mControllerListener;

    public static final int DEFAULT_SHOW_DURATION_MS = 5000;
    private int showDurationMs = DEFAULT_SHOW_DURATION_MS;
    private boolean dragging;

    public AbstractController(Context context) {
        super(context);
        init();
    }

    public AbstractController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbstractController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mControllerListener = new ControllerListener();

        LayoutInflater.from(getContext()).inflate(getLayoutResId(), this);

        mPlayBtn = (ImageView) findViewById(R.id.play);
        mPlayBtn.setOnClickListener(mControllerListener);

        mCurrentTime = (TextView) findViewById(R.id.current_time);
        mTotalTime = (TextView) findViewById(R.id.total_time);

        mProgress = (SeekBar) findViewById(R.id.play_progress);
        mProgress.setMax(PROGRESS_BAR_MAX);
        mProgress.setOnSeekBarChangeListener(mControllerListener);
    }

    @Override
    public void setPlayer(IMediaPlayer player) {
        Log.d(TAG, "setPlayer");
        this.mPlayer = player;
        mPlayer.addListener(mControllerListener);

        updatePlayPauseButton();
        updateProgress();
    }

    protected class ControllerListener implements IPlayerListener,
            View.OnClickListener,
            SeekBar.OnSeekBarChangeListener {

        // Play/Pause Btn
        @Override
        public void onClick(View v) {
            if (mPlayBtn == v) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                }
                else {
                    mPlayer.start();
                }
            }
        }

        // Seek bar
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mCurrentTime.setText(Utils.stringForTime(positionValue(progress)));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            removeCallbacks(hideAction);
            dragging = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            dragging = false;
            mPlayer.seekTo(positionValue(seekBar.getProgress()));
            hideDeferred();
        }

        // ExoPlayer
        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.d(TAG, "onLoadingChanged isLoading:" + isLoading);
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.d(TAG, "onPlayerStateChanged playWhenReady:" + playWhenReady + ", playbackState:" + playbackState);
            updatePlayPauseButton();
            updateProgress();
        }

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            Log.d(TAG, "onTimelineChanged timeline:" + timeline);
            updateProgress();
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.d(TAG, "onPlayerError error:" + error);
        }

        @Override
        public void onPositionDiscontinuity() {
            Log.d(TAG, "onPositionDiscontinuity");
            updateProgress();
        }

        @Override
        public void onBufferUpdated() {

        }
    }

    /**
     * Returns whether the controller is currently visible.
     */
    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    public void show() {
        show(showDurationMs);
    }

    /**
     * Shows the controller for the {@code durationMs}. If {@code durationMs} is 0 the controller is
     * shown until {@link #hide()} is called.
     *
     * @param durationMs The duration in milliseconds.
     */
    public void show(int durationMs) {
        setVisibility(VISIBLE);
        showDurationMs = durationMs;
        hideDeferred();
    }

    /**
     * Hides the controller.
     */
    public void hide() {
        setVisibility(GONE);
        removeCallbacks(updateProgressAction);
        removeCallbacks(hideAction);
    }

    private void hideDeferred() {
        removeCallbacks(hideAction);
        if (showDurationMs > 0) {
            postDelayed(hideAction, showDurationMs);
        }
    }

    private void updateProgress() {
        long duration = mPlayer == null ? 0 : mPlayer.getDuration();
        long position = mPlayer == null ? 0 : mPlayer.getCurrentPosition();

        mTotalTime.setText(Utils.stringForTime(duration));

        if (!dragging) {
            mCurrentTime.setText(Utils.stringForTime(position));
        }

        if (!dragging) {
            mProgress.setProgress(progressBarValue(position));
        }

        long bufferedPosition = mPlayer == null ? 0 : mPlayer.getBufferedPosition();
        Log.d(TAG, "updateProgress, bufferedPosition:" + bufferedPosition);

        mProgress.setSecondaryProgress(progressBarValue(bufferedPosition));

        // Remove scheduled updates.
        removeCallbacks(updateProgressAction);

        // Schedule an update if necessary.
        int playbackState = mPlayer == null ? ExoPlayer.STATE_IDLE : mPlayer.getPlaybackState();
        if (playbackState != ExoPlayer.STATE_IDLE && playbackState != ExoPlayer.STATE_ENDED) {
            long delayMs;
            if (mPlayer.isPlaying() && playbackState == ExoPlayer.STATE_READY) {
                delayMs = 1000 - (position % 1000);
                if (delayMs < 200) {
                    delayMs += 1000;
                }
            } else {
                delayMs = 1000;
            }
            postDelayed(updateProgressAction, delayMs);
        }
    }

    private void updatePlayPauseButton() {
        boolean playing = mPlayer != null && mPlayer.isPlaying();
        mPlayBtn.setImageResource(playing ? R.drawable.ic_pause : R.drawable.ic_play);
    }

    private int progressBarValue(long position) {
        long duration = mPlayer == null ? C.TIME_UNSET : mPlayer.getDuration();
        return duration == C.TIME_UNSET || duration == 0 ? 0
                : (int) ((position * PROGRESS_BAR_MAX) / duration);
    }

    private long positionValue(int progress) {
        long duration = mPlayer == null ? C.TIME_UNSET : mPlayer.getDuration();
        return duration == C.TIME_UNSET ? 0 : ((duration * progress) / PROGRESS_BAR_MAX);
    }

    private final Runnable updateProgressAction = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    private final Runnable hideAction = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    public abstract int getLayoutResId();
}
