package com.giga.ehospital.reservation.fragment.standard;

import android.view.View;

import com.giga.ehospital.reservation.R;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.BindView;

public abstract class StandardWithTobBarLayoutFragment extends StandardFragment {

    @BindView(R.id.topbar)
    protected QMUITopBarLayout mTopBar;

    @Override
    protected void initTopBar() {
        if (isSpecialTopBar()) {
            // 特殊topbar 特殊处理
            handleSpecialTopBar();
        } else {
            // 正常
            if (canDragBack()) {
                mTopBar.addLeftBackImageButton().setOnClickListener(view -> popBackStack());
            }
            mTopBar.setBackgroundDividerEnabled(topBarHavDivider());
            mTopBar.setTitle(getTopBarTitle());
            if (topBarIsTransparent()) {
                //getResources().getColor(R.color.qmui_config_color_transparent, null)
                mTopBar.setBackgroundColor(getResources().getColor(R.color.qmui_config_color_transparent));
            }
            initTopBarLast();
        }
    }

    @Override
    protected View getTopBar() {
        return mTopBar;
    }
}
