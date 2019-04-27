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
import com.giga.ehospital.reservation.manager.hosadmin.DepDataManager;
import com.giga.ehospital.reservation.manager.hosadmin.DepTypeDataManager;
import com.giga.ehospital.reservation.model.code.DepartmentType;
import com.giga.ehospital.reservation.model.hospital.Department;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DepModifyFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.et_dep_name)
    EditText etDepName;
    @BindView(R.id.tv_dep_type)
    TextView tvDepType;
    @BindString(R.string.fragment_dep_modify_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private static DepartmentType departmentType = new DepartmentType();

    private DepTypeDataManager depTypeDataManager;
    private DepDataManager depDataManager;

    private List<DepartmentType> depTypeOptions;

    private Integer sDepTypeIndex;

    private Long sDepTypeId;
    private String sHospitalId;
    private String sDepartmentId;

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hosadmin_dep_add;
    }

    @Override
    protected void initLastCustom() {
        if (depDataManager == null)
            depDataManager = new DepDataManager();
        if (depTypeDataManager == null)
            depTypeDataManager = new DepTypeDataManager();
        initDepTypeOptions();
        initData();
    }

    private void initData() {
        Bundle arguments = this.getArguments();
        Department department = (Department) arguments.get("department");

        sDepartmentId = department.getDepartmentId();
        sHospitalId = department.getHospitalId();
        sDepTypeId = department.getTypeId();
        String departmentName = department.getDepartmentName();
        String typeName = department.getTypeName();

        etDepName.setText(departmentName);
        tvDepType.setText(typeName);
    }

    @OnClick({R.id.tv_dep_type, R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dep_type:
                showDepTypePickerView();
                break;
            case R.id.btn_save:
                pushInfo();
                break;
            default:
                break;
        }
    }

    private void pushInfo() {
        String depName = etDepName.getText().toString().trim();
        String depTypeName = tvDepType.getText().toString().trim();

        Department department = new Department();
        department.setDepartmentId(sDepartmentId);
        department.setHospitalId(sHospitalId);
        department.setTypeId(sDepTypeId);
        department.setDepartmentName(depName);
        department.setTypeName(depTypeName);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        depDataManager.update(department)
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

    private void showDepTypePickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String depType = depTypeOptions.size() > 0 ?
                        depTypeOptions.get(options1).getPickerViewText() : "";
                tvDepType.setText(depType);
                sDepTypeIndex = options1;
                sDepTypeId = depTypeOptions.get(sDepTypeIndex).getDepartmentTypeId();
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

}
