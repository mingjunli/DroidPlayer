package com.anly.droidplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.anly.droidplayer.R;

/**
 * Created by mingjun on 16/9/29.
 */

public abstract class AbstractController extends FrameLayout {

    private ImageView mPlayBtn;
    private TextView mCurrentTime;
    private TextView mTotalTime;
    private SeekBar mProgress;

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
        LayoutInflater.from(getContext()).inflate(getLayoutResId(), this);

        mPlayBtn = (ImageView) findViewById(R.id.play);

        mCurrentTime = (TextView) findViewById(R.id.current_time);
        mTotalTime = (TextView) findViewById(R.id.total_time);

        mProgress = (SeekBar) findViewById(R.id.play_progress);
    }




    public abstract int getLayoutResId();
}
