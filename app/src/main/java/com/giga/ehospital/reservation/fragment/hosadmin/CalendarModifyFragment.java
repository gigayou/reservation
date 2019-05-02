package com.giga.ehospital.reservation.fragment.hosadmin;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.giga.ehospital.reservation.R;
import com.giga.ehospital.reservation.container.NormalContainer;
import com.giga.ehospital.reservation.fragment.standard.StandardWithTobBarLayoutFragment;
import com.giga.ehospital.reservation.manager.hosadmin.CalendarDataManager;
import com.giga.ehospital.reservation.manager.hosadmin.DoctorDataManager;
import com.giga.ehospital.reservation.model.hospital.Doctor;
import com.giga.ehospital.reservation.model.hospital.Hospital;
import com.linxiao.framework.common.GsonParser;
import com.linxiao.framework.net.ApiResponse;
import com.linxiao.framework.rx.RxSubscriber;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    @BindString(R.string.fragment_calendar_modify_title)
    String title;
    @BindString(R.string.LOADING_MESSAGE)
    String LOADING_MESSAGE;

    private Doctor tDoctor = new Doctor();

    private DoctorDataManager doctorDataManager;
    private CalendarDataManager calendarDataManager;
    private String hospitalId;

    private List<Doctor> doctorOptions;
    private List<String> periodOptions = new ArrayList<>();
    private List<String> validOptions = new ArrayList<>();

    private Integer sDoctorIndex;
    private Integer sPeriodIndex;
    private Integer sValidIndex;
    private String sVisitingDate;

    private TimePickerView pvCustomLunar;
    private com.giga.ehospital.reservation.model.hospital.Calendar mCalendar;


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
        if (doctorDataManager == null)
            doctorDataManager = new DoctorDataManager();
        if (calendarDataManager == null)
            calendarDataManager = new CalendarDataManager();
        Hospital hospital = NormalContainer.get(NormalContainer.HOSPITAL);
        hospitalId = hospital.getHospitalId();
        tDoctor = new Doctor();
        tDoctor.setHospitalId(hospitalId);
        initDoctorOptions();
        initPeriodOptions();
        initValidOptions();
        initLunarPickerView();
        initData();
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

    private void initData() {
        Bundle arguments = this.getArguments();
        com.giga.ehospital.reservation.model.hospital.Calendar calendar = (com.giga.ehospital.reservation.model.hospital.Calendar) arguments.get("calendar");
        mCalendar = calendar;

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
        etVisitingRnum.setText(remainingNum + "");
        tvVisitingValid.setText(validOptions.get(isValid));
    }

    @OnClick({R.id.tv_visiting_docotr, R.id.tv_visiting_date, R.id.tv_visiting_period, R.id.tv_visiting_valid, R.id.btn_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_visiting_docotr:
                showDoctorPickerView();
                break;
            case R.id.tv_visiting_date:
                pvCustomLunar.show();
                break;
            case R.id.tv_visiting_period:
                showVisitingPeriodPickerView();
                break;
            case R.id.tv_visiting_valid:
                showVisitingValidPickerView();
                break;
            case R.id.btn_save:
                pushInfo();
                break;
        }
    }


    private void pushInfo() {

        /**doctor id  */
        String doctorId = null;
        // 判断是否有选择
        doctorId = mCalendar.getDoctorId();

        /**period Index */
        int periodIndex = 0;

        /**visiting date */
        String visitingDate = tvVisitingDate.getText().toString().trim();

        /**valid */
        Integer isValid = sValidIndex;

        // doctor sel
        if (sDoctorIndex == null)
            mCalendar.setDoctorId(doctorId);
        else {
            String sDoctorId = doctorOptions.get(sDoctorIndex).getDoctorId();
            mCalendar.setDoctorId(sDoctorId);
        }
        // period sel
        if (sPeriodIndex == null) {
            String periodStr = tvVisitingPeriod.getText().toString();
            if (periodStr.equals("上午"))
                periodIndex = 0;
            else if (periodStr.equals("下午"))
                periodIndex = 1;
            else
                periodIndex = 2;
            mCalendar.setAdmissionPeriod(periodIndex + "");
        }
        else
            mCalendar.setAdmissionPeriod(sPeriodIndex + "");
        // visiting date sel
        if (StringUtils.isNotBlank(visitingDate))
            mCalendar.setAdmissionDate(visitingDate);
        else {
            Toasty.info(getContext(), "请选择日期", Toasty.LENGTH_SHORT, true).show();
            return;
        }
        // visiting num sel
        Integer visitingNum = null;
        try {
            visitingNum = Integer.valueOf(etVisitingNum.getText().toString().trim());
        } catch (Exception e) {
            Toasty.error(getContext(), "请在接诊人数上输入有效数字", Toasty.LENGTH_SHORT, true).show();
            Log.e("error", "cast exception");
            return;
        }
        mCalendar.setAdmissionNum(visitingNum);
        // remaining num sel
        Integer remainingNum = null;
        try {
            remainingNum = Integer.valueOf(etVisitingRnum.getText().toString().trim());
        } catch (Exception e) {
            Toasty.error(getContext(), "请在剩余人数上输入有效数字", Toasty.LENGTH_SHORT, true).show();
            Log.e("error", "cast exception");
            return;
        }
        mCalendar.setRemainingNum(remainingNum);
        // valid
        if (sValidIndex != null)
            mCalendar.setIsValid(isValid);


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        calendarDataManager.update(mCalendar)
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


    private void initDoctorOptions() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
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
                            transform2DoctorList(response.data);
                        } else {
                            Toasty.error(getContext(), response.message, Toast.LENGTH_LONG, true).show();
                        }
                        onComplete();
                    }
                });
    }

    private void transform2DoctorList(String json) {
        JSONArray jsonArray = GsonParser.fromJSONObject(json, JSONArray.class);
        this.doctorOptions = GsonParser.fromJSONArray(jsonArray, Doctor.class);
    }

    private void showDoctorPickerView() {

        if (doctorOptions.size() == 0) {
            // 禁用选择医生tv控件
            tvVisitingDoctor.setEnabled(false);
            // 提醒
            Toasty.info(getContext(), "当前医院无医生信息，请先添加", Toasty.LENGTH_SHORT, true).show();
            return;
        }

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String displayName = "";
                if (doctorOptions.size() > 0) {
                    String doctorName = doctorOptions.get(options1).getDoctorName();
                    String typeName = doctorOptions.get(options1).getTypeName();
                    if (StringUtils.isNotBlank(typeName))
                        displayName = typeName + "-" + doctorName;
                    else
                        displayName = doctorName;
                }

                String name =  doctorOptions.size() > 0 ? displayName : "";
                tvVisitingDoctor.setText(name);
                sDoctorIndex = options1;
            }
        })
                .setTitleText("医生选择选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(doctorOptions);
        pvOptions.show();
    }

    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void initLunarPickerView() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();

        int year = startDate.get(Calendar.YEAR);
        int month = startDate.get(Calendar.MONTH);
        int day = startDate.get(Calendar.DAY_OF_MONTH);
        startDate.set(year, month, day);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2069, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                sVisitingDate = getTime(date);
                tvVisitingDate.setText(sVisitingDate);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                        //公农历切换
                        CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                                //自适应宽
                                setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
                            }
                        });

                    }

                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timePicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timePicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timePicker.getChildCount(); i++) {
                            View childAt = timePicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    //可根据需要自行截取数据显示
    private String getTime(Date date) {
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void showVisitingValidPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String valid = validOptions.size() > 0 ?
                        validOptions.get(options1) : "";
                tvVisitingValid.setText(valid);
                sValidIndex = options1;
            }
        })
                .setTitleText("有效性选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(validOptions);
        pvOptions.show();
    }

    private void showVisitingPeriodPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String period = periodOptions.size() > 0 ?
                        periodOptions.get(options1) : "";
                tvVisitingPeriod.setText(period);
                sPeriodIndex = options1;
            }
        })
                .setTitleText("时间段选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(periodOptions);
        pvOptions.show();
    }

}
