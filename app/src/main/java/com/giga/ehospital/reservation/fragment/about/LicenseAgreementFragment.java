package com.giga.ehospital.reservation.fragment.about;

import android.widget.TextView;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindView;

public class LicenseAgreementFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;

    @Override
    protected String getTopBarTitle() {
        return "许可协议";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_license_agreement;
    }

    @Override
    protected void initLastCustom() {
        initCopyRight(mCopyrightTextView);
    }

}
