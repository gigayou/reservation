package com.giga.ehospital.reservation.fragment.hosadmin;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.hosadmin.DepTypeDataManager;
import com.giga.ehospital.reservation.manager.hosadmin.DoctorDataManager;
import com.giga.ehospital.reservation.model.code.DepartmentType;
import com.giga.ehospital.reservation.model.hospital.Department;
import com.giga.ehospital.reservation.model.hospital.Doctor;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DoctorModifyFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.et_login_id)
    EditText etDoctorId;
    @BindView(R.id.tv_doctor_type)
    TextView tvDoctorType;
    @BindView(R.id.et_doctor_name)
    EditText etDoctorName;
    @BindView(R.id.tv_doctor_sex)
    TextView tvDoctorSex;
    @BindView(R.id.et_doctor_skill)
    EditText etDoctorSkill;
    @BindView(R.id.et_doctor_intro)
    EditText etDoctorIntro;
    @BindString(R.string.fragment_doctor_modify_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private DepartmentType tDepartmentType = new DepartmentType();

    private DepTypeDataManager depTypeDataManager;
    private DoctorDataManager doctorDataManager;

    private List<DepartmentType> depTypeOptions;
    private List<String> sexOptions = new ArrayList<String>();

    private Integer sDepTypeIndex;
    private Integer sSexIndex;

    private String doctorId;
    private String loginId;
    private String hospitalId;
    private Long depTypeId;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hosadmin_doctor_add;
    }

    @Override
    protected void initLastCustom() {
        if (depTypeDataManager == null)
            depTypeDataManager = new DepTypeDataManager();
        if (doctorDataManager == null)
            doctorDataManager = new DoctorDataManager();
        initSexOptions();
        initDepTypeOptions();
        initData();
    }

    private void initData() {
        Bundle arguments = this.getArguments();
        Doctor doctor = (Doctor) arguments.get("doctor");

        doctorId = doctor.getDoctorId();
        loginId = doctor.getLoginId();
        hospitalId = doctor.getHospitalId();
        depTypeId = doctor.getTypeId();
        sSexIndex = doctor.getSex();

        String doctorName = doctor.getDoctorName();
        String doctorSkill = doctor.getSkill();
        String introduction = doctor.getIntroduction();

        // 登录账号不可修改
        etDoctorId.setEnabled(false);

        etDoctorId.setText(loginId);
        tvDoctorType.setText(doctor.getTypeName());
        etDoctorName.setText(doctorName);
        tvDoctorSex.setText(sexOptions.get(sSexIndex));
        etDoctorSkill.setText(doctorSkill);
        etDoctorIntro.setText(introduction);
    }

    @OnClick({R.id.tv_doctor_type, R.id.tv_doctor_sex, R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_doctor_type:
                showDepTypePickerView();
                break;
            case R.id.tv_doctor_sex:
                showSexPickerView();
                break;
            case R.id.btn_save:
                pushInfo();
                break;
            default:
                break;
        }
    }

    private void pushInfo() {
        String doctorName = etDoctorName.getText().toString().trim();
        Long departmentTypeId = 0L;
        if (sDepTypeIndex != null)
            departmentTypeId = depTypeOptions.get(sDepTypeIndex).getDepartmentTypeId();
        else
            departmentTypeId = depTypeId;
        String skill = etDoctorSkill.getText().toString().trim();
        String introduction = etDoctorIntro.getText().toString().trim();

        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);
        doctor.setLoginId(loginId);
        doctor.setHospitalId(hospitalId);
        doctor.setTypeId(departmentTypeId);
        doctor.setDoctorName(doctorName);
        doctor.setSex(sSexIndex);
        doctor.setDoctorTitle("0");
        doctor.setSkill(skill);
        doctor.setIntroduction(introduction);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        doctorDataManager.update(doctor)
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
                            Toasty.success(getContext(), "更新成功", Toasty.LENGTH_SHORT, true).show();
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    private void showDepTypePickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String depType = depTypeOptions.size() > 0 ?
                        depTypeOptions.get(options1).getPickerViewText() : "";
                tvDoctorType.setText(depType);
                sDepTypeIndex = options1;
                depTypeId = depTypeOptions.get(sDepTypeIndex).getDepartmentTypeId();
            }
        })
                .setTitleText("科室类型选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(depTypeOptions);
        pvOptions.show();
    }

    private void initDepTypeOptions() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        depTypeDataManager.list(tDepartmentType)
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
                            transform2DepTypeList(response.data);
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                    }
                });
    }

    private void transform2DepTypeList(String json) {
        JSONArray jsonArray = GsonParser.fromJSONObject(json, JSONArray.class);
        depTypeOptions = GsonParser.fromJSONArray(jsonArray, DepartmentType.class);
    }

    private void initSexOptions() {
        sexOptions.add("男");
        sexOptions.add("女");
    }

    private void showSexPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String sex = sexOptions.size() > 0 ?
                        sexOptions.get(options1) : "";
                tvDoctorSex.setText(sex);
                sSexIndex = options1;
            }
        })
                .setTitleText("性别选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(sexOptions);
        pvOptions.show();
    }
}
