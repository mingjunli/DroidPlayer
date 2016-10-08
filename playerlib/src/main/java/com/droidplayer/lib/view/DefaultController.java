package com.droidplayer.lib.view;

import android.content.Context;
import android.util.AttributeSet;

import com.droidplayer.lib.R;

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
