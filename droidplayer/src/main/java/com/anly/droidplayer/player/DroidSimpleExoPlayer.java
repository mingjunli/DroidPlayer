package com.anly.droidplayer.player;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.TextureView;

import com.anly.droidplayer.Utils;
import com.anly.droidplayer.view.IPlayerView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
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
    private TextureView mSurface;

    public DroidSimpleExoPlayer(Context context) {
        mContext = context;
        mHandler = new Handler();
        mRealPlayer = ExoPlayerFactory.newSimpleInstance(mContext,
                new DefaultTrackSelector(mHandler),
                new DefaultLoadControl(),
                null, true);
        mRealPlayer.setPlayWhenReady(true);
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

        if (!Utils.checkNotNull(mSurface)) {
            throw new IllegalStateException("The IPlayerView can not be null, please call setPlayerView first!!!");
        }

        Log.d(TAG, "prepare");

        mRealPlayer.prepare(mMediaSource, false);
    }

    @Override
    public void start() throws IllegalStateException {

    }

    @Override
    public void pause() throws IllegalStateException {

    }

    @Override
    public void stop() throws IllegalStateException {

    }

    @Override
    public void seekTo(long var1) throws IllegalStateException {

    }

    @Override
    public long getCurrentPosition() {
        return 0;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public void release() {
        mRealPlayer.release();
    }

    @Override
    public void reset() {

    }

    @Override
    public void setPlayerView(IPlayerView playerView) {
        mSurface = playerView.getSurface();
        mRealPlayer.setVideoTextureView(mSurface);
    }
}
