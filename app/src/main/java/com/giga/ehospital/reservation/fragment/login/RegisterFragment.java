package com.giga.ehospital.reservation.fragment.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.manager.LoginDataManager;
import com.giga.ehospital.reservation.manager.patient.UserDataManager;
import com.giga.ehospital.reservation.model.User;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.fragment.BaseFragment;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;
import com.qmuiteam.qmui.layout.QMUIButton;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisterFragment extends BaseFragment {

    @BindView(R.id.etUserPhone)
    EditText etUserPhone;
    @BindView(R.id.etUserPassword)
    EditText etUserPassword;
    @BindView(R.id.btn_back)
    QMUIButton btnBack;
    @BindView(R.id.btn_register)
    QMUIButton btnRegister;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private Pattern phonePattern;
    private Pattern pwdPattern;

    private LoginDataManager loginDataManager;

    @Override
    protected void onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContentView(R.layout.fragment_register, container);
        ButterKnife.bind(this, getContentView());
        if (loginDataManager == null)
            loginDataManager = new LoginDataManager();
    }

    @OnClick({R.id.btn_register, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_back:
                currentFragmentPopBack();
                break;
            default:
                break;
        }
    }

    private void register() {
        String phone = etUserPhone.getText().toString().trim();
        String password = etUserPassword.getText().toString().trim();

        User user = new User();
        user.setUserPhone(phone);
        user.setUserPwd(password);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        loginDataManager.register(user)
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
                            currentFragmentPopBack();
                            Toasty.success(getContext(), "注册成功，请登录", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    /**
     * 退栈到上一个fragment
     */
    private void currentFragmentPopBack() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }
}
