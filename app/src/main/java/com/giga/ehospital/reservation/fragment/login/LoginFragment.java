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
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.manager.LoginDataManager;
import com.giga.ehospital.reservation.manager.hosadmin.DoctorDataManager;
import com.giga.ehospital.reservation.manager.patient.UserDataManager;
import com.giga.ehospital.reservation.manager.sysamdin.BuserDataManager;
import com.giga.ehospital.reservation.manager.sysamdin.HosDataManager;
import com.giga.ehospital.reservation.model.User;
import com.giga.ehospital.reservation.model.hospital.Doctor;
import com.giga.ehospital.reservation.model.hospital.Hospital;
import com.giga.ehospital.reservation.model.system.Buser;
import com.giga.ehospital.reservation.util.ConfigUtil;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;
import com.qmuiteam.qmui.layout.QMUIButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private Hospital tHospital;
    private Buser tBuser;
    private User tUser;
    private Doctor tDoctor;

    private Pattern phonePattern;

    private LoginDataManager loginDataManager;
    private HosDataManager hosDataManager;
    private BuserDataManager buserDataManager;
    private UserDataManager userDataManager;
    private DoctorDataManager doctorDataManager;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        ButterKnife.bind(this, root);
        if (loginDataManager == null) {
            loginDataManager = new LoginDataManager();
        }
        if (hosDataManager == null) {
            hosDataManager = new HosDataManager();
        }
        if (buserDataManager == null) {
            buserDataManager = new BuserDataManager();
        }
        if (userDataManager == null) {
            userDataManager = new UserDataManager();
        }
        if (doctorDataManager == null) {
            doctorDataManager = new DoctorDataManager();
        }
        if (tHospital == null)
            tHospital = new Hospital();
        if (tBuser == null)
            tBuser = new Buser();
        if (tUser == null)
            tUser = new User();
        if (tDoctor == null)
            tDoctor = new Doctor();
        return root;
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String userId = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (checkUserPhone(userId)) {
                    userLogin(userId, password);
                } else {
                    buserLogin(userId, password);
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

    public boolean checkUserPhone(String phone){
        if(phonePattern == null)
            phonePattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|178|(18[0,5-9]))\\d{8}$");
        Matcher m = phonePattern.matcher(phone);
        return m.matches();
    }

    private void selectRelationBuser(String buserId) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        tBuser.setLoginId(buserId);
        buserDataManager.list(tBuser)
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
                            List<Buser> buserList = GsonParser.fromJSONArray(response.data, Buser.class);
                            if (buserList.size() == 0)
                                NormalContainer.put(NormalContainer.BUSER, new Buser());
                            else
                                NormalContainer.put(NormalContainer.BUSER, buserList.get(0));
                        } else {
                            Toasty.error(getContext(),response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    private void selectRelationUser(String userPhone) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        tUser.setUserPhone(userPhone);
        userDataManager.list(tUser)
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
                            List<User> userList = GsonParser.fromJSONArray(response.data, User.class);
                            if (userList.size() == 0)
                                NormalContainer.put(NormalContainer.USER, new User());
                            else
                                NormalContainer.put(NormalContainer.USER, userList.get(0));
                        } else {
                            Toasty.error(getContext(),response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    private void selectRelationHospital(String userId) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        tHospital.setHospitalManager(userId);
        hosDataManager.list(tHospital)
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
                            List<Hospital> hospitals = GsonParser.fromJSONArray(response.data, Hospital.class);
                            if (hospitals.size() == 0)
                                NormalContainer.put(NormalContainer.HOSPITAL, new Hospital());
                            else
                                NormalContainer.put(NormalContainer.HOSPITAL, hospitals.get(0));
                        } else {
                            Toasty.error(getContext(),response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    public void selectRelationDoctor(String userId) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        tDoctor.setLoginId(userId);
        doctorDataManager.list(tDoctor)
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
                            List<Doctor> doctors = GsonParser.fromJSONArray(response.data, Doctor.class);
                            if (doctors.size() == 0)
                                NormalContainer.put(NormalContainer.DOCTOR, new Doctor());
                            else
                                NormalContainer.put(NormalContainer.DOCTOR, doctors.get(0));
                        } else {
                            Toasty.error(getContext(),response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }


    private void displayHomeView(int roleId, String userId) {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        Bundle bundle = new Bundle();
        // 设置标记为不可返回
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        bundle.putInt("roleId", roleId);
        bundle.putString("userId", userId);
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
                            selectRelationUser(username);
                            Toasty.success(getContext(), SUCCESS_MESSAGE, Toast.LENGTH_SHORT, true).show();
                            displayHomeView(-1, username);
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
                            if (roleId == ConfigUtil.ROLE_HOS_ADMIN || roleId == ConfigUtil.ROLE_HOS_DOCTOR) {
                                selectRelationHospital(username);
                                if (roleId == ConfigUtil.ROLE_HOS_DOCTOR)
                                    selectRelationDoctor(username);
                            }
                            selectRelationBuser(username);
                            Toasty.success(getContext(), SUCCESS_MESSAGE, Toast.LENGTH_SHORT, true).show();
                            displayHomeView(roleId, username);
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

}
