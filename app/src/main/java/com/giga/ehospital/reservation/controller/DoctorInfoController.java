package com.giga.ehospital.reservation.controller;

import android.content.Context;
import android.view.LayoutInflater;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.inter.ControllerClickHandler;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorInfoController extends QMUIWindowInsetLayout {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
//    @BindView(R.id.QMUILinearLayout)
//    QMUILinearLayout mQMUILinearLayout;

    private Context mContext;
    private ControllerClickHandler mHandler;

    public DoctorInfoController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mContext = context;
        this.mHandler = mHandler;
        LayoutInflater.from(context).inflate(R.layout.controller_doctor_info, this);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        initTopBar();
        initRefreshLayout();
    }

    protected void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.setTitle("消息");
    }

    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }
}
