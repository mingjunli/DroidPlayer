package com.droidplayer.lib;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.droidplayer.lib.player.AbstractPlayer;
import com.droidplayer.lib.player.IPlayerListener;
import com.droidplayer.lib.player.IPlayerView;
import com.droidplayer.lib.player.PlaybackException;
import com.droidplayer.lib.util.DroidUtils;
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
 * Created by mingjun on 16/10/8.
 */

public class DroidPlayer extends AbstractPlayer {

    private static final String TAG = "DroidPlayer";

    private SimpleExoPlayer mRealPlayer;
    private Handler mHandler;

    private Context mContext;
    private MediaSource mMediaSource;
    private IPlayerView mPlayerView;

    private int mPlayerState;

    public DroidPlayer(Context context) {
        this.mContext = context;

        mHandler = new Handler();
        mRealPlayer = ExoPlayerFactory.newSimpleInstance(mContext,
                new DefaultTrackSelector(mHandler),
                new DefaultLoadControl(),
                null, true);

        mPlayerState = mRealPlayer.getPlaybackState();
        Log.d(TAG, "init state:" + mPlayerState);

        mRealPlayer.addListener(new ExoPlayer.EventListener() {

            @Override
            public void onLoadingChanged(boolean isLoading) {
                Log.d(TAG, "isLoading:" + isLoading + ", state:" + mRealPlayer.getPlaybackState());
                for (IPlayerListener listener : listeners) {
                    listener.onLoadingChanged(isLoading);
                }
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                Log.d(TAG, "onPlayerStateChanged:" + playWhenReady + ", state:" + playbackState);
                int preState = mPlayerState;
                mPlayerState = playbackState;

                // state from IDLE to BUFFERING / READY means prepared.
                if (preState == ExoPlayer.STATE_IDLE
                        && (playbackState == ExoPlayer.STATE_BUFFERING || playbackState == ExoPlayer.STATE_READY)) {
                    start();

                    for (IPlayerListener listener : listeners) {
                        Log.d(TAG, "listener:" + listener);
                        listener.onPrepared();
                    }
                }

                // completed
                if (playbackState == ExoPlayer.STATE_ENDED) {
                    for (IPlayerListener listener : listeners) {
                        listener.onCompleted();
                    }
                }

                for (IPlayerListener listener : listeners) {
                    listener.onPlayerStateChanged(playWhenReady, playbackState);
                }
            }

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                for (IPlayerListener listener : listeners) {
                    listener.onPlayerError(new PlaybackException());
                }
            }

            @Override
            public void onPositionDiscontinuity() {
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
        if (!DroidUtils.checkNotNull(mMediaSource)) {
            throw new IllegalStateException("Should set data source first!!!");
        }

        if (!DroidUtils.checkNotNull(mPlayerView)) {
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
        return mRealPlayer.getPlayWhenReady() && mRealPlayer.getPlaybackState() != ExoPlayer.STATE_ENDED;
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
