package com.giga.ehospital.reservation.fragment.sysadmin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.sysamdin.BuserDataManager;
import com.giga.ehospital.reservation.model.system.Buser;
import com.giga.ehospital.reservation.util.ConfigUtil;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HosAdminModifyFragment  extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.et_hosadmin_loginid)
    EditText etHosadminLoginId;
    @BindView(R.id.et_hosadmin_username)
    EditText etHosadminUsername;
    @BindView(R.id.et_hosadmin_loginpwd)
    EditText etHosadminLoginpwd;
    @BindString(R.string.fragment_hosadmin_modify_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private BuserDataManager buserDataManager;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sysadmin_hosadmin_add;
    }

    @Override
    protected void initLastCustom() {
        if (buserDataManager == null) {
            buserDataManager = new BuserDataManager();
        }
        initData();
    }

    @OnClick({R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                pushHosAdminInfo();
                break;
            default:
                break;
        }
    }

    private void initData() {
        Bundle arguments = getArguments();
        Buser hosadmin = (Buser) arguments.get("hosadmin");

        String loginId = hosadmin.getLoginId();
        String userName = hosadmin.getUserName();
        String loginPwd = hosadmin.getLoginPwd();

        etHosadminLoginId.setText(loginId);
        etHosadminLoginpwd.setText(loginPwd);
        etHosadminUsername.setText(userName);
    }

    private void pushHosAdminInfo() {
        String hosAdminLoginId = etHosadminLoginId.getText().toString().trim();
        String hosAdminUserName = etHosadminUsername.getText().toString().trim();
        String hosAdminLoginPwd = etHosadminLoginpwd.getText().toString().trim();

        Buser buser = new Buser();
        buser.setLoginId(hosAdminLoginId);
        buser.setUserName(hosAdminUserName);
        buser.setLoginPwd(hosAdminLoginPwd);
        buser.setRoleId(ConfigUtil.ROLE_HOS_ADMIN);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        buserDataManager.update(buser)
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
                            getActivity().onBackPressed();
                            Toasty.success(getContext(), "修改成功", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }
}
