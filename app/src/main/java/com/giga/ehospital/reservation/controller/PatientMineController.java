package com.giga.ehospital.reservation.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.inter.ControllerClickHandler;
import com.giga.ehospital.reservation.fragment.about.AboutFragment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientMineController extends QMUIWindowInsetLayout {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
//    @BindView(R.id.groupListView)
//    QMUIGroupListView mGroupListView;
//    @BindView(R.id.QMUILinearLayout)
//    QMUILinearLayout mQMUILinearLayout;

    private Context mContext;
    private ControllerClickHandler mHandler;

    public PatientMineController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mContext = context;
        this.mHandler = mHandler;
        LayoutInflater.from(mContext).inflate(R.layout.controller_patient_mine, this);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        initTopBar();
        initRefreshLayout();
    }

    private void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.setTitle(getResources().getString(R.string.mine));
        mTopBar.addRightImageButton(R.mipmap.ic_setting, R.id.topbar_right_setting_button)
                .setOnClickListener(view -> mHandler.startFragment(new AboutFragment()));
    }

    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }
}
