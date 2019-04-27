package com.giga.ehospital.reservation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.activity.BaseFragmentActvity;
import com.giga.ehospital.reservation.base.fragment.BaseFragment;
import com.giga.ehospital.reservation.fragment.home.DoctorHomeFragment;
import com.giga.ehospital.reservation.fragment.home.HosAdminHomeFragment;
import com.giga.ehospital.reservation.fragment.home.PatientHomeFragment;
import com.giga.ehospital.reservation.fragment.home.SysAdminHomeFragment;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends BaseFragmentActvity {

    private long exitTime = 0;
    private int roleId = 0;
    private String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiveBundle();
        if (savedInstanceState == null) {
            BaseFragment fragment = getFirstFragment(roleId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NormalContainer.container.put(NormalContainer.SELECTED_ACTIVITY,this);
    }

    private void receiveBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("message");
        roleId = bundle.getInt("roleId");
        userId = bundle.getString("userId");
    }

    @Override
    protected int getContextViewId() {
        return R.id.home_container;
    }

    /**
     * 返回第一次加载当前activity时候所要加载的fragment
     *
     * @return BaseFragment impl
     */
    protected BaseFragment getFirstFragment(int roleId) {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        switch (roleId) {
            case -1:
                PatientHomeFragment patientHomeFragment = new PatientHomeFragment();
                patientHomeFragment.setArguments(bundle);
                return patientHomeFragment;
            case 1:
                SysAdminHomeFragment sysAdminHomeFragment = new SysAdminHomeFragment();
                sysAdminHomeFragment.setArguments(bundle);
                return sysAdminHomeFragment;
            case 2:
                HosAdminHomeFragment hosAdminHomeFragment = new HosAdminHomeFragment();
                hosAdminHomeFragment.setArguments(bundle);
                return hosAdminHomeFragment;
            case 3:
                DoctorHomeFragment doctorHomeFragment = new DoctorHomeFragment();
                doctorHomeFragment.setArguments(bundle);
                return doctorHomeFragment;
            default:
                return null;
        }
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
