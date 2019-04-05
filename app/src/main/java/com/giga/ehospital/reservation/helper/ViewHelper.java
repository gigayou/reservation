package com.giga.ehospital.reservation.helper;

import android.view.View;

public class ViewHelper {

    /**
     * 锁定所有视图不可选定
     * @param views
     */
    public void lockView(View... views) {
        for (View view : views) {
            view.setClickable(false);
        }
    }

    /**
     * 解锁视图
     * @param views
     */
    public void unlockView(View... views) {
        for (View view : views) {
            view.setClickable(true);
        }
    }
}
