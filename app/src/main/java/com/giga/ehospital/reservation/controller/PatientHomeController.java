package com.giga.ehospital.reservation.controller;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.inter.ControllerClickHandler;
import com.giga.ehospital.reservation.helper.DialogHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.tmall.ultraviewpager.UltraViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PatientHomeController extends QMUIWindowInsetLayout {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.controller_pager)
    ViewPager mViewPager;
    @BindView(R.id.controller_tabs)
    QMUITabSegment mTabSegment;
    @BindView(R.id.ultraview_pager)
    UltraViewPager mHospitalActivityUltraViewPager;

    private Context mContext;
    private ControllerClickHandler mHandler;
    private DialogHelper mDialogHelper;

    public PatientHomeController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mContext = context;
        this.mHandler = mHandler;
        LayoutInflater.from(context).inflate(R.layout.controller_patient_home, this);
        ButterKnife.bind(this);
        init();
    }

    @OnClick({R.id.hospital_manager_linearLayout, R.id.hadmin_manager_linearLayout,
            R.id.db_manager_linearLayout, R.id.system_setting_linearLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hospital_manager_linearLayout:
                break;
            case R.id.hadmin_manager_linearLayout:
                break;
            case R.id.db_manager_linearLayout:
                break;
            case R.id.system_setting_linearLayout:
                break;
        }
    }

    protected void init() {
        initTopBar();
        initRefreshLayout();
    }

    protected void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.setTitle("首页");
    }

    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }
}
