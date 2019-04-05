package com.giga.ehospital.reservation.helper;

import android.content.Context;
import android.view.View;

import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class QMUILinearLayoutHelper {

    private static final float mShadowAlpha = 1.0f;
    private static final int mShadowElevationDp = 10;
    private static final int mRadius = 15;

    private Context mContext;

    // 默认
    public void init(View v) {
        ((QMUILinearLayout)v).setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, mRadius),
                QMUIDisplayHelper.dp2px(mContext, mShadowElevationDp), mShadowAlpha);
    }

    // 自动参数
    public void init(View v,float alpha,int elevation,int radius) {
        ((QMUILinearLayout)v).setRadiusAndShadow(QMUIDisplayHelper.dp2px(mContext, radius),
                QMUIDisplayHelper.dp2px(mContext, elevation), alpha);
    }

    public QMUILinearLayoutHelper(Context mContext) {
        this.mContext = mContext;
    }
}
