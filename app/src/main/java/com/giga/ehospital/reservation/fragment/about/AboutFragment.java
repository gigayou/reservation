package com.giga.ehospital.reservation.fragment.about;

import android.widget.TextView;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindString;
import butterknife.BindView;

public class AboutFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.version_textview)
    TextView versionTextView;
    @BindView(R.id.about_list_groupList)
    QMUIGroupListView aboutListQMUIGroupListView;
    @BindView(R.id.copyright_textview)
    TextView mCopyrightTextView;
    @BindString(R.string.fragment_about_title)
    String title;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initLastCustom() {
        initVersion();
        initGroupList();
        initCopyRight(mCopyrightTextView);
    }

    private void initVersion() {
        versionTextView.setText("Version 1.0.0");
    }

    private void initGroupList() {

        QMUICommonListItemView gratitudeItem = aboutListQMUIGroupListView.createItemView(getResources().getString(R.string.about_gratitude));
        gratitudeItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView declarationItem = aboutListQMUIGroupListView.createItemView(getResources().getString(R.string.about_declaration));
        declarationItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView licenseAgreementItem = aboutListQMUIGroupListView.createItemView(getResources().getString(R.string.about_license_agreement));
        licenseAgreementItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(getContext())
                .addItemView(gratitudeItem, v -> startFragment(new GratitudeFragment()))
                .addItemView(declarationItem, v -> startFragment(new DeclarationFragment()))
                .addItemView(licenseAgreementItem, v -> startFragment(new LicenseAgreementFragment()))
                .addTo(aboutListQMUIGroupListView);
    }
}
