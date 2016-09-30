package com.anly.droidplayer.player;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.anly.droidplayer.Utils;
import com.anly.droidplayer.view.IPlayerView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

/**
 * Created by mingjun on 16/9/30.
 */

public class DroidSimpleExoPlayer extends AbstractPlayer {

    private static final String TAG = "DroidSimpleExoPlayer";

    private SimpleExoPlayer mRealPlayer;
    private Handler mHandler;

    private Context mContext;
    private MediaSource mMediaSource;
    private IPlayerView mPlayerView;

    public DroidSimpleExoPlayer(Context context) {
        mContext = context;
        mHandler = new Handler();
        mRealPlayer = ExoPlayerFactory.newSimpleInstance(mContext,
                new DefaultTrackSelector(mHandler),
                new DefaultLoadControl(),
                null, true);
        mRealPlayer.setPlayWhenReady(true);
        mRealPlayer.addListener(new ExoPlayer.EventListener() {

            @Override
            public void onLoadingChanged(boolean isLoading) {
                for (IPlayerListener listener : listeners) {
                    listener.onLoadingChanged(isLoading);
                }
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                for (IPlayerListener listener : listeners) {
                    listener.onPlayerStateChanged(playWhenReady, playbackState);
                }
            }

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
                for (IPlayerListener listener : listeners) {
                    listener.onTimelineChanged(timeline, manifest);
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                for (IPlayerListener listener : listeners) {
                    listener.onPlayerError(error);
                }
            }

            @Override
            public void onPositionDiscontinuity() {
                for (IPlayerListener listener : listeners) {
                    listener.onPositionDiscontinuity();
                }
            }
        });
    }

    @Override
    public void setDataSource(Uri uri) throws IllegalArgumentException, IllegalStateException {
        mMediaSource = new ExtractorMediaSource(uri,
                new DefaultDataSourceFactory(mContext, "droidplayer"),
                new DefaultExtractorsFactory(),
                mHandler,
                null);
    }

    @Override
    public void prepare() throws IllegalStateException {
        if (!Utils.checkNotNull(mMediaSource)) {
            throw new IllegalStateException("Should set data source first!!!");
        }

        if (!Utils.checkNotNull(mPlayerView)) {
            throw new IllegalStateException("The IPlayerView can not be null, please call attachPlayerView() first!!!");
        }

        Log.d(TAG, "prepare");

        mRealPlayer.prepare(mMediaSource, false);
    }

    @Override
    public void start() throws IllegalStateException {
        mRealPlayer.setPlayWhenReady(true);
    }

    @Override
    public void pause() throws IllegalStateException {
        mRealPlayer.setPlayWhenReady(false);
    }

    @Override
    public void stop() throws IllegalStateException {

    }

    @Override
    public void seekTo(long position) throws IllegalStateException {
        mRealPlayer.seekTo(position);
    }

    @Override
    public long getCurrentPosition() {
        return mRealPlayer.getCurrentPosition();
    }

    @Override
    public long getBufferedPosition() {
        return mRealPlayer.getBufferedPosition();
    }

    @Override
    public int getPlaybackState() {
        return mRealPlayer.getPlaybackState();
    }

    @Override
    public long getDuration() {
        return mRealPlayer.getDuration();
    }

    @Override
    public void release() {
        mRealPlayer.release();
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean isPlaying() {
        return mRealPlayer.getPlayWhenReady();
    }

    @Override
    public void attachPlayerView(IPlayerView playerView) {
        mPlayerView = playerView;

        if (playerView != null) {
            playerView.setPlayer(this);
            mRealPlayer.setVideoTextureView(playerView.getSurface());
        }
    }
}
