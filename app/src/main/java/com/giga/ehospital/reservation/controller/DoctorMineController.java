package com.giga.ehospital.reservation.controller;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.inter.ControllerClickHandler;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.about.AboutFragment;
import com.giga.ehospital.reservation.fragment.info.BuserInfoModifyFragment;
import com.giga.ehospital.reservation.fragment.login.LoginFragment;
import com.giga.ehospital.reservation.model.system.Buser;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorMineController extends QMUIWindowInsetLayout {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;
    @BindView(R.id.username_textview)
    TextView tvUsername;
    @BindView(R.id.llt_self_account)
    LinearLayout llt1;
    @BindView(R.id.head_imageview)
    CircleImageView civHeadImage;

    private Context mContext;
    private ControllerClickHandler mHandler;

    // QMUI dialog style
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    public DoctorMineController(Context context, ControllerClickHandler mHandler) {
        super(context);
        this.mContext = context;
        this.mHandler = mHandler;
        LayoutInflater.from(mContext).inflate(R.layout.controller_doctor_mine, this);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        initTopBar();
        initTvUserName();
        initRefreshLayout();
        initGroupListView();
    }

    @OnClick({R.id.llt_self_account, R.id.head_imageview, R.id.username_textview})
    public void onClcik(View v) {
        switch (v.getId()) {
            case R.id.llt_self_account:
                mHandler.startFragment(new BuserInfoModifyFragment());
                break;
            case R.id.head_imageview:
                mHandler.startFragment(new BuserInfoModifyFragment());
                break;
            case R.id.username_textview:
                mHandler.startFragment(new BuserInfoModifyFragment());
                break;
        }
    }

    private void initGroupListView() {
        // logout
        QMUICommonListItemView logoutItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_logout),
                "退出当前账号",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(logoutItem, getOnclickListener())
                .addTo(mGroupListView);
    }

    private View.OnClickListener getOnclickListener() {
        return v -> {
            CharSequence itemViewText = ((QMUICommonListItemView) v).getText();
            if (itemViewText.equals("退出当前账号")) {
                String title = "你确定退出当前账号吗？";
                String content = "我确定退出";
                String cancelMsg = "取消";
                String confirmMsg = "确定";
                showMessageNegativeDialog(title, cancelMsg, confirmMsg);
            }
        };
    }

    private void showMessageNegativeDialog(String title, String cancelMsg, String confirmMsg) {
        new QMUIDialog.MessageDialogBuilder(getContext())
                .setTitle(title)
                .addAction(cancelMsg, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, confirmMsg, QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        mHandler.startFragment(new LoginFragment());
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    private void initTvUserName() {
        Buser buser = NormalContainer.get(NormalContainer.BUSER);
        if (buser == null || buser.getUserName() == null) {
            tvUsername.setText("用户名");
            llt1.setEnabled(false);
            civHeadImage.setEnabled(false);
            tvUsername.setEnabled(false);
        }
        else {
            String userName = buser.getUserName();
            tvUsername.setText(userName);
        }
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
