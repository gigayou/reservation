package com.giga.ehospital.reservation.controller;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.inter.ControllerClickHandler;
import com.giga.ehospital.reservation.helper.DialogHelper;
import com.giga.ehospital.reservation.helper.TipDialogHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.tmall.ultraviewpager.UltraViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoctorHomeController extends QMUIWindowInsetLayout {

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
    private TipDialogHelper mTipDialogHelper;
    private DialogHelper mDialogHelper;

    public DoctorHomeController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mContext = context;
        this.mHandler = mHandler;
        LayoutInflater.from(context).inflate(R.layout.controller_doctor_home, this);
        ButterKnife.bind(this);
        init(context);
    }

    @OnClick({R.id.doctor_reserdate_manage_linearLayout, R.id.doctor_selfinfo_manage_linearLayout,
            R.id.doctor_reserinfo_manage_linearLayout, R.id.doctor_answer_linearLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doctor_reserdate_manage_linearLayout:
                break;
            case R.id.doctor_selfinfo_manage_linearLayout:
                break;
            case R.id.doctor_reserinfo_manage_linearLayout:
                break;
            case R.id.doctor_answer_linearLayout:
                break;
        }
    }

    protected void init(Context context) {
        initBasic(context);
        initTopBar();
        initRefreshLayout();
        initUltraViewPager();
    }

    private void initBasic(Context context) {
        mTipDialogHelper = new TipDialogHelper(context);
        mDialogHelper = new DialogHelper(context);
    }

    @SuppressWarnings("all")
    protected void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.setTitle("首页");
        mTopBar.setBackgroundColor(getResources().getColor(R.color.qmui_config_color_transparent, null));
    }

    /**
     * 初始化轮播图
     */
    private void initUltraViewPager() {
        mHospitalActivityUltraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        mHospitalActivityUltraViewPager.setAdapter(mUltraPagerAdapter);
        mHospitalActivityUltraViewPager.setInfiniteLoop(true);
        mHospitalActivityUltraViewPager.setAutoScroll(4000);
    }

    private PagerAdapter mUltraPagerAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View root = LayoutInflater.from(container.getContext()).inflate(R.layout.ultrapager_item, null);
//            if (mHealthArticles != null) {
            ImageView imageView = root.findViewById(R.id.imageview);
            if (position % 3 == 0) {
                Glide.with(getContext()).load(R.mipmap.bg_ulpager_t4).into(imageView);
            } else if (position % 3 == 1) {
                Glide.with(getContext()).load(R.mipmap.bg_ulpager_t2).into(imageView);
            } else {
                Glide.with(getContext()).load(R.mipmap.bg_ulpager_t3).into(imageView);
            }
//            }
            container.addView(root);
            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }
}
