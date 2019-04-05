package com.giga.ehospital.reservation.fragment.sysadmin;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindString;
import butterknife.BindView;

public class HosManageFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.hos_manage_recyclerview)
    RecyclerView mHosManageRecyclerView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindString(R.string.fragment_hosadmin_manage_title)
    String title;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sysadmin_hos_manage;
    }

    @Override
    protected void initLastCustom() {

    }
}
