package com.droidplayer.lib.scale;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

import com.droidplayer.lib.R;

/**
 * Created by mingjun on 16/10/8.
 */

public class ScalableTextureView extends TextureView {

    private static final String TAG = "ScalableTextureView";
    private ScalableType mScaleType;

    private int mVideoWidth;
    private int mVideoHeight;

    public ScalableTextureView(Context context) {
        this(context, null);
    }

    public ScalableTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalableTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs == null) {
            return;
        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.scaleStyle, 0, 0);
        if (a == null) {
            return;
        }

        int scaleType = a.getInt(R.styleable.scaleStyle_scalableType, ScalableType.NONE.ordinal());
        mScaleType = ScalableType.values()[scaleType];

        a.recycle();
    }

    public void setScaleType(ScalableType scaleType) {
        this.mScaleType = scaleType;
    }

    public void setVideoSize(int videoWidth, int videoHeight) {
        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;

        scaleVideoSize(videoWidth, videoHeight);
    }

    private void scaleVideoSize(int videoWidth, int videoHeight) {
        if (videoWidth == 0 || videoHeight == 0) {
            return;
        }

        Size viewSize = new Size(getWidth(), getHeight());
        Size videoSize = new Size(videoWidth, videoHeight);
        ScaleManager scaleManager = new ScaleManager(viewSize, videoSize);
        Matrix matrix = scaleManager.getScaleMatrix(mScaleType);
        if (matrix != null) {
            setTransform(matrix);
        }
    }
}
