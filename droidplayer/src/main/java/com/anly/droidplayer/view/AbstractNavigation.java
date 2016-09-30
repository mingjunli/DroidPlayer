package com.anly.droidplayer.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.anly.droidplayer.R;

/**
 * Created by mingjun on 16/9/30.
 */

public abstract class AbstractNavigation extends FrameLayout implements INavigation {

    private ImageView mBackBtn;

    public AbstractNavigation(Context context) {
        super(context);
        init();
    }

    public AbstractNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AbstractNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(getLayoutResId(), this);

        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackClicked();
            }
        });
    }

    public void onBackClicked() {
        if (getContext() instanceof Activity) {
            ((Activity)getContext()).onBackPressed();
        }
    }

    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }

    public abstract int getLayoutResId();
}
