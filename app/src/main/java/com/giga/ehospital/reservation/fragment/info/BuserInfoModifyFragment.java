package com.giga.ehospital.reservation.fragment.info;

import android.app.ProgressDialog;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.login.LoginFragment;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.sysamdin.BuserDataManager;
import com.giga.ehospital.reservation.model.system.Buser;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;

import org.apache.commons.lang3.StringUtils;


import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BuserInfoModifyFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.et_buser_id)
    EditText etBuserId;
    @BindView(R.id.et_buser_name)
    EditText etBuserName;
    @BindView(R.id.et_buser_loginPwd)
    EditText etBuserLoginPwd;
    @BindView(R.id.et_buser_oldPwd)
    EditText etBuserOldPwd;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindString(R.string.self_info_fragment_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private Buser mBuser;

    private BuserDataManager buserDataManager;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buser_info_modify;
    }

    @Override
    protected void initLastCustom() {
        btnSave.setText("修改密码");
        if (buserDataManager == null)
            buserDataManager = new BuserDataManager();
        initData();
    }

    private void initData() {
        etBuserId.setEnabled(false);
        etBuserName.setEnabled(false);

        mBuser = NormalContainer.get(NormalContainer.BUSER);
        etBuserId.setText(mBuser.getLoginId());
        etBuserName.setText(mBuser.getUserName());
    }

    private void changePwd(String loginId, String oldPwd, String newPwd) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        buserDataManager.changePwd(loginId, oldPwd, newPwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressDialog.setMessage(LOADING_MESSAGE);
                    progressDialog.show();
                })
                .doOnComplete(() -> progressDialog.dismiss())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        ApiResponse response = GsonParser.fromJSONObject(s, ApiResponse.class);
                        if (response.success()) {
//                            getActivity().onBackPressed();
                            Toasty.success(getContext(), "修改密码成功，请重新登录", Toasty.LENGTH_SHORT, true).show();
                            startFragment(new LoginFragment());
                        } else {
                            Toasty.error(getContext(),response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    @OnClick({R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                String loginPwd = etBuserLoginPwd.getText().toString().trim();
                if (StringUtils.isBlank(loginPwd)) {
                    Toasty.warning(getContext(), "请输入登录密码", Toasty.LENGTH_SHORT, true).show();
                    return;
                } else {
                    String loginId = mBuser.getLoginId();
                    String oldPwd = etBuserOldPwd.getText().toString().trim();
                    String newPwd = etBuserLoginPwd.getText().toString().trim();

                    if (StringUtils.isBlank(oldPwd)) {
                        Toasty.warning(getContext(), "请输入旧密码", Toasty.LENGTH_SHORT, true).show();
                        return;
                    }
                    if (StringUtils.isBlank(newPwd)) {
                        Toasty.warning(getContext(), "请输入新密码", Toasty.LENGTH_SHORT, true).show();
                        return;
                    }

                    changePwd(loginId, oldPwd, newPwd);
                }
                break;
        }
    }
}
