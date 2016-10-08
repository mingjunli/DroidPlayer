package com.droidplayer.lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.droidplayer.lib.player.IController;

/**
 * Created by mingjun on 16/10/8.
 */

public abstract class AbstractController extends FrameLayout implements IController {

    public AbstractController(Context context) {
        super(context);
    }

    public AbstractController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    @Override
    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(GONE);
    }
}
