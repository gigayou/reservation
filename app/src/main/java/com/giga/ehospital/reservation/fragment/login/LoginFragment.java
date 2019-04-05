package com.giga.ehospital.reservation.fragment.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.activity.HomeActivity;
import com.giga.ehospital.reservation.base.fragment.BaseFragment;
import com.giga.ehospital.reservation.manager.LoginDataManager;
import com.giga.ehospital.reservation.model.User;
import com.giga.ehospital.reservation.model.system.Buser;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;
import com.qmuiteam.qmui.layout.QMUIButton;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginFragment extends BaseFragment {

    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPasswrod)
    EditText etPassword;
    @BindView(R.id.btn_login)
    QMUIButton btnLogin;
    @BindView(R.id.btn_register)
    QMUIButton btnRegister;
    @BindString(R.string.LOGIN_LOADING_MESSAGE)
    String LOADING_MESSAGE;
    @BindString(R.string.LOGIN_SUCCESS_MESSAGE)
    String SUCCESS_MESSAGE;

    private LoginDataManager loginDataManager;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        ButterKnife.bind(this, root);
        if (loginDataManager == null) {
            loginDataManager = new LoginDataManager();
        }
        return root;
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                // 根据用户名长度判别是普通用户登录还是管理员登录
                if (username.length() == 11) {
                    userLogin(username, password);
                } else {
                    displayHomeView(1);
//                    buserLogin(username, password);
                }
                break;
            case R.id.btn_register:
//                displayRegView();
                break;
            case KeyEvent.KEYCODE_BACK:
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }


    private void displayHomeView(int roleId) {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        Bundle bundle = new Bundle();
        // 设置标记为不可返回
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        bundle.putInt("roleId", roleId);
        intent.putExtra("message", bundle);
        startActivity(intent);
    }

    /**
     * 通过fragment管理类和fragment事务来控制当前页面跳转到注册页面
     *
     * 注册页面可回退栈到当前页
     */
    private void displayRegView() {
        Fragment registerFragment = new RegisterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.login_container, registerFragment);
        ft.commit();
    }


    /**
     * 测试api接口
     */
    public void requestAPI(Context context, String loadingMsg, String successMsg) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        loginDataManager.getTestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressDialog.setMessage(loadingMsg);
                    progressDialog.show();
                })
                .doOnComplete(() -> progressDialog.dismiss())
                .subscribe(new RxSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 普通用户登录
     *
     * @param username 用户名/手机号
     * @param password 密码
     */
    public void userLogin(String username, String password) {
        User user = new User();
        user.setUserPhone(username);
        user.setUserPwd(password);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        loginDataManager.userLogin(user)
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
                            Toasty.success(getContext(), SUCCESS_MESSAGE, Toast.LENGTH_SHORT, true).show();
                            displayHomeView(-1);
                        } else {
                            Toasty.error(getContext(),response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }


    /**
     * 系统管理/医院管理员/医疗专家登录
     *
     * @param username 用户名
     * @param password 密码
     */
    public void buserLogin(String username, String password) {
        Buser buser = new Buser();
        buser.setLoginId(username);
        buser.setLoginPwd(password);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        loginDataManager.buserLogin(buser)
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
                            int roleId = 0;
                            try {
                                String jsonData = response.data;
                                JSONObject jsonObject = GsonParser.fromJSONObject(jsonData, JSONObject.class);
                                roleId = jsonObject.getInt("roleId");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toasty.success(getContext(), SUCCESS_MESSAGE, Toast.LENGTH_SHORT, true).show();
                            displayHomeView(roleId);
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }



}
