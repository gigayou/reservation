package com.giga.ehospital.reservation.fragment.about;

import android.widget.TextView;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;

import butterknife.BindView;

public class GratitudeFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;

    @Override
    protected String getTopBarTitle() {
        return "致谢";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gratitude;
    }

    @Override
    protected void initLastCustom() {
        initCopyRight(mCopyrightTextView);
    }

}