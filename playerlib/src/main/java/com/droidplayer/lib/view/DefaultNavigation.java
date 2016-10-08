package com.droidplayer.lib.view;

import android.content.Context;
import android.util.AttributeSet;

import com.droidplayer.lib.R;

/**
 * Created by mingjun on 16/9/30.
 */

public class DefaultNavigation extends AbstractNavigation {

    public DefaultNavigation(Context context) {
        super(context);
    }

    public DefaultNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.default_navigation_view;
    }
}
