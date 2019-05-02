package com.giga.ehospital.reservation.fragment.doctor;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class CalendarModifyFragment extends StandardWithTobBarLayoutFragment {

    @BindView(R.id.scrollview)
    NestedScrollView scrollView;
    @BindView(R.id.tv_visiting_docotr)
    TextView tvVisitingDoctor;
    @BindView(R.id.tv_visiting_date)
    TextView tvVisitingDate;
    @BindView(R.id.tv_visiting_period)
    TextView tvVisitingPeriod;
    @BindView(R.id.et_visiting_num)
    EditText etVisitingNum;
    @BindView(R.id.et_visiting_rnum)
    EditText etVisitingRnum;
    @BindView(R.id.tv_visiting_valid)
    TextView tvVisitingValid;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindString(R.string.fragment_calendar_modify_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private List<String> periodOptions = new ArrayList<>();
    private List<String> validOptions = new ArrayList<>();

    @Override
    protected String getTopBarTitle() {
        return title;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hosadmin_calendar_add;
    }

    @Override
    protected void initLastCustom() {
        btnSave.setVisibility(View.INVISIBLE);
        initPeriodOptions();
        initValidOptions();
        initData();
    }

    private void initData() {
        Bundle arguments = this.getArguments();
        com.giga.ehospital.reservation.model.hospital.Calendar calendar = (com.giga.ehospital.reservation.model.hospital.Calendar) arguments.get("calendar");

        String visitingDoctor = "";
        String departmentName = calendar.getDepartmentName();
        if (StringUtils.isNotBlank(departmentName))
            visitingDoctor = departmentName + " " + calendar.getDoctorName();
        else
            visitingDoctor = calendar.getDoctorName();
        String admissionDate = calendar.getAdmissionDate();
        Integer admissionPeriod = Integer.valueOf(calendar.getAdmissionPeriod());
        Integer admissionNum = calendar.getAdmissionNum();
        Integer remainingNum = calendar.getRemainingNum();
        Integer isValid = calendar.getIsValid();

        tvVisitingDoctor.setText(visitingDoctor);
        tvVisitingDate.setText(admissionDate);
        tvVisitingPeriod.setText(periodOptions.get(admissionPeriod));
        etVisitingNum.setText(admissionNum + "");
        etVisitingNum.setEnabled(false);
        etVisitingRnum.setText(remainingNum + "");
        etVisitingRnum.setEnabled(false);
        tvVisitingValid.setText(validOptions.get(isValid));
    }

    private void initValidOptions() {
        validOptions.add("有效");
        validOptions.add("无效");
    }

    private void initPeriodOptions() {
        periodOptions.add("上午");
        periodOptions.add("下午");
        periodOptions.add("晚上");
    }
}
