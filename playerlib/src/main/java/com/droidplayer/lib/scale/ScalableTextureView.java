package com.droidplayer.lib.scale;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;

/**
 * Created by mingjun on 16/10/8.
 */

public class ScalableTextureView extends TextureView {

    private int mScaleType;

    private int mVideoWidth;
    private int mVideoHeight;

    public ScalableTextureView(Context context) {
        super(context);
    }

    public ScalableTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScalableTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScaleType(@ScaleType.ScaleTypeDef int scaleType) {
        this.mScaleType = scaleType;
    }

    public void setVideoSize(int videoWidth, int videoHeight) {
        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int viewRotation = (int) getRotation();
        // 如果判断成立，则说明显示的TextureView和本身的位置是有90度的旋转的，所以需要交换宽高参数。
        if (viewRotation == 90 || viewRotation == 270) {
            int tempMeasureSpec = widthMeasureSpec;
            widthMeasureSpec = heightMeasureSpec;
            heightMeasureSpec = tempMeasureSpec;
        }

        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);

        if (mScaleType == ScaleType.FIT_XY) {
            width = widthMeasureSpec;
            height = heightMeasureSpec;
        }
        else if (mVideoWidth > 0 && mVideoHeight > 0) {

            int widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);

            if (widthSpecMode == View.MeasureSpec.AT_MOST
                    && heightSpecMode == View.MeasureSpec.AT_MOST) {

                float specAspectRatio = (float) widthSpecSize
                        / (float) heightSpecSize;
                float displayAspectRatio;

                switch (mScaleType) {
                    case ScaleType.SCALE_16_9:
                        displayAspectRatio = 16.0f / 9.0f;
                        if (viewRotation == 90
                                || viewRotation == 270) {
                            displayAspectRatio = 1.0f / displayAspectRatio;
                        }
                        break;

                    case ScaleType.SCALE_4_3:
                        displayAspectRatio = 4.0f / 3.0f;
                        if (viewRotation == 90
                                || viewRotation == 270) {
                            displayAspectRatio = 1.0f / displayAspectRatio;
                        }
                        break;

                    case ScaleType.CENTER_CROP:
                    case ScaleType.CENTER_INSIDE:
                    default:
                        displayAspectRatio = (float) mVideoWidth
                                / (float) mVideoHeight;
                }

                boolean widthBigger = displayAspectRatio > specAspectRatio;

                switch (mScaleType) {
                    case ScaleType.SCALE_16_9:
                    case ScaleType.SCALE_4_3:
                        if (widthBigger) {
                            // too wide, fix width
                            width = widthSpecSize;
                            height = (int) (width / displayAspectRatio);
                        } else {
                            // too high, fix height
                            height = heightSpecSize;
                            width = (int) (height * displayAspectRatio);
                        }
                        break;

                    case ScaleType.CENTER_CROP:
                        if (widthBigger) {
                            // not high enough, fix height
                            height = heightSpecSize;
                            width = (int) (height * displayAspectRatio);
                        } else {
                            // not wide enough, fix width
                            width = widthSpecSize;
                            height = (int) (width / displayAspectRatio);
                        }
                        break;

                    case ScaleType.CENTER_INSIDE:
                    default:
                        if (widthBigger) {
                            // too wide, fix width
                            width = Math.min(mVideoWidth, widthSpecSize);
                            height = (int) (width / displayAspectRatio);
                        } else {
                            // too high, fix height
                            height = Math.min(mVideoHeight, heightSpecSize);
                            width = (int) (height * displayAspectRatio);
                        }
                        break;
                }
            }
        }

        setMeasuredDimension(width, height);
    }
}
