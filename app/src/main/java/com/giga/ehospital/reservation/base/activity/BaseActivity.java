package com.giga.ehospital.reservation.base.activity;

import com.giga.ehospital.reservation.base.manager.QDUpgradeManager;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import static com.giga.ehospital.ReservationApplication.getContext;


public class BaseActivity extends QMUIActivity {

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }

    @Override
    public void onResume() {
        super.onResume();
        QDUpgradeManager.getInstance(getContext()).runUpgradeTipTaskIfExist(this);
    }
}
