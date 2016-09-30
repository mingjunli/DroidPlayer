package com.anly.droidplayer.view;

import android.content.Context;
import android.util.AttributeSet;

import com.anly.droidplayer.R;

/**
 * Created by mingjun on 16/9/29.
 */

public class DefaultController extends AbstractVideoController {

    public DefaultController(Context context) {
        super(context);
    }

    public DefaultController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.default_controller_view;
    }
}
