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
import com.giga.ehospital.reservation.fragment.sysadmin.DBManageFragment;
import com.giga.ehospital.reservation.fragment.sysadmin.HosAdminManagerFragment;
import com.giga.ehospital.reservation.fragment.sysadmin.HosManageFragment;
import com.giga.ehospital.reservation.fragment.sysadmin.SysSettingManager;
import com.giga.ehospital.reservation.helper.DialogHelper;
import com.giga.ehospital.reservation.helper.TipDialogHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.tmall.ultraviewpager.UltraViewPager;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SysAdminHomeController extends QMUIWindowInsetLayout {

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
    @BindString(R.string.wait_please)
    String WAIT_PLEASE_TOAST_TITLE;

    private Context mContext;
    private ControllerClickHandler mHandler;
    private TipDialogHelper mTipDialogHelper;
    private DialogHelper mDialogHelper;

    public SysAdminHomeController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mContext = context;
        this.mHandler = mHandler;
        LayoutInflater.from(context).inflate(R.layout.controller_sys_admin_home, this);
        ButterKnife.bind(this);
        init(context);
    }

    @OnClick({R.id.hospital_manager_linearLayout, R.id.hadmin_manager_linearLayout,
            R.id.db_manager_linearLayout, R.id.system_setting_linearLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hospital_manager_linearLayout:
                mHandler.startFragment(new HosManageFragment());
                break;
            case R.id.hadmin_manager_linearLayout:
                mHandler.startFragment(new HosAdminManagerFragment());
                break;
            case R.id.db_manager_linearLayout:
                mHandler.startFragment(new DBManageFragment());
                break;
            case R.id.system_setting_linearLayout:
//                mHandler.startFragment(new SysSettingManager());
                Toasty.info(getContext(), WAIT_PLEASE_TOAST_TITLE, Toasty.LENGTH_SHORT, true).show();
                break;
        }
    }

    protected void init(Context context) {
        initBasic(context);
        initTopBar();
        initRefreshLayout();
        initUltraViewPager();
    }

    /**
     * 初始化基础组件
     *
     * @param context
     */
    private void initBasic(Context context) {
        mTipDialogHelper = new TipDialogHelper(context);
        mDialogHelper = new DialogHelper(context);
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

    protected void initTopBar() {
        mTopBar.setBackgroundDividerEnabled(false);
        mTopBar.setTitle("首页");
//        use this method needs api level gt 20
//        getResources().getColor(R.color.qmui_config_color_transparent, null)
        mTopBar.setBackgroundColor(getResources().getColor(R.color.qmui_config_color_transparent));
    }

    protected void initRefreshLayout() {
        mPullRefreshLayout.setEnabled(false);
    }
}
