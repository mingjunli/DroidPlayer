package com.droidplayer.lib.scale;

import android.support.annotation.IntDef;

/**
 * Created by mingjun on 16/10/8.
 */

public interface ScaleType {

    public int FIT_XY = 0;
    public int CENTER_CROP = 1;
    public int SCALE_16_9 = 2;
    public int SCALE_4_3 = 3;
    public int CENTER_INSIDE = 4;

    @IntDef({
            FIT_XY,
            CENTER_CROP,
            SCALE_16_9,
            SCALE_4_3,
            CENTER_INSIDE
    })
    @interface ScaleTypeDef {

    }
}
