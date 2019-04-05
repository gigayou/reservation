package com.giga.ehospital.reservation.fragment.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giga.ehospital.reservation.R;
import com.linxiao.framework.fragment.BaseFragment;
import com.qmuiteam.qmui.layout.QMUIButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment {

    @BindView(R.id.btn_back)
    QMUIButton btnBack;

    @Override
    protected void onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(R.layout.fragment_register, container);
        ButterKnife.bind(this, getContentView());
    }

    @OnClick({R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                currentFragmentPopBack();
                break;
            default:
                break;
        }
    }

    /**
     * 退栈到上一个fragment
     */
    private void currentFragmentPopBack() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }
}
