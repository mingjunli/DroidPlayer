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

public class MainActivity extends AppCompatActivity {

    private DefaultPlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
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

        mPlayer.attachPlayerView(mPlayerView);

        Uri uri = Uri.parse("http://video.jiecao.fm/8/18/%E5%A4%A7%E5%AD%A6.mp4");
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
