package com.giga.ehospital.reservation.manager.sysamdin;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindString;
import butterknife.BindView;

public class HosAdminDataManager extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.hosadmin_manage_recyclerview)
    RecyclerView mHosAdminManageRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.fragment_hosadmin_manage_title)
    String title;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;


    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initLastCustom() {

    }
}
