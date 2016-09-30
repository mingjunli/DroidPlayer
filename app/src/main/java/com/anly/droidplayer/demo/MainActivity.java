package com.anly.droidplayer.demo;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anly.droidplayer.PlayerFactory;
import com.anly.droidplayer.player.IMediaPlayer;
import com.anly.droidplayer.view.DefaultPlayerView;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private DefaultPlayerView mPlayerView;

    private SimpleExoPlayer player;
    private Handler mainHandler;

    private boolean shouldAutoPlay = true;
    private boolean shouldRestorePosition;
    private int playerWindow;
    private long playerPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainHandler = new Handler();
        mPlayerView = (DefaultPlayerView) findViewById(R.id.player_view);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private IMediaPlayer mPlayer;
    private void initializePlayer() {
        if (mPlayer == null) {
            mPlayer = PlayerFactory.newExoPlayerInstance(this);
        }

        mPlayer.setPlayerView(mPlayerView);

        Uri uri = Uri.parse("http://source.hotbody.cn/Vy9qmQU8-QTZG-nx2o-PXZX-luKK9xGLDwga.mp4");
        mPlayer.setDataSource(uri);

        mPlayer.prepare();
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
