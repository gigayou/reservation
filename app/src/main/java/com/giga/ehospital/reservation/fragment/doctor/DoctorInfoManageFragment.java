package com.giga.ehospital.reservation.fragment.doctor;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.hosadmin.DepTypeDataManager;
import com.giga.ehospital.reservation.manager.hosadmin.DoctorDataManager;
import com.giga.ehospital.reservation.model.code.DepartmentType;
import com.giga.ehospital.reservation.model.hospital.Doctor;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DoctorInfoManageFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.et_login_id)
    EditText etLoginId;
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
    @BindString(R.string.fragment_doctor_info_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private static DepartmentType departmentType = new DepartmentType();

    private DepTypeDataManager depTypeDataManager;
    private DoctorDataManager doctorDataManager;

    private List<DepartmentType> depTypeOptions;
    private List<String> sexOptions = new ArrayList();

    private Integer sSexIndex;

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
        initData();
    }

    private void initData() {
        Doctor doctor = NormalContainer.get(NormalContainer.DOCTOR);

        String doctorId = doctor.getDoctorId();
        String loginId = doctor.getLoginId();
        String hospitalId = doctor.getHospitalId();
        Long typeId = doctor.getTypeId();
        String doctorName = doctor.getDoctorName();
        Integer sex = doctor.getSex();
        String doctorTitle = doctor.getDoctorTitle();
        String skill = doctor.getSkill();
        String introduction = doctor.getIntroduction();
        String doctorPhoto = doctor.getDoctorPhoto();


        // 不能修改loginId
        etLoginId.setEnabled(false);

        etLoginId.setText(loginId);
        tvDoctorType.setText(doctor.getTypeName());
        etDoctorName.setText(doctorName);
        tvDoctorSex.setText(sexOptions.get(sex));
        etDoctorSkill.setText(skill);
        etDoctorIntro.setText(introduction);
    }

    @OnClick({R.id.tv_doctor_sex, R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
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
        Doctor doctor = NormalContainer.get(NormalContainer.DOCTOR);

        String doctorName = etDoctorName.getText().toString().trim();
        String skill = etDoctorSkill.getText().toString().trim();
        String introduction = etDoctorIntro.getText().toString().trim();
        if (StringUtils.isBlank(doctorName))
            Toasty.error(getContext(), "请填写医生姓名", Toasty.LENGTH_SHORT, true).show();
        else {
            doctor.setDoctorName(doctorName);
            if (sSexIndex != null)
                doctor.setSex(sSexIndex);
            doctor.setSkill(skill);
            doctor.setIntroduction(introduction);

            // 更新缓存数据
            NormalContainer.put(NormalContainer.DOCTOR, doctor);

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

    }

    private void initDepTypeOptions() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        depTypeDataManager.list(departmentType)
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
        List<DepartmentType> departmentTypes = GsonParser.fromJSONArray(jsonArray, DepartmentType.class);

        depTypeOptions = departmentTypes;
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
