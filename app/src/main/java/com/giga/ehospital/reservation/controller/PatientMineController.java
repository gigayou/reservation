package com.giga.ehospital.reservation.controller;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.base.inter.ControllerClickHandler;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.about.AboutFragment;
import com.giga.ehospital.reservation.fragment.login.LoginFragment;
import com.giga.ehospital.reservation.fragment.patient.PatientManageFragment;
import com.giga.ehospital.reservation.fragment.patient.ReservationHistoryFragment;
import com.giga.ehospital.reservation.fragment.patient.ReservationSucceedFragment;
import com.giga.ehospital.reservation.fragment.patient.ReservationWaitingFragment;
import com.giga.ehospital.reservation.helper.QMUILinearLayoutHelper;
import com.giga.ehospital.reservation.model.User;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.QMUIWindowInsetLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class PatientMineController extends QMUIWindowInsetLayout {

    @BindView(R.id.topbar)
    QMUITopBarLayout mTopBar;
    @BindView(R.id.pull_to_refresh)
    QMUIPullRefreshLayout mPullRefreshLayout;
    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;
    @BindView(R.id.username_textview)
    TextView tvUsername;
    @BindView(R.id.QMUILinearLayout)
    QMUILinearLayout mQMUILinearLayout;
    @BindView(R.id.llt_self_account)
    LinearLayout llt1;
    @BindView(R.id.head_imageview)
    CircleImageView civHeadImage;
    /** 待就诊 | 订单 | 收藏 */
    @BindView(R.id.wait_diagnosis_linearlayout)
    LinearLayout lltWaitDiagnosis;
    @BindView(R.id.iv_wait_diagnosis)
    ImageView ivWaitDiagnosis;
    @BindView(R.id.tv_wait_diagnosis)
    TextView tvWaitDiagnosis;
    @BindView(R.id.order_linearlayout)
    LinearLayout lltOrder;
    @BindView(R.id.iv_order)
    ImageView ivOrder;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.my_collection_linearlayout)
    LinearLayout lltMyCollection;
    @BindView(R.id.iv_my_collection)
    ImageView ivMyCollection;
    @BindView(R.id.tv_my_collection)
    TextView tvMyCollection;
    /** WAIT PLEASE Str */
    @BindString(R.string.wait_please)
    String WAIT_PLEASE;

    private Context mContext;
    private ControllerClickHandler mHandler;
    QMUILinearLayoutHelper mQMUILinearLayoutHelper;

    // QMUI dialog style
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

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
        initQMUILinearLayout();
        initRefreshLayout();
        initGroupListView();
        initTvUserName();
        localUserCheck();
    }

    private void localUserCheck() {
        User user = NormalContainer.get(NormalContainer.USER);
        if (user == null || user.getUserPhone() == null)
            Toasty.warning(getContext(), "同步用户数据失败，请重新登录", Toasty.LENGTH_SHORT, true).show();
    }

    private boolean userCheck() {
        boolean flag = false;
        User user = NormalContainer.get(NormalContainer.USER);
        if (user == null || user.getUserPhone() == null) {
            Toasty.warning(getContext(), "同步用户数据失败，请重新登录", Toasty.LENGTH_SHORT, true).show();
            return flag;
        }
        flag = true;
        return flag;
    }

    @OnClick({R.id.wait_diagnosis_linearlayout, R.id.iv_wait_diagnosis, R.id.tv_wait_diagnosis,
                R.id.order_linearlayout, R.id.iv_order, R.id.tv_order,
                R.id.my_collection_linearlayout, R.id.iv_my_collection, R.id.tv_my_collection})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wait_diagnosis_linearlayout:
                mHandler.startFragment(new ReservationWaitingFragment());
                break;
            case R.id.iv_wait_diagnosis:
                mHandler.startFragment(new ReservationWaitingFragment());
                break;
            case R.id.tv_wait_diagnosis:
                mHandler.startFragment(new ReservationWaitingFragment());
                break;
            case R.id.order_linearlayout:
                Toasty.info(getContext(), WAIT_PLEASE, Toasty.LENGTH_SHORT, true).show();
                break;
            case R.id.iv_order:
                Toasty.info(getContext(), WAIT_PLEASE, Toasty.LENGTH_SHORT, true).show();
                break;
            case R.id.tv_order:
                Toasty.info(getContext(), WAIT_PLEASE, Toasty.LENGTH_SHORT, true).show();
                break;
            case R.id.my_collection_linearlayout:
                Toasty.info(getContext(), WAIT_PLEASE, Toasty.LENGTH_SHORT, true).show();
                break;
            case R.id.iv_my_collection:
                Toasty.info(getContext(), WAIT_PLEASE, Toasty.LENGTH_SHORT, true).show();
                break;
            case R.id.tv_my_collection:
                Toasty.info(getContext(), WAIT_PLEASE, Toasty.LENGTH_SHORT, true).show();
                break;
        }
    }

    private void initTvUserName() {
        User user = NormalContainer.get(NormalContainer.USER);
        if (user == null || user.getUserName() == null) {
            tvUsername.setText("用户名");
            llt1.setEnabled(false);
            civHeadImage.setEnabled(false);
            tvUsername.setEnabled(false);
        }
        else {
            String userName = user.getUserName();
            tvUsername.setText(userName);
        }
    }

    private void initGroupListView() {

        // 常用工具
        QMUICommonListItemView registerRecordItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_history),
                "挂号记录",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView electronicCaseHistory = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_electronic_case),
                "电子病历",
                "验证密码后方可查看",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView electronicPrescriptionItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_tcm),
                "电子处方",
                "验证密码后方可查看",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView finishDiagnosisItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_finish_diagnosis),
                "已就诊",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("常用工具")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(registerRecordItem, getOnclickListener())
                .addItemView(electronicCaseHistory, getOnclickListener())
                .addItemView(electronicPrescriptionItem, getOnclickListener())
                .addItemView(finishDiagnosisItem, getOnclickListener())
                .addTo(mGroupListView);

        //  普通工具
        QMUICommonListItemView myConsultingItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_consulting),
                "我的咨询",
                "可查看咨询的历史对话信息",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUICommonListItemView myMedicalCardsItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_medical_card),
                "我的诊疗卡",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("普通工具")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(myConsultingItem, getOnclickListener())
                .addItemView(myMedicalCardsItem, getOnclickListener())
                .addTo(mGroupListView);

        // logout
        QMUICommonListItemView logoutItem = mGroupListView.createItemView(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_logout),
                "退出当前账号",
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON
        );

        QMUIGroupListView.newSection(getContext())
                .setTitle("账号操作")
                .setLeftIconSize(QMUIDisplayHelper.dp2px(getContext(), 28), ViewGroup.LayoutParams.WRAP_CONTENT)
                .addItemView(logoutItem, getOnclickListener())
                .addTo(mGroupListView);

    }

    private View.OnClickListener getOnclickListener() {
        return v -> {

            CharSequence itemViewText = ((QMUICommonListItemView) v).getText();
            if (itemViewText.equals("挂号记录")) {
                if (userCheck())
                    mHandler.startFragment(new ReservationHistoryFragment());
            } else if (itemViewText.equals("电子病历")) {
                Toasty.info(getContext(), WAIT_PLEASE, Toasty.LENGTH_SHORT, true).show();
            } else if (itemViewText.equals("电子处方")) {
                Toasty.info(getContext(), WAIT_PLEASE, Toasty.LENGTH_SHORT, true).show();
            } else if (itemViewText.equals("已就诊")) {
                if (userCheck())
                    mHandler.startFragment(new ReservationSucceedFragment());
            } else if (itemViewText.equals("我的咨询")) {
                Toasty.info(getContext(), WAIT_PLEASE, Toasty.LENGTH_SHORT, true).show();
            } else if (itemViewText.equals("我的诊疗卡")) {
                if (userCheck())
                    mHandler.startFragment(new PatientManageFragment());
            } else if (itemViewText.equals("退出当前账号")) {
                String title = "你确定退出当前账号吗？";
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
                        NormalContainer.clear();
                        mHandler.startFragment(new LoginFragment());
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    private void initQMUILinearLayout() {
        mQMUILinearLayoutHelper = new QMUILinearLayoutHelper(mContext);
        mQMUILinearLayoutHelper.init(mQMUILinearLayout);
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
