package com.giga.ehospital.reservation.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.activity.BaseFragmentActvity;
import com.giga.ehospital.reservation.base.fragment.BaseFragment;
import com.giga.ehospital.reservation.fragment.login.LoginFragment;
import com.giga.ehospital.reservation.fragment.login.RegisterFragment;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseFragmentActvity {

    private static final String KEY_CURRENT_TAG = "CurrentTag";
    private static final String KEY_TAGS = "FragmentTags";
    private static final String KEY_CLASS_NAMES = "FragmentClassNames";

    private ArrayList<String> fragmentTags = new ArrayList<>();
    private ArrayList<String> fragmentClassNames = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private FragmentManager mFragmentManager;

    private String currentTag;

    private long exitTime = 0;

    @Override
    protected int getContextViewId() {
        return R.id.login_container;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            BaseFragment fragment = getFirstFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NormalContainer.container.put(NormalContainer.SELECTED_ACTIVITY,this);
    }

    protected BaseFragment getFirstFragment() {
        return new LoginFragment();
    }

    //    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        ButterKnife.bind(this);
//        mFragmentManager = getSupportFragmentManager();
//        if (savedInstanceState != null) {
//            restoreFragments(savedInstanceState);
//        } else {
//            initFragments();
//        }
//    }

    /**
     * 初始化登陆界面中会用到的ifragment
     */
    private void initFragments() {
        addFragment(new LoginFragment(), "LoginFragment");
        addFragment(new RegisterFragment(), "RegisterFragment");

        currentTag = "LoginFragment";
        switchFragment(currentTag);
    }

    /**
     * 添加fragment
     *
     * @param fragment Fragment对象
     * @param tag Fragment对象标签
     */
    protected void addFragment(@NonNull Fragment fragment, @NonNull String tag) {
        fragments.add(fragment);
        fragmentTags.add(tag);
        fragmentClassNames.add(fragment.getClass().getName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CURRENT_TAG, currentTag);
        outState.putStringArrayList(KEY_TAGS, fragmentTags);
        outState.putStringArrayList(KEY_CLASS_NAMES, fragmentClassNames);
    }

    /**
     * 切换fragment
     *
     * @param tag Fragment对象标签
     */
    public void switchFragment(String tag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (fragmentTags.indexOf(tag) < 0) {
            return;
        }
        Fragment showFragment = fragments.get(fragmentTags.indexOf(tag));
        Fragment currentFragment = mFragmentManager.findFragmentByTag(currentTag);
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (showFragment.isAdded()) {
            transaction.show(showFragment);
        } else {
            transaction.add(R.id.login_container, showFragment, tag);
            transaction.show(showFragment);
        }
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commitAllowingStateLoss();
        currentTag = tag;
    }

    /**
     * Fragment复位
     *
     * @param savedInstanceState
     */
    private void restoreFragments(@NonNull Bundle savedInstanceState) {
        currentTag = savedInstanceState.getString(KEY_CURRENT_TAG, "");
        fragments.clear();
        fragmentTags.addAll(savedInstanceState.getStringArrayList(KEY_TAGS));
        fragmentClassNames.clear();
        fragmentClassNames.addAll(savedInstanceState.getStringArrayList(KEY_CLASS_NAMES));
//        Logger.d(TAG, fragmentTags.toString());
//        Logger.d(TAG, fragmentClassNames.toString());
//        Logger.d(TAG, "CurrentTag = " + currentTag);
        for(int i = 0; i < fragmentTags.size(); i++) {
            Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTags.get(i));
            if (fragment == null) {
                try {
                    Class<?> fragmentClass = Class.forName(fragmentClassNames.get(i));
                    Object obj = fragmentClass.newInstance();
                    if (obj instanceof Fragment) {
                        fragment = (Fragment) obj;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fragment != null) {
                fragments.add(fragment);
                if(fragment.isAdded()) {
                    mFragmentManager.beginTransaction()
                            .hide(fragment)
                            .commitAllowingStateLoss();
                }
            }
        }
        switchFragment(currentTag);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //2秒内按两次退出
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toasty.info(this, getString(R.string.press_again_exit),
                        Toasty.LENGTH_SHORT, true).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
